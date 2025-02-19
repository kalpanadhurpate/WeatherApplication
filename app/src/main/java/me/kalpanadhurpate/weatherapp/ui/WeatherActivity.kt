package me.kalpanadhurpate.weatherapp.ui

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kalpanadhurpate.weatherapp.data.model.Latlon
import me.kalpanadhurpate.weatherapp.databinding.ActivityMainBinding
import me.kalpanadhurpate.weatherapp.utils.UiState
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var adapter: CityListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        setupViewModel()
        setUpUI()
        initListener()
        setUpObserverForLatLon()
        setUpObserverForWeather()

    }


    private fun setupViewModel() {
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

    }

    private fun setUpUI() {
        val recyclerView = viewBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        adapter.onItemClick = {
            weatherViewModel.getWeatherByLatLon(it.lat!!, it.lon!!)
            adapter.clearData()
        }
    }

    private fun initListener() {
        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { weatherViewModel.getLatLon(it) }
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun setUpObserverForLatLon() {
        lifecycleScope.launch {
            weatherViewModel.uiState.collect {
                when (it) {
                    is UiState.Success -> {
                        renderList(it.data)
                    }

                    is UiState.Loading -> {
                    }

                    is UiState.Error -> {
                        Toast.makeText(
                            this@WeatherActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setUpObserverForWeather() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherViewModel.weatherInfo.collect {
                    when (it) {
                        is UiState.Success -> {
                            viewBinding.mainTextView.text = it.data.weather[0].main
                            viewBinding.tempValue.text = it.data.main.temp.toString()
                            viewBinding.humidityValue.text = it.data.main.humidity.toString()
                            viewBinding.nameOfCity.text = it.data.name
                            Glide.with(viewBinding.ImageView.context)
                                .load("https://openweathermap.org/img/wn/10d@2x.png")
                                .into(viewBinding.ImageView)
                        }

                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(
                                this@WeatherActivity,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun renderList(latLonList: List<Latlon>) {
        adapter.addData(latLonList)
        adapter.notifyDataSetChanged()
    }

}