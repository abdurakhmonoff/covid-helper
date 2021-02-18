package com.abdurakhmonoff.covidhelper.ui.stats

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdurakhmonoff.covidhelper.R
import com.abdurakhmonoff.covidhelper.databinding.FragmentStatsBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.snackbar.Snackbar

class StatsFragment : Fragment() {

    private val viewModel: StatsViewModel by lazy {
        val app = requireNotNull(activity).application
        ViewModelProvider(this, StatsViewModelFactory(app)).get(StatsViewModel::class.java)
    }

    private lateinit var binding: FragmentStatsBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stats, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel.country.observe(viewLifecycleOwner, { countryDTO ->
            AlertDialog.Builder(context)
                .setTitle(countryDTO.country)
                .setMessage("Cases: ${countryDTO.cases} (+${countryDTO.todayCases})\nRecovered: ${countryDTO.recovered} (+${countryDTO.todayRecovered})\nDeaths: ${countryDTO.deaths} (+${countryDTO.todayDeaths})\nActive: ${countryDTO.active}")
                .setPositiveButton("OK", null)
                .show()
        })

        binding.countriesRecyclerView.adapter = CountryListAdapter()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        checkDeviceLocationSettingsAndStartGeofence()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu_stats, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.getCurrentLocStats -> getCurrentLocationAndShowDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocationAndShowDialog() {
        if (isPermissionGranted()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    viewModel.getCountryName(location.latitude, location.longitude)
                }
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationAndShowDialog()
            }
        }
    }

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())
        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(
                        activity,
                        REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error getting location settings resolution: " + sendEx.message)
                }
            } else {
                Snackbar.make(
                    binding.recyclerContainer,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndStartGeofence()
                }.show()
            }
        }
    }
}

private const val TAG = "StatsFragment"
private const val REQUEST_CODE = 54
private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29