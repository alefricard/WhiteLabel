package br.com.ricardo.whitelabel.domain.usecase.di

import br.com.ricardo.whitelabel.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCreateProductUseCase(useCase: CreateProductUseCaseImpl): CreateProductUseCase

    @Binds
    fun bindGetProductUseCase(useCase: GetProductUseCaseImpl): GetProductUseCase

    @Binds
    fun bindUploadProductUseCase(useCase: UploadProductImageUseCaseImpl): UploadProductImageUseCase
}