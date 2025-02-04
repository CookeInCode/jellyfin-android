@file:Suppress("NOTHING_TO_INLINE")

package org.jellyfin.mobilec.utils.extensions

import androidx.annotation.CheckResult
import org.jellyfin.mobilec.utils.Constants

@get:CheckResult
val IntRange.width: Int
    get() = endInclusive - start

@CheckResult
fun IntRange.scaleInRange(percent: Int): Int {
    return start + width * percent / Constants.PERCENT_MAX
}
