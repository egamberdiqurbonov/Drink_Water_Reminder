package uz.egam.drinkwaterreminder.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        SettingsScreenContent()
    }
}

@Composable
fun SettingsScreenContent() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Settings Screen",
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    SettingsScreenContent()
}