package com.smith.ryan.dttest.feature.core.extensions

/**
 * Removes all occurrences of 'amp;' from the calling String
 *
 * @return Calling String after completing the removal
 */
fun String.removeAllAmpersands() = replace("amp;", "")

fun String.doesNotContain(string: String): Boolean = !contains(string)