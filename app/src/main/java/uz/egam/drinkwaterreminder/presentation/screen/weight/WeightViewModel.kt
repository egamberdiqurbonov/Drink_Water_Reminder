package uz.egam.drinkwaterreminder.presentation.screen.weight

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.presentation.screen.active.ActiveScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import uz.egam.drinkwaterreminder.util.UnitImperial
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator
) : WeightContract.ViewModel, ViewModel() {
    override val container = container<WeightContract.UIState, WeightContract.SideEffect>(
        initialState = WeightContract.UIState(
            currentWeight = 90,
            currentUnit = UnitImperial
        )
    )

    private fun loadInitialValue() = intent {
        val currentWeight = appRepository.getWeight()
        val currentUnit = appRepository.getUnit()
        reduce {
            state.copy(currentWeight = currentWeight, currentUnit = currentUnit)
        }
    }

    override fun onEventDispatcher(intent: WeightContract.Intent): Job = intent {
        when(intent) {
            is WeightContract.Intent.LoadData -> {
                loadInitialValue()
            }
            is WeightContract.Intent.ChangeWeight -> {
                appRepository.insertWeight(weight = intent.weight)
            }
            is WeightContract.Intent.ClickNextButton -> {
                appRepository.insertWeight(weight = intent.weight)
                appNavigator.push(ActiveScreen())
            }
        }
    }
}