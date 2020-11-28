package com.smith.ryan.dttest.feature.coreui.extensions

import android.view.View
import androidx.annotation.MainThread
import com.google.android.material.snackbar.Snackbar

/**
 * Shows an indefinite Snackbar using the calling String as the message.
 *
 * @return Snackbar that was shown to allow for dismissal handling.
 */
@MainThread
fun String.showIndefiniteSnackbar(view: View): Snackbar {
    val snack = Snackbar.make(view, this, Snackbar.LENGTH_INDEFINITE)
    snack.show()
    return snack
}

@MainThread
fun String.showLongSnackbar(view: View) {
    Snackbar.make(view, this, Snackbar.LENGTH_LONG).show()
}