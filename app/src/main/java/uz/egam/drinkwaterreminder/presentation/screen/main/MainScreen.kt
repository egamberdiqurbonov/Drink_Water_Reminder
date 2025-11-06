package uz.egam.drinkwaterreminder.presentation.screen.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity
import uz.egam.drinkwaterreminder.ui.components.AddBeverageBottomSheet
import uz.egam.drinkwaterreminder.ui.components.DailyAverageCard
import uz.egam.drinkwaterreminder.ui.components.DrinkItems
import uz.egam.drinkwaterreminder.ui.components.ThisWeekSection
import uz.egam.drinkwaterreminder.util.UnitMetric


class MainScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: MainContract.ViewModel = getViewModel<MainViewModel>()
        val uiState: MainContract.UiState = viewModel.collectAsState().value

        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(MainContract.Intent.LoadData)
        }

        MainScreenContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun MainScreenContent(
    uiState: MainContract.UiState,
    onEventDispatcher: (MainContract.Intent) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        AddBeverageBottomSheet(
            unit = uiState.unit,
            onAddClick = { size, beverage ->
                onEventDispatcher(
                    MainContract.Intent.ClickAddDrink(
                        drink = DrinkEntity(
                            id = 0L,
                            name = beverage,
                            amount = size,
                            time = System.currentTimeMillis(),
                            isFavorite = 0
                        )
                    )
                )
                showBottomSheet = false
            },
            onDismiss = { showBottomSheet = false }
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        onEventDispatcher.invoke(MainContract.Intent.ClickChartButton)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Analytics,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(32.dp),
                        contentDescription = "",
                    )
                }

//                IconButton(
//                    onClick = {
//                        onEventDispatcher.invoke(MainContract.Intent.ClickSettingsButton)
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.Settings,
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(32.dp),
//                        contentDescription = "",
//                    )
//                }
            }

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        ThisWeekSection(uiState.weeklyAverage)
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .border(width = 1.dp, Color(0x1AFFFFFF), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {

                                Canvas(
                                    modifier = Modifier
                                        .matchParentSize()
                                ) {
                                    drawArc(
                                        color = Color.Cyan,
                                        startAngle = -90f,
                                        sweepAngle = 360 * uiState.dailyAverage.progress,
                                        useCenter = false,
                                        style = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                }

                                Text(
                                    text = "${uiState.dailyAverage.amount} ${if (uiState.unit == UnitMetric) "ml" else "fl oz"} ",
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .width(24.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                DailyAverageCard(
                                    modifier = Modifier,
                                    title = "Daily Average",
                                    value = "${uiState.dailyAverage.amount} ${if (uiState.unit == UnitMetric) "ml" else "fl oz"} "
                                )

                                DailyAverageCard(
                                    modifier = Modifier
                                        .padding(top = 8.dp),
                                    title = "Daily Target",
                                    value = "${uiState.dailyTarget} ${if (uiState.unit == UnitMetric) "ml" else "fl oz"} "
                                )
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                showBottomSheet = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, start = 36.dp, end = 36.dp)
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Cyan,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = "Add Beverage",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Today",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 24.dp)
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .background(Color(0x33FFFFFF), shape = RoundedCornerShape(16.dp))
                        ) {
                            uiState.dailyDrinksList.forEachIndexed { index, item ->
                                DrinkItems(
                                    modifier = Modifier,
                                    drinkItem = item
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun MainScreenContentPreview() {
//    MainScreenContent(
//        uiState = MainContract.UiState(isLoading = false),
//        onEventDispatcher = {}
//    )
//}