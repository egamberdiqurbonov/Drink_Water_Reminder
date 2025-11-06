package uz.egam.drinkwaterreminder.worker

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uz.egam.drinkwaterreminder.R
import uz.egam.drinkwaterreminder.util.NOTIFICATION_CHANNEL_ID

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(
    appContext = appContext,
    params = params
) {
    private val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {

        Log.d("TTT", "doWork: ")

        val notification = NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Drink Water Reminder")
            .setContentText("Suv ichish vaqti keldi")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (appContext.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification)
        }
            return Result.success()
    }
}