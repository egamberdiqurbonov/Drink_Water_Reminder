package uz.egam.drinkwaterreminder.presentation.screen.onboarding

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel

class OnboardingScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: OnboardingContract.ViewModel = getViewModel<OnBoardingViewModel>()

        OnboardingScreenContent(
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun OnboardingScreenContent(
    onEventDispatcher: (OnboardingContract.Intent) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))

            Text(
                text = "Remind Water\nReminder",
                fontSize = 36.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 52.sp,
                modifier = Modifier
                    .padding(start = 24.dp),
                color = Color.Cyan
            )

            Text(
                text = "Let's get started by figuring out how\nmuch water you should be drinking\nevery day",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 22.sp,
                modifier = Modifier
                    .padding(top = 36.dp, start = 24.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    Log.d("TTT", "OnboardingScreenContent: click")
                    onEventDispatcher.invoke(OnboardingContract.Intent)
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
@Preview(showBackground = true)
fun OnboardingScreenPreview() {
    OnboardingScreenContent(
        onEventDispatcher = {}
    )
}

