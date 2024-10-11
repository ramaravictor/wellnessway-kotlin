package com.example.wellnessway.presentation.location

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.wellnessway.common.Resource
import com.example.wellnessway.domain.use_case.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {
    private val _state = mutableStateOf(LocationState())

    val state: State<LocationState> = _state

    fun getLocations(
        latitude: Double? = null,
        longitude: Double? = null
    ) {
        getLocationUseCase(
            latitude, longitude
        ).onEach {
            when (it) {
                is Resource.Success -> {

                    _state.value = LocationState(list = it.data?.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value =
                        LocationState(error = it.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    _state.value = LocationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}