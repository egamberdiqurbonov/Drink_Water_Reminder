package uz.egam.drinkwaterreminder.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.egam.drinkwaterreminder.R

class SplashScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel: SplashContract.ViewModel = getViewModel<SplashViewModel>()

        LaunchedEffect(Unit) {
            viewModel.onEventDispatcher(SplashContract.Intent)
        }

        SplashContent()
    }
}

@Composable
fun SplashContent() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.water_image),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun SplashPreview() {
    SplashContent()
}