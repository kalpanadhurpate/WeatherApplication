package me.kalpanadhurpate.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.kalpanadhurpate.weatherapp.data.repository.WeatherRepository
import me.kalpanadhurpate.weatherapp.data.model.Latlon
import me.kalpanadhurpate.weatherapp.data.model.WeatherResponse
import me.kalpanadhurpate.weatherapp.utils.UiState
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Latlon>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Latlon>>> = _uiState

    private val _weatherInfo = MutableStateFlow<UiState<WeatherResponse>>(UiState.Loading)

    val weatherInfo: StateFlow<UiState<WeatherResponse>> = _weatherInfo


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getLatLon(city: String) {
        val query = MutableStateFlow("")
        query.value = city
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            query.debounce(300)
                .filter { query ->
                    if (query.isEmpty()) {
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
               // .distinctUntilChanged()
                .flatMapLatest {
                    println("In flatMapLatest")
                    repository.getLatLon(city)
                }
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect { it ->
                    println("size is ${it.size}")
                    _uiState.value = UiState.Success(it)
                }

        }
    }

    fun getWeatherByLatLon(lat: Double, lon: Double) {
        viewModelScope.launch {
           // _weatherInfo.value = UiState.Loading
            repository.getWeatherForLatLon(lat, lon)
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _weatherInfo.value = UiState.Error(e.toString())
                }
                .collect {
                    println("Resultt is ${it}")
                    _weatherInfo.value = UiState.Success(it)

                }

        }
    }

}