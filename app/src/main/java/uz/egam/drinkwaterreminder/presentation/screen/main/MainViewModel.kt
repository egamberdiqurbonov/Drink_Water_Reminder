package uz.egam.drinkwaterreminder.presentation.screen.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.presentation.screen.chart.ChartScreen
import uz.egam.drinkwaterreminder.presentation.screen.settings.SettingsScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import uz.egam.drinkwaterreminder.util.UnitMetric
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator,
) : MainContract.ViewModel, ViewModel() {

    override val container = container<MainContract.UiState, MainContract.SideEffect>(
        MainContract.UiState(
            isLoading = true,
            unit = UnitMetric,
            weeklyAverage = emptyList(),
            dailyAverage = DailyAverageUiData(getDayIndex(), amount = 0, progress = 0f),
            dailyTarget = 3300,
            dailyDrinksList = emptyList()
        )
    )

    override fun onEventDispatcher(intent: MainContract.Intent): Job = intent {
        when (intent) {
            is MainContract.Intent.LoadData -> {
                loadData()
            }

            is MainContract.Intent.ClickChartButton -> {
                appNavigator.push(ChartScreen())
            }

            is MainContract.Intent.ClickSettingsButton -> {
                appNavigator.push(SettingsScreen())
            }

            is MainContract.Intent.ClickAddDrink -> {
                appRepository.insertDrink(drink = intent.drink)
                loadData()
            }
        }
    }

    private fun loadData() = intent {
        val unit = appRepository.getUnit()
        val weeklyAverage = appRepository.getWeeklyAverageAmounts()
        val dailyAverage = weeklyAverage.find { it.dayIndex == getDayIndex() } ?: DailyAverageUiData(getDayIndex(), 0, 0f)
        val target = appRepository.getDailyTarget()
        val dailyDrinksList = appRepository.getTodayDrink()

        reduce {
            state.copy(
                isLoading = false,
                unit = unit,
                weeklyAverage = weeklyAverage,
                dailyAverage = dailyAverage,
                dailyTarget = target,
                dailyDrinksList = dailyDrinksList
            )
        }
    }

    private fun getDayIndex(): Int {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayIndex = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - 1
        return dayIndex;
    }
}