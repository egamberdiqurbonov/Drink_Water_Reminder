package uz.egam.drinkwaterreminder.presentation.screen.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.presentation.screen.main.MainScreen
import uz.egam.drinkwaterreminder.presentation.screen.onboarding.OnboardingScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator
) : SplashContract.ViewModel, ViewModel() {
    override val container = container<SplashContract.UIState, SplashContract.SideEffect>(SplashContract.UIState)

    override fun onEventDispatcher(intent: SplashContract.Intent): Job = intent {
        delay(1500)
        if (appRepository.getLogIn()) {
            appNavigator.replace(MainScreen())
        } else appNavigator.replace(OnboardingScreen())
    }
}