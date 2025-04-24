package com.example.parcial_2_1

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class DatosFragment : Fragment() {

    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPrograma: EditText
    private lateinit var etSemestre: EditText
    private lateinit var btnEditarGuardar: Button
    private var enModoEdicion = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_datos, container, false)

        etNombre = view.findViewById(R.id.etNombre)
        etEdad = view.findViewById(R.id.etEdad)
        etCorreo = view.findViewById(R.id.etCorreo)
        etPrograma = view.findViewById(R.id.etPrograma)
        etSemestre = view.findViewById(R.id.etSemestre)
        btnEditarGuardar = view.findViewById(R.id.btnEditarGuardar)

        cargarDatos()

        btnEditarGuardar.setOnClickListener {
            if (enModoEdicion) {
                guardarDatos()
                cambiarModo(false)
                Toast.makeText(requireContext(), "Datos guardados", Toast.LENGTH_SHORT).show()
            } else {
                cambiarModo(true)
            }
        }

        return view
    }

    private fun cargarDatos() {
        val sharedPref = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        etNombre.setText(sharedPref.getString("nombre", ""))
        etEdad.setText(sharedPref.getInt("edad", 0).toString())
        etCorreo.setText(sharedPref.getString("correo", ""))
        etPrograma.setText(sharedPref.getString("programa", ""))
        etSemestre.setText(sharedPref.getInt("semestre", 0).toString())
    }

    private fun guardarDatos() {
        val nombre = etNombre.text.toString()
        val edad = etEdad.text.toString().toIntOrNull() ?: 0
        val correo = etCorreo.text.toString()
        val programa = etPrograma.text.toString()
        val semestre = etSemestre.text.toString().toIntOrNull() ?: 0

        val sharedPref = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nombre", nombre)
            putInt("edad", edad)
            putString("correo", correo)
            putString("programa", programa)
            putInt("semestre", semestre)
            apply()
        }
    }

    private fun cambiarModo(edicion: Boolean) {
        enModoEdicion = edicion
        etNombre.isEnabled = edicion
        etEdad.isEnabled = edicion
        etCorreo.isEnabled = edicion
        etPrograma.isEnabled = edicion
        etSemestre.isEnabled = edicion
        btnEditarGuardar.text = if (edicion) "Guardar" else "Editar"
    }
}