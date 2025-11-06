package uz.egam.drinkwaterreminder.presentation.screen.unit_selection

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import uz.egam.drinkwaterreminder.util.UnitMetric

interface UnitSelectionContract {

    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect {}

    data class UIState(
        val currentUnit: String = UnitMetric
    )

    sealed interface Intent {
        object LoadData: Intent
        object ClickNextButton: Intent
        data class ChangeUnit(
            val unit: String
        ): Intent
    }
}