package uz.egam.drinkwaterreminder.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.egam.drinkwaterreminder.data.source.local.database.AppDatabase
import uz.egam.drinkwaterreminder.data.source.local.database.dao.DrinkDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @[Provides Singleton]
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database"
        ).build()
    }

    @Provides
    fun provideDrinkDao(appDatabase: AppDatabase): DrinkDao = appDatabase.getDrinkDao()

    @[Provides Singleton]
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MY_SHARED_PREFERENCE", Context.MODE_PRIVATE)
    }
}