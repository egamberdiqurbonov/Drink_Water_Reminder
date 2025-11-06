package uz.egam.drinkwaterreminder.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.egam.drinkwaterreminder.domain.data.DrinkUiData

@Composable
fun DrinkItems(
    modifier: Modifier,
    drinkItem: DrinkUiData
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = drinkItem.name,
                fontSize = 16.sp,
                color = Color.White
            )

            Text(
                text = drinkItem.time,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 4.dp),
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${drinkItem.amount} ml",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            tint = Color.Cyan,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(32.dp),
            contentDescription = ""
        )
    }

    HorizontalDivider(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
}