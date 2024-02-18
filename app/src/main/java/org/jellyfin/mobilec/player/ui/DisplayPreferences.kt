package org.jellyfin.mobilec.player.ui

import org.jellyfin.mobilec.utils.Constants

data class DisplayPreferences(
    val skipBackLength: Long = Constants.DEFAULT_SEEK_TIME_MS,
    val skipForwardLength: Long = Constants.DEFAULT_SEEK_TIME_MS,
)
