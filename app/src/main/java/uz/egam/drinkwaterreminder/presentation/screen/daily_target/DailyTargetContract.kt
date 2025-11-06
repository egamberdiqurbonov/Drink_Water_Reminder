package uz.egam.drinkwaterreminder.presentation.screen.daily_target

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface DailyTargetContract {

    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect
    data class UIState(
        val unit: String,
        val currentTarget: Int
    )
    sealed interface Intent {
        object LoadData: Intent
        data class ChangeTarget(
            val target: Int
        ): Intent
        data class ClickFinishButton(
            val target: Int
        ): Intent
    }
}