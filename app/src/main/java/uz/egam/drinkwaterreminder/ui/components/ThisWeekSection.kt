package uz.egam.drinkwaterreminder.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.egam.drinkwaterreminder.domain.data.DailyAverageUiData

@Composable
fun ThisWeekSection(
    list: List<DailyAverageUiData>
) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0x33FFFFFF), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Text(
            text = "This week",
            fontSize = 16.sp,
            color = Color.White
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            list.forEachIndexed { index, data ->
                Log.d("TTT", "ThisWeekSection: $index ${data.progress}")
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(width = 1.dp, Color(0x80FFFFFF), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Canvas(
                        modifier = Modifier
                            .matchParentSize()
                    ) {
                        drawArc(
                            color = Color.Cyan,
                            startAngle = -90f,
                            sweepAngle = 360 * data.progress,
                            useCenter = false,
                            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black, shape = CircleShape)
                    ) {
                        Text(
                            text = days[index],
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
//
//@Composable
//@Preview(showBackground = false)
//fun Preview(modifier: Modifier = Modifier) {
//    ThisWeekSection()
//}