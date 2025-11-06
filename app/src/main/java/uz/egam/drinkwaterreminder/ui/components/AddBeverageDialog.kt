package uz.egam.drinkwaterreminder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.egam.drinkwaterreminder.util.UnitMetric
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBeverageBottomSheet(
    unit: String,
    onAddClick: (size: Int, beverage: String) -> Unit,
    onDismiss: () -> Unit
) {
    val sizes = if (unit == UnitMetric) listOf(100, 125, 150, 200, 250, 300, 350) else listOf(3, 5, 7, 9, 10, 12, 14)
    val beverages = listOf("Water", "Tea", "Coffee", "Juice", "Soda", "Energy Drink")

    var selectedSize by remember { mutableStateOf<Int?>(null) }
    var selectedBeverage by remember { mutableStateOf<String?>(null) }

    val isAddEnabled = selectedSize != null && selectedBeverage != null

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .navigationBarsPadding()
        ) {
            Text(
                text = "Add Beverage",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(Date()),
                color = Color(0x80FFFFFF),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "SIZE",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(sizes.size) { size ->
                        val isSelected = selectedSize == sizes[size]
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSize = sizes[size] },
                            label = { Text(text = "${sizes[size]} ${if (unit == UnitMetric) "ml" else "fl oz"}") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Cyan,
                                selectedLabelColor = Color.Black,
                            ),
                            modifier = Modifier
                                .padding(start = if (size == 0) 16.dp else 0.dp, end = if (size == sizes.lastIndex) 16.dp else 0.dp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "BEVERAGE",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(beverages.size) { bev ->
                        val isSelected = selectedBeverage == beverages[bev]
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedBeverage = beverages[bev] },
                            label = { Text(beverages[bev]) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Cyan,
                                selectedLabelColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(start = if (bev == 0) 16.dp else 0.dp, end = if (bev == beverages.lastIndex) 16.dp else 0.dp)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton (
                onClick = {
                    if (selectedSize != null && selectedBeverage != null) {
                        onAddClick(selectedSize!!, selectedBeverage!!)
                    }
                },
                enabled = isAddEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Cyan,
                    disabledContainerColor = Color.Gray,
                    contentColor = Color.Black,
                    disabledContentColor = Color(0xCCFFFFFF)
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Add")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
