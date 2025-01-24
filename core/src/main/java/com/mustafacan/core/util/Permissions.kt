package com.mustafacan.core.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

 fun Context.hasNotificationPermission() : Boolean{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            return false
        }
    } else {
        // Device does not support required permission
        return true
    }
}

fun Context.openPermissionsPage() {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("android.provider.extra.APP_PACKAGE", this.packageName)

    } else {
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("app_package", this.packageName)
        intent.putExtra("app_uid", this.applicationInfo.uid)
    }
    this.startActivity(intent)
}