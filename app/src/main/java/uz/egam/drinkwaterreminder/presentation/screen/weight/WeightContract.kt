package uz.egam.drinkwaterreminder.presentation.screen.weight

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface WeightContract {

    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect

    data class UIState(
        val currentWeight: Int,
        val currentUnit: String
    )

    sealed interface Intent {
        object LoadData: Intent
        data class ChangeWeight(
            val weight: Int
        ): Intent
        data class ClickNextButton(
            val weight: Int
        ): Intent
    }
}