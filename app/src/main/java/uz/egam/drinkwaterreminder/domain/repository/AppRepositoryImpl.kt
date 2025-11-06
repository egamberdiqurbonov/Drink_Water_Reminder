package uz.egam.drinkwaterreminder.domain.repository

import android.util.Log
import uz.egam.drinkwaterreminder.data.source.local.database.dao.DrinkDao
import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity
import uz.egam.drinkwaterreminder.data.source.local.preference.PreferenceHelper
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData
import uz.egam.drinkwaterreminder.domain.data.DrinkUiData
import uz.egam.drinkwaterreminder.domain.mapper.toDrinkUiData
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class AppRepositoryImpl @Inject constructor(
    private val drinkDao: DrinkDao,
    private val preferenceHelper: PreferenceHelper
) : AppRepository {

    override suspend fun insertLogIn(logIn: Boolean) {
        preferenceHelper.insertLogIn(logIn = logIn)
    }

    override suspend fun getLogIn(): Boolean {
        return preferenceHelper.getLogIn()
    }

    override suspend fun insertWeight(weight: Int) {
        preferenceHelper.insertWeight(weight = weight)
    }

    override suspend fun getWeight(): Int {
        return preferenceHelper.getWeight()
    }

    override suspend fun insertActive(active: String) {
        preferenceHelper.insertActive(active = active)
    }

    override suspend fun getActive(): String {
        return preferenceHelper.getActive()
    }

    override suspend fun insertDailyTarget(value: Int) {
        preferenceHelper.insertDailyTarget(value = value)
    }

    override suspend fun getDailyTarget(): Int {
        return preferenceHelper.getDailyTarget()
    }

    override suspend fun insertUnit(unit: String) {
        preferenceHelper.insertUnit(unit = unit)
    }

    override suspend fun getUnit(): String {
        return preferenceHelper.getUnit()
    }

    override suspend fun insertDrink(drink: DrinkEntity) {
        drinkDao.insertDrink(drink = drink)
    }

    override suspend fun getTodayDrink(): List<DrinkUiData> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis


        return drinkDao.getDrinkByDay(
            startOfDay = startOfDay,
            endOfDay = endOfDay,
        ).map { it.toDrinkUiData() }
    }

    override suspend fun getWeeklyAverageAmounts(): List<DailyAverageUiData> {
        val calendar = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY

            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfWeek = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_YEAR, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfWeek = calendar.timeInMillis

        val dailyTarget = getDailyTarget()
        val results = drinkDao.getWeeklyAmounts(startOfWeek, endOfWeek)

        Log.d("TTT", "getWeeklyAverageAmounts: $startOfWeek ---- $endOfWeek")

        Log.d("TTT", "getWeeklyAverageAmounts: $results")

        val list = mutableListOf<DailyAverageUiData>()
        for (i in 0 ..6) {
            if (i == 6) {
                val dayResult = results.find { it.dayOfWeek.toInt() == 0 }
                val amount = dayResult?.totalAmount ?: 0
                val progress = if (dailyTarget == 0) 0f else (amount.toFloat() / dailyTarget).coerceIn(0f, 1f)
                val data = DailyAverageUiData(dayIndex = 6, amount = amount, progress = progress)
                list.add(data)
            } else {
                val dayResult = results.find { it.dayOfWeek.toInt() == i+1 }
                val amount = dayResult?.totalAmount ?: 0
                val progress = if (dailyTarget == 0) 0f else (amount.toFloat() / dailyTarget).coerceIn(0f, 1f)
                val data = DailyAverageUiData(dayIndex = i+1, amount = amount, progress = progress)
                list.add(data)
            }
        }

        return list;
    }
}