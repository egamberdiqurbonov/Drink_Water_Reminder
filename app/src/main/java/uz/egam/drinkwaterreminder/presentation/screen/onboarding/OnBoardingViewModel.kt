package uz.egam.drinkwaterreminder.presentation.screen.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.presentation.screen.unit_selection.UnitSelectionScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : OnboardingContract.ViewModel, ViewModel() {
    override val container = container<OnboardingContract.UIState, OnboardingContract.SideEffect>(OnboardingContract.UIState)

    override fun onEventDispatcher(intent: OnboardingContract.Intent): Job = intent {
        Log.d("TTT", "onEventDispatcher: ")
        appNavigator.push(UnitSelectionScreen())
    }
}