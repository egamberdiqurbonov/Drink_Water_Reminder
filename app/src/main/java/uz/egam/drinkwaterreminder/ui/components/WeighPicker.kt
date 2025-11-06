package uz.egam.drinkwaterreminder.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.egam.drinkwaterreminder.util.UnitMetric

@SuppressLint("FrequentlyChangingValue")
@Composable
fun WeightPicker(
    modifier: Modifier,
    unit: String,
    initialValue: Int = if (unit == UnitMetric) 70 else 140,
    range: IntRange = if (unit == UnitMetric) 10..200 else 20..400,
    onValueChange: (Int) -> Unit = {}
) {

    val list = remember { range.toList() }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = list.indexOf(initialValue)
    )

    LaunchedEffect(initialValue) {
        listState.scrollToItem(list.indexOf(initialValue))
    }
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)


    LazyColumn(
        modifier = modifier,
        state = listState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(2) {
            Text(text = "", modifier = Modifier.padding(vertical = 16.dp))
        }

        items(list.size) { index ->
            val value = list[index]
            val selected = listState.firstVisibleItemIndex == index
            Text(
                text = "$value ${if (unit == UnitMetric) " kg" else " Ib"}",
                fontSize = if (selected) 28.sp else 20.sp,
                color = Color.White,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(2) {
            Text(text = "", modifier = Modifier.padding(vertical = 16.dp))
        }

    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val selectedIndex = listState.firstVisibleItemIndex
            if (selectedIndex in list.indices) {
                onValueChange(list[selectedIndex])
            }
        }
    }
}
