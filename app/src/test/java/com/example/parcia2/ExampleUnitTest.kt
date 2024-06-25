package com.example.parcia2

import com.example.parcia2.presentacion.ciudades.CiudadesEstado
import com.example.parcia2.presentacion.ciudades.CiudadesIntencion
import com.example.parcia2.presentacion.ciudades.CiudadesViewModel
import com.example.parcia2.presentacion.ciudades.CiudadesViewModelFactory
import com.example.parcia2.repository.RepositorioMock
import com.example.parcia2.repository.RepositorioMockError
import com.example.parcia2.router.MockRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class ExampleUnitTest2 {

    //Thread para simular la UI
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    //Dependencias mock
    val repositorio = RepositorioMock()
    val router = MockRouter()

    val repositorioError = RepositorioMock()

    //Armo ViewModel
    val factory = CiudadesViewModelFactory(repositorio, router)
    val viewModel = factory.create(CiudadesViewModel::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun ciudadesViewModel_buscar_cor()  = runTest(timeout = 3.seconds) {
        //Creo Valor esperado
        val estadoEsperado = CiudadesEstado.Resultado(listOf(repositorio.cordoba))

        launch(Dispatchers.Main) {
            viewModel.ejecutar(intencion = CiudadesIntencion.Buscar("cor"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, viewModel.uiState)
        }
    }

    @Test
    fun ciudadesViewModel_buscar_plata()  = runTest(timeout = 3.seconds) {
        //Creo Valor esperado
        val estadoEsperado = CiudadesEstado.Resultado(listOf(repositorio.laPlata))

        launch(Dispatchers.Main) {
            viewModel.ejecutar(intencion = CiudadesIntencion.Buscar("plata"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, viewModel.uiState)
        }
    }

    @Test
    fun ciudadesViewModel_buscar_vacio()  = runTest(timeout = 3.seconds) {
        //Creo Valor esperado
        val estadoEsperado = CiudadesEstado.Vacio

        launch(Dispatchers.Main) {
            viewModel.ejecutar(intencion = CiudadesIntencion.Buscar("jojo"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, viewModel.uiState)
        }
    }

    @Test
    fun ciudadesViewModel_buscar_error()  = runTest(timeout = 3.seconds) {

        val repositorioError = RepositorioMockError()

        //Armo ViewModel
        val fa = CiudadesViewModelFactory(repositorioError, router)
        val vm = fa.create(CiudadesViewModel::class.java)

        //Creo Valor esperado
        val estadoEsperado = CiudadesEstado.Error("error desconocido")

        launch(Dispatchers.Main) {
            vm.ejecutar(intencion = CiudadesIntencion.Buscar("jojo"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, vm.uiState)
        }
    }

    @Test
    fun testJojo(){
        assertEquals(4, 2 + 2)
    }
}