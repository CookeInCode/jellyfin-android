package org.jellyfin.mobilec.utils

class CombinedIntRange(private vararg val ranges: IntRange) {
    operator fun contains(value: Int) = ranges.any { range -> range.contains(value) }
}
