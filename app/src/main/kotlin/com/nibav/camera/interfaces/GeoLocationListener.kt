package com.nibav.camera.interfaces

import android.location.Location

interface GeoLocationListener {
    fun currentLocation(location: Location)
}
