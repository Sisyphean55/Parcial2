package com.istea.appdelclima.repository

import com.example.parcia2.repository.modelos.Ciudad
import com.example.parcia2.repository.modelos.Clima
import com.example.parcia2.repository.modelos.ListForecast

interface Repositorio {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
    suspend fun traerClima(lat: Float, lon: Float) : Clima
    suspend fun traerPronostico(nombre: String) : List<ListForecast>
}