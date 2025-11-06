package uz.egam.drinkwaterreminder.presentation.screen.chart

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData

interface ChartContract {

    interface ViewModel: ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect
    data class UiState(
        val unit: String,
        val weekData: List<DailyAverageUiData>
    )
    sealed interface Intent {
        object ClickBackButton: Intent
        object LoadData: Intent
    }
}