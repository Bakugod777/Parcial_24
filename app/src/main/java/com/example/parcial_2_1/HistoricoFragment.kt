package com.example.parcial_2_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class HistoricoFragment : Fragment() {

    private lateinit var listViewHistory: ListView
    private lateinit var sharedPreferences: SharedPreferences
    private val historyList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historico, container, false)

        // Inicializar SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("medicationData", Context.MODE_PRIVATE)

        // Referenciar la lista de historial
        listViewHistory = view.findViewById(R.id.listViewHistory)

        // Inicializar el adaptador y asociarlo al ListView
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, historyList)
        listViewHistory.adapter = adapter

        // Cargar el historial almacenado
        loadHistory()

        return view
    }

    private fun loadHistory() {
        val savedHistory = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()
        historyList.clear()
        historyList.addAll(savedHistory)
        adapter.notifyDataSetChanged()
    }
}