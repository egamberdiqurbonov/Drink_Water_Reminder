package uz.egam.drinkwaterreminder.presentation.screen.splash

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface SplashContract {

    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect
    object UIState
    object Intent
}