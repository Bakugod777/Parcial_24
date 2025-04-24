package com.example.parcial_2_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BienvenidaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Solo inflar la vista, sin cambiar de fragmento automáticamente
        return inflater.inflate(R.layout.fragment_bienvenida, container, false)
    }
}