package com.anangkur.jsonschemeapplication.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionUtils {
    fun checkPermission(context: Activity, manifestPermission: String, requestPermissionCode: Int): Boolean{
        if (ContextCompat.checkSelfPermission(context, manifestPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(manifestPermission), requestPermissionCode)
        } else {
            return true
        }
        return false
    }

    fun checkPermission(context: Fragment, manifestPermission: String, requestPermissionCode: Int): Boolean{
        if (ContextCompat.checkSelfPermission(context.requireContext(), manifestPermission) != PackageManager.PERMISSION_GRANTED) {
            context.requestPermissions(arrayOf(manifestPermission), requestPermissionCode)
        } else {
            return true
        }
        return false
    }
}