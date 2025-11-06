package uz.egam.drinkwaterreminder.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity
import uz.egam.drinkwaterreminder.util.DayAmount

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: DrinkEntity)

    @Query("SELECT * FROM drinkentity")
    suspend fun getAllDrink(): List<DrinkEntity>

    @Query("SELECT * FROM drinkentity WHERE time BETWEEN :startOfDay AND :endOfDay")
    suspend fun getDrinkByDay(startOfDay: Long, endOfDay: Long): List<DrinkEntity>

    //    @Query("""
//        SELECT
//            strftime('%w', datetime(time / 1000, 'unixepoch', 'localtime')) AS dayOfWeek,
//            SUM(amount) as totalAmount
//        FROM DrinkEntity
//        WHERE time BETWEEN :startOfWeek AND :endOfWeek
//        GROUP BY dayOfWeek
//    """)
//    suspend fun getWeeklyAmounts(startOfWeek: Long, endOfWeek: Long): List<DayAmount>
    @Query("""
        SELECT
            CAST(strftime('%w', time / 1000, 'unixepoch', 'localtime') AS INTEGER) AS dayOfWeek,
            SUM(amount) AS totalAmount
        FROM DrinkEntity
        WHERE time BETWEEN :startOfWeek AND :endOfWeek
        GROUP BY dayOfWeek
    """)
    suspend fun getWeeklyAmounts(startOfWeek: Long, endOfWeek: Long): List<DayAmount>


    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)
}