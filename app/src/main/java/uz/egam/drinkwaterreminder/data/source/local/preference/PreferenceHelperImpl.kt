package uz.egam.drinkwaterreminder.data.source.local.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import uz.egam.drinkwaterreminder.util.NOT_VERY_ACTIVE
import uz.egam.drinkwaterreminder.util.UnitMetric
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelperImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferenceHelper {
    override suspend fun insertLogIn(logIn: Boolean) {
        sharedPreferences.edit {
            putBoolean("LOG_IN", logIn)
        }
    }

    override suspend fun getLogIn(): Boolean {
        return sharedPreferences.getBoolean("LOG_IN", false)
    }

    override suspend fun insertWeight(weight: Int) {
        sharedPreferences.edit {
            putInt("WEIGHT", weight)
        }
    }

    override suspend fun getWeight(): Int {
        return sharedPreferences.getInt("WEIGHT", 70)
    }

    override suspend fun insertActive(active: String) {
        sharedPreferences.edit {
            putString("ACTIVE", active)
        }
    }

    override suspend fun getActive(): String {
        return sharedPreferences.getString("ACTIVE", NOT_VERY_ACTIVE) ?: NOT_VERY_ACTIVE
    }

    override suspend fun insertDailyTarget(value: Int) {
        sharedPreferences.edit {
            putInt("DAILY_TARGET", value)
        }
    }

    override suspend fun getDailyTarget(): Int {
        return sharedPreferences.getInt("DAILY_TARGET", -1)
    }

    override suspend fun insertUnit(unit: String) {
        sharedPreferences.edit {
            putString("UNIT", unit)
        }
    }

    override suspend fun getUnit(): String {
        return sharedPreferences.getString("UNIT", UnitMetric) ?: UnitMetric
    }
}