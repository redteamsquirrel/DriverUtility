package net.mastery.driverutility.extensions

import android.view.View
import net.mastery.driverutility.SafeClickListener


fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
  val safeClickListener = SafeClickListener {
    onSafeClick(it)
  }
  setOnClickListener(safeClickListener)
}