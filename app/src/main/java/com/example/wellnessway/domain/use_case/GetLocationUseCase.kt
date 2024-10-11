package com.example.wellnessway.domain.use_case


import com.example.wellnessway.common.Resource
import com.example.wellnessway.domain.model.LocationResponse
import com.example.wellnessway.domain.repository.WellnessWayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repository: WellnessWayRepository
) {
    operator fun invoke(
        latitude: Double? = null,
        longitude: Double? = null
    ): Flow<Resource<LocationResponse>> = flow {
        try {
            emit(Resource.Loading<LocationResponse>())
            val response = repository.getLocation(latitude, longitude)
            emit(Resource.Success<LocationResponse>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<LocationResponse>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<LocationResponse>("Couldn't reach server. Check your internet connection."))
        }

    }
}