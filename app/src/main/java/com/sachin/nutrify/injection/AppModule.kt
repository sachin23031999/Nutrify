package com.sachin.nutrify.injection

import com.sachin.nutrify.MainViewModel
import com.sachin.nutrify.data.CredentialRepository
import com.sachin.nutrify.data.FirestoreRepository
import com.sachin.nutrify.data.impl.AuthRepositoryImpl
import com.sachin.nutrify.data.impl.CredentialRepositoryImpl
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl
import com.sachin.nutrify.data.prefs.AuthDataSource
import com.sachin.nutrify.data.room.AuthRepository
import com.sachin.nutrify.provider.GoogleAuthProvider
import com.sachin.nutrify.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthDataSource(get()) }
    factory<CredentialRepository> { CredentialRepositoryImpl() }
    factory<FirestoreRepository> { FirestoreRepositoryImpl() }
    factory<AuthRepository> { AuthRepositoryImpl(get()) }
    single { GoogleAuthProvider() }

    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get(), get(), get()) }
}
