package org.jellyfin.mobilec.player.cast

import com.google.android.exoplayer2.Player

interface ICastPlayerProvider {
    val isCastSessionAvailable: Boolean

    fun get(): Player?
}
