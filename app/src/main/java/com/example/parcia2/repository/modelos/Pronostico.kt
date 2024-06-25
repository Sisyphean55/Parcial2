package com.example.parcia2.repository.modelos
import kotlinx.serialization.Serializable
import com.example.parcia2.repository.modelos.Weather

@Serializable
data class ForecastDTO (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListForecast>
)

@Serializable
data class ListForecast(
    val dt: Long,
    val dt_txt: String,
    val main: MainForecast,
    val weather: List<Weather>
)

@Serializable
data class MainForecast(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val sea_level: Long,
    val grnd_level: Long,
    val humidity: Long,
    val temp_kf: Double
)