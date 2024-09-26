package com.mustafacan.ui_dogs.feature.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.birds.GetBirdsUseCase
import com.mustafacan.domain.usecase.cats.GetCatsUseCase
import com.mustafacan.domain.usecase.dogs.GetDogsUseCase
import com.mustafacan.ui_dogs.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.random.Random


@HiltWorker
class AlarmWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getDogsUseCase: GetDogsUseCase,
    private val getCatsUseCase: GetCatsUseCase,
    private val getBirdsUseCase: GetBirdsUseCase
): CoroutineWorker(context, workerParams) {

    companion object {
        private const val CHANNEL_ID = "notification_channel"
        private const val CHANNEL_NAME = "AlarmChannel"


        private const val NOTIFICATION_TITLE_PARAM = "notification_title"
        private const val NOTIFICATION_MESSAGE_PARAM = "notification_message"

        fun initWorker(context: Context, dog: Dog) {
            val inputData = Data.Builder()
                .putString(NOTIFICATION_TITLE_PARAM, "Alarm Active")
                .putString(NOTIFICATION_MESSAGE_PARAM, "This alarm for " + dog.name)
                .build()

            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

            val workRequest = PeriodicWorkRequestBuilder<AlarmWorker>(16, TimeUnit.MINUTES)
                .setInitialDelay(2, TimeUnit.MINUTES)
                .setInputData(inputData)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork("alarm" + dog.name, ExistingPeriodicWorkPolicy.KEEP, workRequest)
        }
    }

    override suspend fun doWork(): Result {
        Log.d("alarmworker", "running worker " + Calendar.getInstance().timeInMillis)
        val title = inputData.getString(NOTIFICATION_TITLE_PARAM)
        //var message = inputData.getString(NOTIFICATION_MESSAGE_PARAM)

        return withContext(Dispatchers.IO) {
            var notificationBody = "Pet List: "
            val dogsResponse = async { getDogsUseCase.runUseCase() }.await()
            var catsResponse = async { getCatsUseCase.runUseCase() }.await()
            var birdsResponse = async { getBirdsUseCase.runUseCase() }.await()

            (dogsResponse as? ApiResponse.Success<List<Dog>>)?.let {
                notificationBody += "${it.data.size} dogs, "
            }

            (catsResponse as? ApiResponse.Success<List<Cat>>)?.let {
                notificationBody += "${it.data.size} cats, "
            }

            (birdsResponse as? ApiResponse.Success<List<Bird>>)?.let {
                notificationBody += "${it.data.size} birds"
            }

            notificationBody += " (This data is taken from remote server)"

            showNotification(title, notificationBody)
            Result.success()
        }

    }

    private fun showNotification(title: String? = "Animals App", message: String? = "") {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.temperament)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // Display the notification
        notificationManager.notify(Random.nextInt(), notification)
    }



}