package uz.egam.drinkwaterreminder.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.domain.repository.AppRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsAppRepository(impl: AppRepositoryImpl): AppRepository
}