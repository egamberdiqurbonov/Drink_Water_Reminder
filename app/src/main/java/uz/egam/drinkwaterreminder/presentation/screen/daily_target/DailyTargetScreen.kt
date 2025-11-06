package uz.egam.drinkwaterreminder.presentation.screen.daily_target

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.egam.drinkwaterreminder.ui.components.DailyTargetPicker
import uz.egam.drinkwaterreminder.util.UnitMetric
import uz.egam.drinkwaterreminder.worker.ReminderWorker
import java.util.concurrent.TimeUnit

class DailyTargetScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: DailyTargetContract.ViewModel = getViewModel<DailyTargetViewModel>()
        val uiState: DailyTargetContract.UIState = viewModel.collectAsState().value

        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(DailyTargetContract.Intent.LoadData)
        }

        DailyTargetContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun DailyTargetContent(
    uiState: DailyTargetContract.UIState,
    onEventDispatcher: (DailyTargetContract.Intent) -> Unit
) {
    val context = LocalContext.current
    var selectedTarget by remember { mutableIntStateOf(uiState.currentTarget) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(Color.Black)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))

            Text(
                text = "Daily target\nintake",
                fontSize = 36.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 52.sp,
                modifier = Modifier
                    .padding(start = 24.dp),
                color = Color.White
            )

            Text(
                text = "We calculated your suggested daily\nintake. Feel free to change it. You\ncan reset it to the suggested amount\nin the settings later",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 22.sp,
                modifier = Modifier
                    .padding(top = 36.dp, start = 24.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            DailyTargetPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                unit = uiState.unit,
                initialValue = uiState.currentTarget,
                onValueChange = { value ->
                    selectedTarget = value
                    onEventDispatcher(DailyTargetContract.Intent.ChangeTarget(target = selectedTarget))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onEventDispatcher(
                        DailyTargetContract.Intent.ClickFinishButton(
                            target = selectedTarget
                        )
                    )
                    val request = PeriodicWorkRequestBuilder<ReminderWorker>(30, TimeUnit.MINUTES)
                        .build()
                    val workManager = WorkManager.getInstance(context = context)

                    workManager.cancelAllWork()

                    workManager.enqueueUniquePeriodicWork(
                        uniqueWorkName = "Reminder Work",
                        existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
                         request = request
                    )
                },
                modifier = Modifier
                    .padding(bottom = 36.dp)
                    .size(72.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x33FFFFFF),
                    contentColor = Color.White
                ),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    modifier = Modifier
                        .size(32.dp),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = false)
fun DailyTargetPreview() {
    DailyTargetContent(
        uiState = DailyTargetContract.UIState(unit = UnitMetric, currentTarget = 3300),
        onEventDispatcher = {}
    )
}