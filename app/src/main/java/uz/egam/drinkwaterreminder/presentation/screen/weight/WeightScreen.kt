package uz.egam.drinkwaterreminder.presentation.screen.weight

import android.util.Log
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
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.egam.drinkwaterreminder.ui.components.WeightPicker
import uz.egam.drinkwaterreminder.util.UnitMetric

class WeightScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: WeightContract.ViewModel = getViewModel<WeightViewModel>()
        val uiState: WeightContract.UIState = viewModel.collectAsState().value

        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(WeightContract.Intent.LoadData)
        }
        WeightContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun WeightContent(
    uiState: WeightContract.UIState,
    onEventDispatcher: (WeightContract.Intent) -> Unit
) {
    var currentUnit by remember { mutableStateOf(uiState.currentUnit) }
    var selectedWeight by remember { mutableIntStateOf(uiState.currentWeight) }

    LaunchedEffect(uiState.currentWeight, uiState.currentUnit) {
        Log.d("TTT", "WeightContent: ${uiState.currentUnit}")
        Log.d("TTT", "WeightContent: ${uiState.currentWeight}")
        selectedWeight = uiState.currentWeight
        currentUnit = uiState.currentUnit
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(Color.Black)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))

            Text(
                text = "What's your\nweight?",
                fontSize = 36.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 52.sp,
                modifier = Modifier
                    .padding(start = 24.dp),
                color = Color.White
            )

            Text(
                text = "We use your weight to calculate how\nmuch water you should drink every\nday.",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 22.sp,
                modifier = Modifier
                    .padding(top = 36.dp, start = 24.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            WeightPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                initialValue = uiState.currentWeight,
                unit = currentUnit,
                onValueChange = { value ->
                    selectedWeight = value
                    onEventDispatcher.invoke(WeightContract.Intent.ChangeWeight(selectedWeight))
                },
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onEventDispatcher.invoke(
                        WeightContract.Intent.ClickNextButton(
                            weight = selectedWeight
                        )
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
                    imageVector = Icons.Outlined.ChevronRight,
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
fun WeightPreview() {
    WeightContent(
        uiState = WeightContract.UIState(currentWeight = 70, currentUnit = UnitMetric),
        onEventDispatcher = {}
    )
}