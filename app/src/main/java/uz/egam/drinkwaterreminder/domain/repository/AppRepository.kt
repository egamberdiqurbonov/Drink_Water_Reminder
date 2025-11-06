package uz.egam.drinkwaterreminder.domain.repository

import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData
import uz.egam.drinkwaterreminder.domain.data.DrinkUiData

interface AppRepository {
    suspend fun insertLogIn(logIn: Boolean)
    suspend fun getLogIn(): Boolean
    suspend fun insertWeight(weight: Int)
    suspend fun getWeight(): Int
    suspend fun insertActive(active: String)
    suspend fun getActive(): String
    suspend fun insertDailyTarget(value: Int)
    suspend fun getDailyTarget(): Int
    suspend fun insertUnit(unit: String)
    suspend fun getUnit(): String
    suspend fun insertDrink(drink: DrinkEntity)
    suspend fun getTodayDrink(): List<DrinkUiData>
    suspend fun getWeeklyAverageAmounts(): List<DailyAverageUiData>
}