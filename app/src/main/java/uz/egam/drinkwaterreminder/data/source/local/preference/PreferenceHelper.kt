package uz.egam.drinkwaterreminder.data.source.local.preference

interface PreferenceHelper {
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
}