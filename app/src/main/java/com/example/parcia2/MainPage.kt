package com.example.parcia2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.parcia2.presentacion.ciudades.CiudadesPage
import com.example.parcia2.presentacion.clima.ClimaPage
import com.example.parcia2.router.Ruta

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(longitude : Float, latitude : Float) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = Ruta.Ciudades.id
    ) {
        composable(
            route = Ruta.Ciudades.id
        ) {
            CiudadesPage(navHostController)
        }
        composable(
            route = "clima?lat={lat}&lon={lon}&nombre={nombre}",
            arguments =  listOf(
                navArgument("lat") { type= NavType.FloatType },
                navArgument("lon") { type= NavType.FloatType },
                navArgument("nombre") { type= NavType.StringType }
            )
        ) {
            var lat = it.arguments?.getFloat("lat") ?: 0.0f
            var lon = it.arguments?.getFloat("lon") ?: 0.0f
            if(latitude > 0.0f) {
                lat = latitude
                lon = longitude
            }
            val nombre = it.arguments?.getString("nombre") ?: ""
            ClimaPage(navHostController, lat = lat, lon = lon, nombre = nombre)
        }
    }
}