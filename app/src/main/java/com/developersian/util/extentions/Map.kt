package com.developersian.util.extentions

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

fun projection(googleMap: GoogleMap): Foursome<LatLng, LatLng, LatLng, LatLng> {
	val projection = googleMap.projection.visibleRegion.latLngBounds
	val northEast = LatLng(projection.northeast.latitude, projection.northeast.longitude) // North East of the Screen.
	val northWest = LatLng(projection.southwest.latitude, projection.northeast.longitude) // North West of the Screen.
	val southWest = LatLng(projection.southwest.latitude, projection.southwest.longitude) // South West of the Screen.
	val southEast = LatLng(projection.northeast.latitude, projection.southwest.longitude) // South East of the Screen.
	return Foursome(northEast, northWest, southWest, southEast)
}