package net.mastery.driverutility.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class User(
  val id: Long? = null,
  val username: String? = null,
  val first: String?= null,
  val last: String? = null
): Parcelable