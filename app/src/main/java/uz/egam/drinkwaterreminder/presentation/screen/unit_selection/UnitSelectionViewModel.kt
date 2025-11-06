package uz.egam.drinkwaterreminder.presentation.screen.unit_selection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.presentation.screen.weight.WeightScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class UnitSelectionViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator
) : UnitSelectionContract.ViewModel, ViewModel() {

    override val container = container<UnitSelectionContract.UIState, UnitSelectionContract.SideEffect>(
        initialState = UnitSelectionContract.UIState()
    )

    private fun loadInitialUnit() = intent {
        val currentUnit = appRepository.getUnit()
        reduce {
            state.copy(currentUnit = currentUnit)
        }
    }

    override fun onEventDispatcher(intent: UnitSelectionContract.Intent): Job = intent {
        when (intent) {
            is UnitSelectionContract.Intent.LoadData -> {
                loadInitialUnit()
            }

            is UnitSelectionContract.Intent.ChangeUnit -> {
                appRepository.insertUnit(intent.unit)
            }

            is UnitSelectionContract.Intent.ClickNextButton -> {
                appNavigator.push(WeightScreen())
            }
        }
    }
}