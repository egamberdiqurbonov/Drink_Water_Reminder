package uz.egam.drinkwaterreminder.presentation.screen.main

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData
import uz.egam.drinkwaterreminder.domain.data.DrinkUiData

interface MainContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    sealed interface SideEffect {}

    data class UiState(
        val isLoading: Boolean,
        val unit: String,
        val weeklyAverage: List<DailyAverageUiData>,
        val dailyAverage: DailyAverageUiData,
        val dailyTarget: Int,
        val dailyDrinksList: List<DrinkUiData>
    )

    sealed interface Intent {
        object ClickChartButton : Intent
        object ClickSettingsButton : Intent
        object LoadData : Intent
        data class ClickAddDrink(
            val drink: DrinkEntity
        ): Intent
    }
}