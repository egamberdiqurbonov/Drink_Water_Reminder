package uz.egam.drinkwaterreminder.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigationDispatcher
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import uz.egam.drinkwaterreminder.ui.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindsAppNavigator(impl: AppNavigationDispatcher): AppNavigator

    @Binds
    fun bindsNavigationHandler(impl: AppNavigationDispatcher): NavigationHandler
}