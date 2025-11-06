package uz.egam.drinkwaterreminder.presentation.screen.active

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface ActiveContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect
    data class UIState(
        val currentActive: String
    )

    sealed interface Intent {
        object LoadData: Intent
        data class ChangeActive(
            val active: String
        ): Intent
        object ClickNextButton: Intent
    }
}