package com.sachin.nutrify.injection

import com.sachin.nutrify.MainViewModel
import com.sachin.nutrify.data.CredentialRepository
import com.sachin.nutrify.data.FirestoreRepository
import com.sachin.nutrify.data.impl.CredentialRepositoryImpl
import com.sachin.nutrify.data.impl.FirestoreRepositoryImpl
import com.sachin.nutrify.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<CredentialRepository> { CredentialRepositoryImpl() }
    factory<FirestoreRepository> { FirestoreRepositoryImpl() }
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get()) }
}
