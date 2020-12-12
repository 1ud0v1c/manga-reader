package com.ludovic.vimont.mangareader.helper

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.ludovic.vimont.mangareader.R

object ViewHelper {
    fun transparentStatusBar(activity: Activity, isTransparent: Boolean) {
        if (isTransparent) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
            activity.actionBar?.hide()
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        or View.SYSTEM_UI_FLAG_VISIBLE)
            } else {
                activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
            }
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.purple_500)
            activity.actionBar?.show()
        }
    }
}