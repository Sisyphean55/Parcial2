package com.example.parcia2.presentacion.clima.pronostico

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.parcia2.repository.modelos.ListForecast



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PronosticoView(
    modifier: Modifier = Modifier,
    state : PronosticoEstado,
    onAction: (PronosticoIntencion)->Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(PronosticoIntencion.actualizarClima)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state){
            is PronosticoEstado.Error -> ErrorView(mensaje = state.mensaje)
            is PronosticoEstado.Exitoso -> PronosticoView(Modifier,state.climas)
            PronosticoEstado.Vacio -> LoadingView()
            PronosticoEstado.Cargando -> EmptyView()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun EmptyView(){
    Text(text = "No hay nada que mostrar")
}

@Composable
fun LoadingView(){
    Text(text = "Cargando")
}

@Composable
fun ErrorView(mensaje: String){
    Text(text = mensaje)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PronosticoView(modifier : Modifier = Modifier,
                   climas: List<ListForecast>){
    LazyRow (
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),

    ){
        items(items = climas) {
            val parsedDay = it.dt_txt.split('-')[2].split(' ')[0]
            Row{
                Card(
                    modifier = modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Row(modifier = modifier.padding(20.dp)) {
                        Text(text = "Pronóstico para el día: ", style = MaterialTheme.typography.titleLarge)
                        Text (text = parsedDay, style = MaterialTheme.typography.titleLarge)
                    }
                    Row (modifier = modifier.padding(start = 20.dp), horizontalArrangement = Arrangement.Center){
                        Text(text = it.weather[0].description, style = MaterialTheme.typography.bodyLarge)
                    }
                    Row(modifier = modifier.padding(start = 20.dp)) {
                        Text(text = "Mínima: ")
                        Text (text = "${it.main.temp_min} ºC")
                    }
                    Row(modifier = modifier.padding(start = 20.dp)) {
                        Text(text = "Máxima: ")
                        Text (text = "${it.main.temp_max} ºC")
                    }
                    Row(modifier = modifier.padding(start = 20.dp)) {
                        Text(text = "Humedad: ")
                        Text(text = "${it.main.humidity}%")
                    }
                }
            }
        }
    }
}
