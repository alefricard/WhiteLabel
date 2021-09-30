package br.com.ricardo.whitelabel.data.di

import br.com.ricardo.whitelabel.data.FirebaseProductDataSource
import br.com.ricardo.whitelabel.data.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindProductDataSource(dataSource: FirebaseProductDataSource): ProductDataSource
}