package uz.egam.drinkwaterreminder.presentation.screen.chart

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import uz.egam.drinkwaterreminder.util.UnitMetric
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator
) : ChartContract.ViewModel, ViewModel() {

    override val container = container<ChartContract.UiState, ChartContract.SideEffect>(ChartContract.UiState(unit = UnitMetric, weekData = emptyList()))

    override fun onEventDispatcher(intent: ChartContract.Intent): Job = intent {
        when (intent) {
            is ChartContract.Intent.ClickBackButton -> {
                appNavigator.back()
            }

            is ChartContract.Intent.LoadData -> {
                val unit = appRepository.getUnit()
                val weekData = appRepository.getWeeklyAverageAmounts()
                reduce {
                    state.copy(
                        unit = unit,
                        weekData = weekData
                    )
                }
            }
        }
    }
}