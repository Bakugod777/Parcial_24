package com.example.parcial_2_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class AnalisisFragment : Fragment() {

    private lateinit var edtMedName: EditText
    private lateinit var edtDosePerKg: EditText
    private lateinit var edtPatientWeight: EditText
    private lateinit var edtFrequency: EditText
    private lateinit var edtDuration: EditText
    private lateinit var btnCalculate: Button
    private lateinit var txtResult: TextView
    private lateinit var btnSave: Button
    private lateinit var listViewHistory: ListView

    private lateinit var sharedPreferences: SharedPreferences
    private val historyList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analisis, container, false)

        sharedPreferences = requireContext().getSharedPreferences("medicationData", Context.MODE_PRIVATE)

        edtMedName = view.findViewById(R.id.edtMedName)
        edtDosePerKg = view.findViewById(R.id.edtDosePerKg)
        edtPatientWeight = view.findViewById(R.id.edtPatientWeight)
        edtFrequency = view.findViewById(R.id.edtFrequency)
        edtDuration = view.findViewById(R.id.edtDuration)
        btnCalculate = view.findViewById(R.id.btnCalculate)
        txtResult = view.findViewById(R.id.txtResult)
        btnSave = view.findViewById(R.id.btnSave)
        listViewHistory = view.findViewById(R.id.listViewHistory)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, historyList)
        listViewHistory.adapter = adapter

        btnCalculate.setOnClickListener { calculateDosage() }
        btnSave.setOnClickListener { saveDosage() }

        loadHistory()

        return view
    }

    private fun calculateDosage() {
        val medName = edtMedName.text.toString().trim()
        val dosePerKg = edtDosePerKg.text.toString().toDoubleOrNull()
        val weight = edtPatientWeight.text.toString().toDoubleOrNull()
        val frequency = edtFrequency.text.toString().toIntOrNull()
        val duration = edtDuration.text.toString().toIntOrNull()

        if (medName.isEmpty() || dosePerKg == null || weight == null || frequency == null || duration == null ||
            dosePerKg <= 0 || weight <= 0 || frequency <= 0 || duration <= 0) {
            showAlert("Error", "Por favor, complete todos los campos correctamente")
            return
        }

        val totalDose = weight * dosePerKg
        val dosesPerDay = 24 / frequency
        val totalDoses = dosesPerDay * duration

        val schedule = (1..totalDoses).joinToString(", ") { "Dosis $it cada $frequency horas" }
        txtResult.text = "Dosis Total: ${"%.2f".format(totalDose)} mg\nHorario: $schedule"
    }

    private fun saveDosage() {
        val medName = edtMedName.text.toString()
        val resultText = txtResult.text.toString()
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

        if (medName.isEmpty() || resultText.isEmpty()) {
            showToast("Nada que guardar")
            return
        }

        // Limpiar el historial antes de agregar el nuevo registro
        historyList.clear()

        // Crear el nuevo registro
        val record = "$date - $medName: $resultText"
        historyList.add(record)
        adapter.notifyDataSetChanged()

        // Guardar el historial actualizado en SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putStringSet("history", historyList.toSet())
        editor.apply()

        showToast("Registro guardado")
    }

    private fun loadHistory() {
        val savedHistory = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()
        historyList.addAll(savedHistory)
        adapter.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}