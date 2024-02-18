package org.jellyfin.mobilec.utils

import android.util.Log
import org.jellyfin.mobilec.BuildConfig
import timber.log.Timber

class JellyTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.DEBUG || priority >= Log.INFO) super.log(priority, tag, message, t)
    }
}
