package uz.egam.drinkwaterreminder.presentation.screen.daily_target

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.viewmodel.container
import uz.egam.drinkwaterreminder.domain.repository.AppRepository
import uz.egam.drinkwaterreminder.presentation.screen.main.MainScreen
import uz.egam.drinkwaterreminder.ui.navigation.AppNavigator
import uz.egam.drinkwaterreminder.util.UnitMetric
import uz.egam.drinkwaterreminder.worker.ReminderWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DailyTargetViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val appNavigator: AppNavigator
) : DailyTargetContract.ViewModel, ViewModel() {

    override val container = container<DailyTargetContract.UIState, DailyTargetContract.SideEffect>(DailyTargetContract.UIState(unit = UnitMetric, currentTarget = 3300))

    override fun onEventDispatcher(intent: DailyTargetContract.Intent): Job = intent {
        when (intent) {
            is DailyTargetContract.Intent.LoadData -> {
                loadData()
            }

            is DailyTargetContract.Intent.ChangeTarget -> {
                appRepository.insertDailyTarget(value = intent.target)
            }

            is DailyTargetContract.Intent.ClickFinishButton -> {
                appRepository.insertDailyTarget(value = intent.target)
                appRepository.insertLogIn(logIn = true)

                appNavigator.replaceAll(MainScreen())
            }
        }
    }

    private fun loadData() = intent {
        val unit = appRepository.getUnit()
        var target = appRepository.getDailyTarget()
        if (target == -1) {
            target = if (unit == UnitMetric) 3300 else 100
        }
        reduce {
            state.copy(
                unit = unit,
                currentTarget = target
            )
        }
    }
}