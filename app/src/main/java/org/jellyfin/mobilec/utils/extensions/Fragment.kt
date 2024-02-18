@file:Suppress("NOTHING_TO_INLINE")

package org.jellyfin.mobilec.utils.extensions

import androidx.fragment.app.Fragment
import org.jellyfin.mobilec.MainActivity

inline fun Fragment.requireMainActivity(): MainActivity = requireActivity() as MainActivity
