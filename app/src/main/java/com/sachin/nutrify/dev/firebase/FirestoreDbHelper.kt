package com.sachin.nutrify.dev.firebase

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.sachin.nutrify.dev.model.ChatMessage
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.model.User
import com.sachin.nutrify.dev.utils.Constants
import com.sachin.nutrify.dev.utils.Logger.Companion.d
import com.sachin.nutrify.dev.utils.SharedPrefHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.HashMap

class FirestoreDbHelper(context: Context) {
    var fireBaseDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var pref: SharedPrefHelper = SharedPrefHelper.getInstance(context)
    private var TAG = FirestoreDbHelper::class.java.simpleName
    private var mMessageListener: GetMessagesListener? = null
   // private var localDb = RoomDB.getDatabase(context)

    private val mChatMessages: MutableLiveData<ArrayList<ChatMessage>> = MutableLiveData()
    private val mUserList: MutableLiveData<ArrayList<FUser>> = MutableLiveData()
    fun addUser(user: User, signUpCallback: SignUpListener) {
        d(TAG, "addUser()")
        val dataMap = HashMap<String, Any>()
        dataMap[Constants.FIRST_NAME] = user.firstName
        dataMap[Constants.LAST_NAME] = user.lastName
        dataMap[Constants.EMAIL] = user.email
        dataMap[Constants.PASSWORD] = user.password
        dataMap[Constants.ENC_IMAGE] = user.encodedImage.toString()
        putData(Constants.Database.USERS, dataMap, signUpCallback)
    }


    private fun putData(key: String, data: HashMap<String, Any>, signUpCallback: SignUpListener) {
        fireBaseDb.collection(key)
            .add(data)
            .addOnSuccessListener {
                d(TAG, "User added")
                signUpCallback.signUpSuccess()
            }
            .addOnFailureListener {
                d(TAG, "User not added, please try again")
                signUpCallback.signUpFailure()
            }
    }

    interface SignInListener {
        fun signInSuccess()
        fun signInFailure()
    }

    interface SignUpListener {
        fun signUpSuccess()
        fun signUpFailure()
    }
    fun signIn(id: String, pass: String, callback: SignInListener) {
        d(TAG, "signIn()")
        fireBaseDb.collection(Constants.Database.USERS)
            .whereEqualTo(Constants.EMAIL, id)
            .whereEqualTo(Constants.PASSWORD, pass)
            .get()
            .addOnCompleteListener { task ->
                d(TAG, "signIn success")
                if(task.isSuccessful && task.result != null
                    && task.result.documents.size > 0) {
                    val snapshot = task.result.documents[0]
                    d(TAG, "Snapshot id: " + snapshot.id)

                    var fUser = FUser(
                        snapshot.id,
                        snapshot.getString(Constants.FIRST_NAME)!!,
                        snapshot.getString(Constants.LAST_NAME)!!,
                        snapshot.getString(Constants.EMAIL)!!,
                        snapshot.getString(Constants.PASSWORD)!!,
                        snapshot.getString(Constants.ENC_IMAGE)!!
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                       // localDb.userDetailDao().insertUser(fUser)
                        pref.putBoolean(Constants.IS_SIGNED_IN, true)
                        pref.putString(Constants.SIGNED_IN_USER_UID, snapshot.id)
                    }
                }
                callback.signInSuccess()
                return@addOnCompleteListener
            }
            /*.addOnFailureListener {
                d(TAG, "signIn failure")
                callback.signInFailure()
                return@addOnFailureListener
            }*/
    }

    fun sendMessage(msg: ChatMessage) {
        val map = HashMap<String, Any>()
        map[Constants.KEY_SENDER_ID] = msg.senderId
        map[Constants.KEY_RECEIVER_ID] = msg.receiverId
        map[Constants.KEY_MESSAGE] = msg.message
        map[Constants.KEY_TIMESTAMP] = msg.tms
        fireBaseDb.collection(Constants.KEY_COLLECTION_CHAT).add(map).addOnSuccessListener {
            d(TAG, "sendMessage successful")
        }.addOnFailureListener {
            d(TAG, "sendMessage failed")
        }
    }

    interface GetMessagesListener {
        fun getMessagesSuccess(list: MutableLiveData<ArrayList<ChatMessage>>)
        fun getMessagesFailure()
    }

    private val eventListener: EventListener<QuerySnapshot> =
        EventListener<QuerySnapshot> { value, error ->
            if (value != null) {
                for(docChange in value.documentChanges) {
                    if(docChange.type == DocumentChange.Type.ADDED
                        || docChange.type == DocumentChange.Type.MODIFIED) {
                        val it = docChange.document
                        val chatMessage = ChatMessage(
                            it.getString(Constants.KEY_SENDER_ID)!!,
                            it.getString(Constants.KEY_RECEIVER_ID)!!,
                            it.getString(Constants.KEY_MESSAGE)!!,
                            it.getString(Constants.KEY_TIMESTAMP)!!
                        )
                        mChatMessages.value?.add(chatMessage)
                    }
                }
                mMessageListener?.getMessagesSuccess(mChatMessages)
            }
        }


    fun getMessagesByRxId(id: String, action: GetMessagesListener) {
        mMessageListener = action
        //SENT msgs
        fireBaseDb.collection(Constants.Database.CHATS)
            .whereEqualTo(Constants.KEY_RECEIVER_ID, id)
            .whereEqualTo(Constants.KEY_SENDER_ID, pref.getString(Constants.SIGNED_IN_USER_UID))
            .addSnapshotListener(eventListener)

        //RECEIVED msgs
        fireBaseDb.collection(Constants.Database.CHATS)
            .whereEqualTo(Constants.KEY_SENDER_ID, id)
            .whereEqualTo(Constants.KEY_RECEIVER_ID, pref.getString(Constants.SIGNED_IN_USER_UID))
            .addSnapshotListener(eventListener)


            /*.get()
            .addOnCompleteListener {task ->
                d(TAG, "getMessages success")
                val response = mutableListOf<ChatMessage>()
                if(task.isSuccessful && task.result != null
                    && task.result.documents.size > 0) {
                    task.result.documents.forEach {
                        val chatMessage = ChatMessage(
                            it.getString(Constants.KEY_SENDER_ID)!!,
                            it.getString(Constants.KEY_RECEIVER_ID)!!,
                            it.getString(Constants.KEY_MESSAGE)!!,
                            it.getString(Constants.KEY_TIMESTAMP)!!
                        )
                        response.add(chatMessage)
                    }
                    action.getMessagesSuccess(response)
                }
            }*/
    }
    interface GetUsersListener {
        fun getUsersSuccess(userList: MutableLiveData<ArrayList<FUser>>)
        fun getUsersFailure()
    }
    fun getAllUsers(action: GetUsersListener) {
        d(TAG, "getAllUsers()")

        fireBaseDb.collection(Constants.Database.USERS)
            .get()
            .addOnCompleteListener { task ->
                d(TAG, "getUsers success")
                if(task.isSuccessful && task.result != null
                    && task.result.documents.size > 0) {
                    val snapshot = task.result.documents
                    //d(TAG, "Snapshot id: " + snapshot.id)
                    val response = mutableListOf<FUser>()
                    snapshot.forEach {
                        val fUser = FUser(
                            it.id,
                            it.getString(Constants.FIRST_NAME)!!,
                            it.getString(Constants.LAST_NAME)!!,
                            it.getString(Constants.EMAIL)!!,
                            it.getString(Constants.PASSWORD)!!,
                            it.getString(Constants.ENC_IMAGE)!!
                        )
                        mUserList.value?.add(fUser)
                        /*CoroutineScope(Dispatchers.IO).launch {
                            // localDb.userDetailDao().insertUser(fUser)
                            //pref.putBoolean(Constants.IS_SIGNED_IN, true)
                        }*/
                    }
                    action.getUsersSuccess(mUserList)
                }
                return@addOnCompleteListener
            }
        /*.addOnFailureListener {
            d(TAG, "getUsers failure")
            return@addOnFailureListener
        }*/

    }

    /*companion object {
        private var singleton: FirestoreDbHelper? = null

        fun getInstance(context: Context?) : FirestoreDbHelper {
            if(singleton == null) {
                synchronized(FirestoreDbHelper::class.java) {
                    if(singleton == null)
                        singleton = FirestoreDbHelper(context!!)
                }
            }
            return singleton!!
        }
    }*/
}