package uz.egam.drinkwaterreminder.presentation.screen.active

import android.util.Log
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
import uz.egam.drinkwaterreminder.ui.components.ActivityLevelSelector
import uz.egam.drinkwaterreminder.util.NOT_VERY_ACTIVE

class ActiveScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ActiveContract.ViewModel = getViewModel<ActiveViewModel>()
        val uiState = viewModel.collectAsState().value

        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(ActiveContract.Intent.LoadData)
        }

        ActiveContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun ActiveContent(
    uiState: ActiveContract.UIState,
    onEventDispatcher: (ActiveContract.Intent) -> Unit
) {
    var selectedActive by remember { mutableStateOf(uiState.currentActive) }
    LaunchedEffect(uiState.currentActive) {
        Log.d("TTT", "ActiveContent: ${uiState.currentActive}")
        selectedActive = uiState.currentActive
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
                text = "How active\nare you?",
                fontSize = 36.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 52.sp,
                modifier = Modifier
                    .padding(start = 24.dp),
                color = Color.White
            )

            Text(
                text = "We take your activity level into\nconsideration when calculating how\nmuch water you should drink daily",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 22.sp,
                modifier = Modifier
                    .padding(top = 36.dp, start = 24.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            ActivityLevelSelector(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                selectedPosition = selectedActive,

                ) { select ->
                selectedActive = select
                onEventDispatcher(ActiveContract.Intent.ChangeActive(active = selectedActive))
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onEventDispatcher(ActiveContract.Intent.ClickNextButton)
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
fun ActivePreview() {
    ActiveContent(
        uiState = ActiveContract.UIState(currentActive =NOT_VERY_ACTIVE),
        onEventDispatcher = {}
    )
}