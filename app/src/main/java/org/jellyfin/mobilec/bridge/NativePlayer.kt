package org.jellyfin.mobilec.bridge

import android.webkit.JavascriptInterface
import kotlinx.coroutines.channels.Channel
import org.jellyfin.mobilec.app.AppPreferences
import org.jellyfin.mobilec.events.ActivityEvent
import org.jellyfin.mobilec.events.ActivityEventHandler
import org.jellyfin.mobilec.player.interaction.PlayOptions
import org.jellyfin.mobilec.player.interaction.PlayerEvent
import org.jellyfin.mobilec.settings.VideoPlayerType
import org.jellyfin.mobilec.utils.Constants
import org.json.JSONObject

@Suppress("unused")
class NativePlayer(
    private val appPreferences: AppPreferences,
    private val activityEventHandler: ActivityEventHandler,
    private val playerEventChannel: Channel<PlayerEvent>,
) {

    @JavascriptInterface
    fun isEnabled() = appPreferences.videoPlayerType == VideoPlayerType.EXO_PLAYER

    @JavascriptInterface
    fun loadPlayer(args: String) {
        PlayOptions.fromJson(JSONObject(args))?.let { options ->
            activityEventHandler.emit(ActivityEvent.LaunchNativePlayer(options))
        }
    }

    @JavascriptInterface
    fun pausePlayer() {
        playerEventChannel.trySend(PlayerEvent.Pause)
    }

    @JavascriptInterface
    fun resumePlayer() {
        playerEventChannel.trySend(PlayerEvent.Resume)
    }

    @JavascriptInterface
    fun stopPlayer() {
        playerEventChannel.trySend(PlayerEvent.Stop)
    }

    @JavascriptInterface
    fun destroyPlayer() {
        playerEventChannel.trySend(PlayerEvent.Destroy)
    }

    @JavascriptInterface
    fun seek(ticks: Long) {
        playerEventChannel.trySend(PlayerEvent.Seek(ticks / Constants.TICKS_PER_MILLISECOND))
    }

    @JavascriptInterface
    fun seekMs(ms: Long) {
        playerEventChannel.trySend(PlayerEvent.Seek(ms))
    }

    @JavascriptInterface
    fun setVolume(volume: Int) {
        playerEventChannel.trySend(PlayerEvent.SetVolume(volume))
    }
}
