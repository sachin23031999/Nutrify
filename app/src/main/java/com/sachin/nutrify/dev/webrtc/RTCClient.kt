package com.developerspace.webrtcsample

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.Logger.Companion.e
import org.webrtc.*


class RTCClient(
        context: Application,
        observer: PeerConnection.Observer
) {

    companion object {
        private const val LOCAL_TRACK_ID = "local_track"
        private const val LOCAL_STREAM_ID = "local_track"
        /*private lateinit var rtcClient: RTCClient
        fun getInstance(context: Context) {
            synchronized((RTCClient::class.java)) {
                if(rtcClient != null) {
                    rtcClient = RTCClient(context.applicationContext, )
                }
            }
        }*/
    }

    private val rootEglBase: EglBase = EglBase.create()

    private var localAudioTrack : AudioTrack? = null
    private var localVideoTrack : VideoTrack? = null
    val TAG = "RTCClient"

    var remoteSessionDescription : SessionDescription? = null

    val db = Firebase.firestore
    init {
        initPeerConnectionFactory(context)
    }

    private val iceServer = listOf(
            PeerConnection.IceServer.builder("stun:stun.l.google.com:19302")
                    .createIceServer()
    )

    private val peerConnectionFactory by lazy { buildPeerConnectionFactory() }
    private val videoCapturer by lazy { getVideoCapturer(context) }

    private val audioSource by lazy { peerConnectionFactory.createAudioSource(MediaConstraints())}
    private val localVideoSource by lazy { peerConnectionFactory.createVideoSource(false) }
    private val peerConnection by lazy { buildPeerConnection(observer) }

    private fun initPeerConnectionFactory(context: Application) {
        val options = PeerConnectionFactory.InitializationOptions.builder(context)
                .setEnableInternalTracer(true)
                .setFieldTrials("WebRTC-H264HighProfile/Enabled/")
                .createInitializationOptions()
        PeerConnectionFactory.initialize(options)
    }

    private fun buildPeerConnectionFactory(): PeerConnectionFactory {
        return PeerConnectionFactory
                .builder()
                .setVideoDecoderFactory(DefaultVideoDecoderFactory(rootEglBase.eglBaseContext))
                .setVideoEncoderFactory(DefaultVideoEncoderFactory(rootEglBase.eglBaseContext, true, true))
                .setOptions(PeerConnectionFactory.Options().apply {
                    disableEncryption = true
                    disableNetworkMonitor = true
                })
                .createPeerConnectionFactory()
    }

    private fun buildPeerConnection(observer: PeerConnection.Observer) = peerConnectionFactory.createPeerConnection(
            iceServer,
            observer
    )

    private fun getVideoCapturer(context: Context) =
            Camera2Enumerator(context).run {
                deviceNames.find {
                    isFrontFacing(it)
                }?.let {
                    createCapturer(it, null)
                } ?: throw IllegalStateException()
            }

    fun initSurfaceView(view: SurfaceViewRenderer) = view.run {
        setMirror(true)
        setEnableHardwareScaler(true)
        init(rootEglBase.eglBaseContext, null)
    }

    fun startLocalVideoCapture(localVideoOutput: SurfaceViewRenderer) {
        val surfaceTextureHelper = SurfaceTextureHelper.create(Thread.currentThread().name, rootEglBase.eglBaseContext)
        (videoCapturer as VideoCapturer).initialize(surfaceTextureHelper, localVideoOutput.context, localVideoSource.capturerObserver)
        videoCapturer.startCapture(320, 240, 60)
        localAudioTrack = peerConnectionFactory.createAudioTrack(LOCAL_TRACK_ID + "_audio", audioSource);
        localVideoTrack = peerConnectionFactory.createVideoTrack(LOCAL_TRACK_ID, localVideoSource)
        localVideoTrack?.addSink(localVideoOutput)
        val localStream = peerConnectionFactory.createLocalMediaStream(LOCAL_STREAM_ID)
        localStream.addTrack(localVideoTrack)
        localStream.addTrack(localAudioTrack)
        peerConnection?.addStream(localStream)
    }

    private fun PeerConnection.call(sdpObserver: SdpObserver, meetingID: String) {
        val constraints = MediaConstraints().apply {
            mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
        }

        createOffer(object : SdpObserver by sdpObserver {
            override fun onCreateSuccess(desc: SessionDescription?) {
                setLocalDescription(object : SdpObserver {
                    override fun onSetFailure(p0: String?) {
                        d(TAG, "call(), onSetFailure(): $p0")
                    }

                    override fun onSetSuccess() {
                        d(TAG, "call(), onSetSuccess()")
                        val offer = hashMapOf(
                                "sdp" to desc?.description,
                                "type" to desc?.type
                        )
                        db.collection("calls").document(meetingID)
                                .set(offer)
                                .addOnSuccessListener {
                                    d(TAG, "DocumentSnapshot added")
                                }
                                .addOnFailureListener { e ->
                                    d(TAG, "Error adding document")
                                }
                        d(TAG, "onSetSuccess")
                    }

                    override fun onCreateSuccess(p0: SessionDescription?) {
                        d(TAG, "onCreateSuccess: Description $p0")
                    }

                    override fun onCreateFailure(p0: String?) {
                        d(TAG, "onCreateFailure: $p0")
                    }
                }, desc)
                sdpObserver.onCreateSuccess(desc)
            }

            override fun onSetFailure(p0: String?) {
                d(TAG, "onSetFailure: $p0")
            }

            override fun onCreateFailure(p0: String?) {
                d(TAG, "onCreateFailure: $p0")
            }
        }, constraints)
    }

    private fun PeerConnection.answer(sdpObserver: SdpObserver, meetingID: String) {
        val constraints = MediaConstraints().apply {
            mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
        }
        createAnswer(object : SdpObserver by sdpObserver {
            override fun onCreateSuccess(desc: SessionDescription?) {
                val answer = hashMapOf(
                        "sdp" to desc?.description,
                        "type" to desc?.type
                )
                db.collection("calls").document(meetingID)
                        .set(answer)
                        .addOnSuccessListener {
                            d(TAG, "DocumentSnapshot added")
                        }
                        .addOnFailureListener { e ->
                            d(TAG, "Error adding document")
                        }
                setLocalDescription(object : SdpObserver {
                    override fun onSetFailure(p0: String?) {
                        d(TAG, "onSetFailure: $p0")
                    }

                    override fun onSetSuccess() {
                        d(TAG, "onSetSuccess")
                    }

                    override fun onCreateSuccess(p0: SessionDescription?) {
                        d(TAG, "onCreateSuccess: Description $p0")
                    }

                    override fun onCreateFailure(p0: String?) {
                        d(TAG, "onCreateFailureLocal: $p0")
                    }
                }, desc)
                sdpObserver.onCreateSuccess(desc)
            }

            override fun onCreateFailure(p0: String?) {
                d(TAG, "onCreateFailureRemote: $p0")
            }
        }, constraints)
    }

    fun call(sdpObserver: SdpObserver, meetingID: String) = peerConnection?.call(sdpObserver, meetingID)

    fun answer(sdpObserver: SdpObserver, meetingID: String) = peerConnection?.answer(sdpObserver, meetingID)

    fun onRemoteSessionReceived(sessionDescription: SessionDescription) {
        remoteSessionDescription = sessionDescription
        peerConnection?.setRemoteDescription(object : SdpObserver {
            override fun onSetFailure(p0: String?) {
                d(TAG, "onSetFailure: $p0")
            }

            override fun onSetSuccess() {
                d(TAG, "onSetSuccessRemoteSession")
            }

            override fun onCreateSuccess(p0: SessionDescription?) {
                d(TAG, "onCreateSuccessRemoteSession: Description $p0")
            }

            override fun onCreateFailure(p0: String?) {
                d(TAG, "onCreateFailure")
            }
        }, sessionDescription)

    }

    fun addIceCandidate(iceCandidate: IceCandidate?) {
        d(TAG, "addIceCandidate(), iceCandidate: $iceCandidate")
        peerConnection?.addIceCandidate(iceCandidate)
    }

    fun endCall(meetingID: String) {
        db.collection("calls").document(meetingID).collection("candidates")
                .get().addOnSuccessListener {
                    val iceCandidateArray: MutableList<IceCandidate> = mutableListOf()
                    for ( dataSnapshot in it) {
                        if (dataSnapshot.contains("type") && dataSnapshot["type"]=="offerCandidate") {
                            val offerCandidate = dataSnapshot
                            iceCandidateArray.add(IceCandidate(offerCandidate["sdpMid"].toString(), Math.toIntExact(offerCandidate["sdpMLineIndex"] as Long), offerCandidate["sdp"].toString()))
                        } else if (dataSnapshot.contains("type") && dataSnapshot["type"]=="answerCandidate") {
                            val answerCandidate = dataSnapshot
                            iceCandidateArray.add(IceCandidate(answerCandidate["sdpMid"].toString(), Math.toIntExact(answerCandidate["sdpMLineIndex"] as Long), answerCandidate["sdp"].toString()))
                        }
                    }
                    peerConnection?.removeIceCandidates(iceCandidateArray.toTypedArray())
                }
        val endCall = hashMapOf(
                "type" to "END_CALL"
        )
        db.collection("calls").document(meetingID)//.delete()
                .set(endCall)
                .addOnSuccessListener {
                    d(TAG, "DocumentSnapshot added")
                }
                .addOnFailureListener { e ->
                    d(TAG, "Error adding document")
                }

        peerConnection?.close()
    }

    fun enableVideo(videoEnabled: Boolean) {
        if (localVideoTrack !=null)
            localVideoTrack?.setEnabled(videoEnabled)
    }

    fun enableAudio(audioEnabled: Boolean) {
        if (localAudioTrack != null)
            localAudioTrack?.setEnabled(audioEnabled)
    }
    fun switchCamera() {
        videoCapturer.switchCamera(null)
    }
}