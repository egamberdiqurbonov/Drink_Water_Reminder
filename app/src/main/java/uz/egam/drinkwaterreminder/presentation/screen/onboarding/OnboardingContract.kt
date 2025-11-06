package uz.egam.drinkwaterreminder.presentation.screen.onboarding

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OnboardingContract {

    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent): Job
    }

    object SideEffect {}

    object UIState {}

    object Intent
}