package com.sachin.nutrify.injection

import com.sachin.nutrify.data.AuthRepository
import com.sachin.nutrify.data.FirestoreRepository
import com.sachin.nutrify.data.impl.AuthRepositoryImpl
import com.sachin.nutrify.data.impl.CredentialProviderImpl
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl
import com.sachin.nutrify.data.prefs.AuthDataSource
import com.sachin.nutrify.provider.CredentialProvider
import com.sachin.nutrify.provider.GoogleAuthProvider
import com.sachin.nutrify.ui.auth.AuthViewModel
import com.sachin.nutrify.ui.messaging.chat.ChatViewModel
import com.sachin.nutrify.ui.messaging.contacts.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthDataSource(get()) }

    factory<FirestoreRepository> { FirestoreRepositoryImpl() }
    factory<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    single { GoogleAuthProvider() }
    single<CredentialProvider> { CredentialProviderImpl() }

    viewModel { AuthViewModel(get()) }
    viewModel { ContactsViewModel(get(), get()) }
    viewModel { ChatViewModel(get(), get()) }
}
