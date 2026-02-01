package com.project.mytemplate.di

import com.project.mytemplate.data.datasource.FirebaseFirebaseAuthDataSourceImpl
import com.project.mytemplate.data.datasource.PostsDataSourceImpl
import com.project.mytemplate.data.interfaces.FirebaseAuthDataSource
import com.project.mytemplate.data.interfaces.PostsDataSource
import com.project.mytemplate.data.repository.FirebaseFirebaseAuthRepositoryImpl
import com.project.mytemplate.data.repository.PostsRepositoryImpl
import com.project.mytemplate.domine.interfaces.FirebaseAuthRepository
import com.project.mytemplate.domine.interfaces.PostsRepository
import com.project.mytemplate.domine.usecase.SendMessageUseCase
import com.project.mytemplate.domine.usecase.firebase.SignInWithEmailUseCase
import com.project.mytemplate.domine.usecase.firebase.SignInWithGoogleUseCase
import com.project.mytemplate.domine.usecase.firebase.SignUpWithEmailUseCase
import org.koin.dsl.module
import com.project.mytemplate.presentation.screens.screen1.ScreenOneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val useCaseModule = module {

    single<PostsDataSource> { PostsDataSourceImpl() }
    single<PostsRepository> { PostsRepositoryImpl(get()) }
    factory { SendMessageUseCase(get()) }

}

val viewModelModule = module {
    viewModel { ScreenOneViewModel(get()) }
}

val firebaseModule = module {
    single<FirebaseAuthDataSource> { FirebaseFirebaseAuthDataSourceImpl() }
    single<FirebaseAuthRepository> { FirebaseFirebaseAuthRepositoryImpl(get()) }
    factory { SignInWithEmailUseCase(get()) }
    factory { SignUpWithEmailUseCase(get()) }
    factory { SignInWithGoogleUseCase(get()) }
}
