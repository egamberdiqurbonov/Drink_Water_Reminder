package uz.egam.drinkwaterreminder.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.egam.drinkwaterreminder.data.source.local.database.dao.DrinkDao
import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity

@Database(entities = [DrinkEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDrinkDao(): DrinkDao
}