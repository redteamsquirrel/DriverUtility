package net.mastery.driverutility.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Driver(
  val firstName: String? = null,
  val lastName: String?= null,
  val phoneNumber: String? = null,
  val details: DriverDetails? = null
): Parcelable

@Parcelize
@Serializable
data class DriverDetails(
  val trailerType: String? = null,
  val trailerLength: Float?= null,
  val trailerHeight: Float? = null,
  val trailerWidth: Float? = null,
  val plateNumber: String? = null,
  val currentLocation: LocationLatLon? = null
): Parcelable

@Parcelize
@Serializable
data class LocationLatLon(
  val latitude: Double? = null,
  val longitude: Double?= null
): Parcelable