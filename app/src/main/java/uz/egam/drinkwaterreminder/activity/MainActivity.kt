package uz.egam.drinkwaterreminder.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import uz.egam.drinkwaterreminder.presentation.screen.splash.SplashScreen
import uz.egam.drinkwaterreminder.ui.navigation.NavigationHandler
import uz.egam.drinkwaterreminder.ui.theme.DrinkWaterReminderTheme
import uz.egam.drinkwaterreminder.util.NOTIFICATION_CHANNEL_ID
import uz.egam.drinkwaterreminder.util.NOTIFICATION_CHANNEL_NAME
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkWaterReminderTheme {
                Navigator(SplashScreen()) { navigator ->
                    LaunchedEffect(navigator) {
                        navigationHandler.screenState.collect { it(navigator) }
                    }
                    SlideTransition(navigator)
                }
            }
        }

        createNotificationChannel()
        checkPermission()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }

    private val postNotificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.d("TTT", "Post notification permission granted")
        } else Log.d("TTT", "Post notification permission granted")
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                postNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}