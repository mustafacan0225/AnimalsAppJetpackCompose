package com.mustafacan.ui_reminder.feature.worker

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
import com.mustafacan.domain.usecase.dogs.api_usecase.GetDogsUseCase
import com.mustafacan.ui_reminder.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getDogsUseCase: GetDogsUseCase,
    private val getCatsUseCase: GetCatsUseCase,
    private val getBirdsUseCase: GetBirdsUseCase
) : CoroutineWorker(context, workerParams) {

    enum class ReminderType {
        REMINDER_DOGS,
        REMINDER_CATS,
        REMINDER_BIRDS
    }

    companion object {
        private const val CHANNEL_ID = "notification_channel"
        private const val CHANNEL_NAME = "ReminderChannel"
        private const val REMINDER_TYPE_PARAM = "reminder_type"
        private const val NOTIFICATION_TITLE_RES_ID_PARAM = "title_res_id"
        private const val NOTIFICATION_ICON_RES_ID_PARAM = "icon_res_id"

        fun cancelWorker(context: Context, uniqueWorkName: String) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }

        fun initWorker(
            context: Context,
            reminderType: ReminderType,
            notificationTitleResId: Int,
            notificationIconResId: Int
        ) {
            val inputData = Data.Builder()
                .putString(REMINDER_TYPE_PARAM, reminderType.name)
                .putInt(NOTIFICATION_TITLE_RES_ID_PARAM, notificationTitleResId)
                .putInt(NOTIFICATION_ICON_RES_ID_PARAM, notificationIconResId)
                .build()

            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

            val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(15, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setInputData(inputData)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(reminderType.name,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }

    override suspend fun doWork(): Result {
        Log.d("reminderworker", "running worker " + Calendar.getInstance().timeInMillis)
        return withContext(Dispatchers.IO) {
            var notificationBody = ""
            val reminderType = inputData.getString(REMINDER_TYPE_PARAM)
            when (reminderType) {
                ReminderType.REMINDER_DOGS.name -> {
                    val response = getDogsUseCase.runUseCase() as? ApiResponse.Success<List<Dog>>
                    response?.let {
                        notificationBody += applicationContext.getString(
                            R.string.reminder_message_dogs,
                            it.data.size.toString()
                        )
                    }
                }

                ReminderType.REMINDER_CATS.name -> {
                    val response = getCatsUseCase.runUseCase() as? ApiResponse.Success<List<Cat>>
                    response?.let {
                        notificationBody += applicationContext.getString(
                            R.string.reminder_message_cats,
                            it.data.size.toString()
                        )
                    }
                }

                ReminderType.REMINDER_BIRDS.name -> {
                    val response = getBirdsUseCase.runUseCase() as? ApiResponse.Success<List<Bird>>
                    response?.let {
                        notificationBody += applicationContext.getString(
                            R.string.reminder_message_birds,
                            it.data.size.toString()
                        )
                    }
                }
            }
            /*val dogsResponse = async { getDogsUseCase.runUseCase() }.await()
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
            }*/
            showNotification(
                inputData.getInt(NOTIFICATION_TITLE_RES_ID_PARAM, R.string.reminder),
                inputData.getInt(NOTIFICATION_ICON_RES_ID_PARAM, R.drawable.temperament),
                notificationBody
            )

            Result.success()
        }

    }

    private fun showNotification(titleResId: Int, iconResId: Int, message: String) {
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
            .setContentTitle(applicationContext.getString(titleResId))
            .setContentText(message)
            .setSmallIcon(iconResId)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // Display the notification
        notificationManager.notify(Random.nextInt(), notification)
    }


}