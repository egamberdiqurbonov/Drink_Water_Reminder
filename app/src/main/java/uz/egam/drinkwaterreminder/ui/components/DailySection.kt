package uz.egam.drinkwaterreminder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyAverageCard(
    modifier: Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0x33FFFFFF), shape = RoundedCornerShape(16.dp))
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title.uppercase(),
            fontSize = 16.sp,
            color = Color.Cyan,
            modifier = Modifier
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}