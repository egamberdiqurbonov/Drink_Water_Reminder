package uz.egam.drinkwaterreminder.presentation.screen.active

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.presentation.screen.daily_target.DailyTargetScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import uz.egam.drinkwaterreminder.util.NOT_VERY_ACTIVE
import javax.inject.Inject

@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator
) : ActiveContract.ViewModel, ViewModel() {
    override val container = container<ActiveContract.UIState, ActiveContract.SideEffect>(
        initialState = ActiveContract.UIState(
            currentActive = NOT_VERY_ACTIVE
        )
    )

    private fun loadCurrentActive() = intent {
        val currentActive = appRepository.getActive()

        reduce {
            state.copy(currentActive = currentActive)
        }
    }

    override fun onEventDispatcher(intent: ActiveContract.Intent): Job = intent {
        when(intent) {
            is ActiveContract.Intent.LoadData -> {
                loadCurrentActive()
            }
            is ActiveContract.Intent.ChangeActive -> {
                appRepository.insertActive(active = intent.active)
            }
            is ActiveContract.Intent.ClickNextButton -> {
                appNavigator.push(DailyTargetScreen())
            }
        }
    }
}