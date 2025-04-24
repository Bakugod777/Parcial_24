package com.example.parcial_2_1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)

        // Mostrar WelcomeFragment al iniciar
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, BienvenidaFragment())
            .commit()

        // Ítems del menú
        val itemDatos = findViewById<TextView>(R.id.item_datos)
        val itemAnalisis = findViewById<TextView>(R.id.item_analisis)
        val itemHistorico = findViewById<TextView>(R.id.item_historico)

        itemDatos.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DatosFragment())
                .commit()
            drawerLayout.closeDrawers()
        }

        itemAnalisis.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AnalisisFragment())
                .commit()
            drawerLayout.closeDrawers()
        }

        itemHistorico.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HistoricoFragment())
                .commit()
            drawerLayout.closeDrawers()
        }

        // Botón menú
        findViewById<Button>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(findViewById(R.id.menuLateral))
        }
    }
}