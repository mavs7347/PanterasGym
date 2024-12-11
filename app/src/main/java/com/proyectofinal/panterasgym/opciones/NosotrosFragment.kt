package com.proyectofinal.panterasgym.opciones

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.proyectofinal.panterasgym.R

class NosotrosFragment : Fragment() {
    private lateinit var vista: View
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private val PERMISOS_UBICACION = 1 // Código para identificar la solicitud de permisos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_nosotros, container, false)

        // Inicializa el MapView
        mapView = vista.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            googleMap = map
            configurarMapa()
        }

        return vista
    }

    private fun configurarMapa() {
        // Configura las opciones del mapa
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Verifica permisos y activa la ubicación
        verificarPermisosUbicacion()

        // Coordenadas del marcador
        val ubicacion = LatLng(20.653061, -103.278869)

        // Agregar un marcador en las coordenadas especificadas
        googleMap.addMarker(
            MarkerOptions()
                .position(ubicacion)
                .title("Panteras Gym")
                .snippet("Nuestra ubicación")
        )

        // Mover la cámara hacia las coordenadas con zoom
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
    }


    private fun verificarPermisosUbicacion() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permiso concedido, habilita la ubicación
            habilitarUbicacionEnMapa()
        } else {
            // Solicita permisos al usuario
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISOS_UBICACION
            )
        }
    }

    private fun habilitarUbicacionEnMapa() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }
    }

    // Maneja la respuesta de la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISOS_UBICACION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, habilita la ubicación
                habilitarUbicacionEnMapa()
            } else {
                // Permiso denegado, puedes mostrar un mensaje o manejarlo como desees
                Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Métodos del ciclo de vida del MapView
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}