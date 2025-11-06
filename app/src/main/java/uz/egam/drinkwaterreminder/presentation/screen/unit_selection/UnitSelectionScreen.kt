package uz.egam.drinkwaterreminder.presentation.screen.unit_selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import uz.egam.drinkwaterreminder.ui.components.UnitOptionButton
import uz.egam.drinkwaterreminder.util.UnitImperial
import uz.egam.drinkwaterreminder.util.UnitMetric

class UnitSelectionScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: UnitSelectionContract.ViewModel = getViewModel<UnitSelectionViewModel>()
        val uiState = viewModel.collectAsState().value
        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(UnitSelectionContract.Intent.LoadData)
        }

        UnitSelectionContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun UnitSelectionContent(
    uiState: UnitSelectionContract.UIState,
    onEventDispatcher: (UnitSelectionContract.Intent) -> Unit
) {
    var selectedUnit by remember { mutableStateOf(uiState.currentUnit) }
    LaunchedEffect(uiState.currentUnit) {
        selectedUnit = uiState.currentUnit
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
                text = "Which do you\nprefer?",
                fontSize = 36.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 52.sp,
                modifier = Modifier
                    .padding(start = 24.dp),
                color = Color.White
            )

            Text(
                text = "Choose the unit system you're most\ncomfortable with. You can change\nthe later in the settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 22.sp,
                modifier = Modifier
                    .padding(top = 36.dp, start = 24.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            UnitOptionButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = UnitMetric,
                isSelected = selectedUnit == UnitMetric,
                onClick = {
                    selectedUnit = UnitMetric
                    onEventDispatcher.invoke(UnitSelectionContract.Intent.ChangeUnit(selectedUnit))
                }
            )

            UnitOptionButton(
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp),
                text = UnitImperial,
                isSelected = selectedUnit == UnitImperial,
                onClick = {
                    selectedUnit = UnitImperial
                    onEventDispatcher.invoke(UnitSelectionContract.Intent.ChangeUnit(selectedUnit))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onEventDispatcher.invoke(UnitSelectionContract.Intent.ClickNextButton)
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
fun UnitSelectionPreview() {
    UnitSelectionContent(
        uiState = UnitSelectionContract.UIState(),
        onEventDispatcher = {}
    )
}