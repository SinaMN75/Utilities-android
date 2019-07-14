package com.satya.utilites.extentions

enum class UpdateStatus {
	NO_UPDATE_AVAILABLE,
	NORMAL_UPDATE_AVAILABLE,
	FORCE_UPDATE_AVAILABLE,
	ERROR
}

fun checkFoUpdate(currentVersion: String, latestVersion: String, latestSafeVersion: String): UpdateStatus {
	if (currentVersion == latestVersion) return UpdateStatus.NO_UPDATE_AVAILABLE
	if (currentVersion < latestSafeVersion) return UpdateStatus.FORCE_UPDATE_AVAILABLE
	return if (currentVersion < latestVersion) UpdateStatus.FORCE_UPDATE_AVAILABLE
	else UpdateStatus.ERROR
}