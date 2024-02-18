package org.jellyfin.mobilec

import android.app.Application
import android.webkit.WebView
import org.jellyfin.mobilec.app.apiModule
import org.jellyfin.mobilec.app.applicationModule
import org.jellyfin.mobilec.data.databaseModule
import org.jellyfin.mobilec.utils.JellyTree
import org.jellyfin.mobilec.utils.isWebViewSupported
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class JellyfinApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Setup logging
        Timber.plant(JellyTree())

        if (BuildConfig.DEBUG) {
            // Enable WebView debugging
            if (isWebViewSupported()) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }

        startKoin {
            androidContext(this@JellyfinApplication)
            fragmentFactory()

            modules(
                applicationModule,
                apiModule,
                databaseModule,
            )
        }
    }
}
