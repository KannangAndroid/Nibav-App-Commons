package com.nibav.camera.services

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.nibav.camera.interfaces.GeoLocationListener
import java.io.IOException
import java.util.Locale

class GPSTracker(private val mContext: Context, private var geoLocationListener: GeoLocationListener) :
    Service(), LocationListener {
    // flag for GPS Status
    private var isGPSEnabled = false

    // flag for network status
    private var isNetworkEnabled = false

    /**
     * GPSTracker isGPSTrackingEnabled getter.
     * Check GPS/wifi is enabled
     */
    // flag for GPS Tracking is enabled
    private var isGPSTrackingEnabled = false
    private var location: Location? = null
    @JvmField
    var latitude = 0.0
    @JvmField
    var longitude = 0.0

    // How many Geocoder should return our GPSTracker
    private var geocoderMaxResults = 1

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    private var provider_info: String? = null

    init {
        getLocation()
    }

    /**
     * Try to get my current location by GPS or Network Provider
     */
    private fun getLocation() {
        try {
            locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager

            //getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            //getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            // Try to get location if you GPS Service is enabled
            if (isGPSEnabled) {
                isGPSTrackingEnabled = true
                Log.d(TAG, "Application use GPS Service")

                /*
                 * This provider determines location using
                 * satellites. Depending on conditions, this provider may take a while to return
                 * a location fix.
                 */provider_info = LocationManager.GPS_PROVIDER
            } else if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                isGPSTrackingEnabled = true
                Log.d(TAG, "Application use Network State to get GPS coordinates")

                /*
                 * This provider determines location based on
                 * availability of cell tower and WiFi access points. Results are retrieved
                 * by means of a network lookup.
                 */provider_info = LocationManager.NETWORK_PROVIDER
            }

            // Application can use GPS or Network Provider
            if (provider_info!!.isNotEmpty()) {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                locationManager!!.requestLocationUpdates(
                    provider_info!!,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                    this
                )
                if (locationManager != null) {
                    location = locationManager!!.getLastKnownLocation(provider_info!!)
                    updateGPSCoordinates()
                }
            }
        } catch (e: Exception) {
            //e.printStackTrace();
            Log.e(TAG, "Impossible to connect to LocationManager", e)
        }
    }

    /**
     * Update GPSTracker latitude and longitude
     */
    private fun updateGPSCoordinates() {
        if (location != null) {
            latitude = location!!.latitude
            longitude = location!!.longitude
        }
    }

    /**
     * GPSTracker latitude getter and setter
     *
     * @return latitude
     */
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }
        return latitude
    }

    /**
     * GPSTracker longitude getter and setter
     *
     * @return
     */
    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }
        return longitude
    }

    /**
     * Stop using GPS listener
     * Calling this method will stop using GPS in your app
     */
    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    /**
     * Get list of address by latitude and longitude
     *
     * @return null or List<Address>
    </Address> */
    private fun getGeocoderAddress(context: Context?): List<Address>? {
        if (location != null) {
            val geocoder = Geocoder(context!!, Locale.ENGLISH)
            try {
                return geocoder.getFromLocation(latitude, longitude, geocoderMaxResults)
            } catch (e: IOException) {
                //e.printStackTrace();
                Log.e(TAG, "Impossible to connect to Geocoder", e)
            }
        }
        return null
    }

    /**
     * Try to get AddressLine
     *
     * @return null or addressLine
     */
    fun getAddressLine(context: Context?): String? {
        val addresses = getGeocoderAddress(context)
        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            address.getAddressLine(0)
        } else {
            null
        }
    }

    /**
     * Try to get Locality
     *
     * @return null or locality
     */
    fun getLocality(context: Context?): String? {
        val addresses = getGeocoderAddress(context)
        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            address.locality
        } else {
            null
        }
    }

    /**
     * Try to get Postal Code
     *
     * @return null or postalCode
     */
    fun getPostalCode(context: Context?): String? {
        val addresses = getGeocoderAddress(context)
        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            address.postalCode
        } else {
            null
        }
    }

    /**
     * Try to get CountryName
     *
     * @return null or postalCode
     */
    fun getCountryName(context: Context?): String? {
        val addresses = getGeocoderAddress(context)
        return if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            address.countryName
        } else {
            null
        }
    }

    override fun onLocationChanged(location: Location) {
        this.location=location
        geoLocationListener.currentLocation(location)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        // Get Class Name
        private val TAG = GPSTracker::class.java.name

        // The minimum distance to change updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1 // 1 minute
                ).toLong()
    }
}
