package uz.egam.drinkwaterreminder.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.egam.drinkwaterreminder.data.source.local.preference.PreferenceHelper
import uz.egam.drinkwaterreminder.data.source.local.preference.PreferenceHelperImpl

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceModel {

    @Binds
    fun bindsPreferenceHelper(impl: PreferenceHelperImpl): PreferenceHelper
}