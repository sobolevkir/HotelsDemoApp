package com.sobolevkir.hotels.presentation.screen.hotel_details

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.size.Scale
import com.sobolevkir.hotels.R
import com.sobolevkir.hotels.databinding.FragmentHotelDetailsBinding
import com.sobolevkir.hotels.domain.model.HotelDetails
import com.sobolevkir.hotels.presentation.screen.hotel_details.util.CropBordersTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class HotelDetailsFragment : Fragment(R.layout.fragment_hotel_details) {

    private var _binding: FragmentHotelDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HotelDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHotelDetailsBinding.bind(view)
        val hotelId: Long = arguments?.getLong("id") ?: return
        viewModel.loadData(hotelId)
        initClickListeners()
        observeUiState()
    }

    private fun initClickListeners() {
        binding.toolbar.setNavigationOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressBar.isVisible = state.isLoading
                    state.hotelDetails?.let { hotel -> bindHotelDetails(hotel) }
                    state.errorMessageResId?.let {
                        binding.textErrorMessage.setText(it)
                        binding.textErrorMessage.isVisible = true
                    } ?: run {
                        binding.textErrorMessage.isVisible = false
                    }
                }
            }
        }
    }

    private fun bindHotelDetails(hotel: HotelDetails) {
        binding.apply {
            viewGroupHotelDetails.isVisible = true
            textHotelName.text = hotel.name
            textHotelAddress.text =
                getString(R.string.hotel_address, hotel.address)
            textHotelStars.text =
                buildString {
                    append(getString(R.string.filled_star).repeat(hotel.stars))
                    append(getString(R.string.unfilled_star).repeat(5 - hotel.stars))
                }
            textHotelDistance.text =
                getString(R.string.hotel_distance, hotel.distance)
            textAvailableSuites.text =
                getString(R.string.hotel_available_suites, hotel.availableSuites)
            textCoordinates.text =
                getString(R.string.hotel_coordinates, hotel.lat, hotel.lon)
            imageHotel.load(hotel.image) {
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.image_placeholder)
                transformations(CropBordersTransform())
                scale(Scale.FIT)
            }
        }
        binding.mapView.post {
            initMap(hotel.lat, hotel.lon, hotel.name)
        }
    }

    private fun initMap(lat: Double, lon: Double, name: String) {
        val mapView = binding.mapView
        Configuration.getInstance().userAgentValue = context?.packageName;
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(15.0)
        mapController.setCenter(GeoPoint(lat, lon))

        val marker = Marker(mapView).apply {
            position = GeoPoint(lat, lon)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = name
        }
        mapView.overlays.add(marker)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}