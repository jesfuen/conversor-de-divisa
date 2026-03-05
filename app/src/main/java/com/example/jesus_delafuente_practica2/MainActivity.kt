package com.example.jesus_delafuente_practica2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private lateinit var radioGroup1: RadioGroup
    private lateinit var radioGroup2: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById(R.id.button)
        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)
        radioGroup1 = findViewById(R.id.radioGroup1)
        radioGroup2 = findViewById(R.id.radioGroup2)

        button.setOnClickListener {
            val importe = editText.text.toString().toDoubleOrNull()

            if (importe == null) {
                Toast.makeText(this,"Introduce un importe válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idOrigen = radioGroup1.checkedRadioButtonId
            val idDestino = radioGroup2.checkedRadioButtonId

            if (idOrigen == -1 || idDestino == -1) {
                Toast.makeText(this,"Seleccione una divisa de origen y de destino",Toast.LENGTH_SHORT).show()
            }

            val resultado = convertir(importe,idOrigen,idDestino)

            textView.text = resultado.toString()

        }
    }

    private fun convertir(importe: Double,idOrigen: Int,idDestino: Int): Double {
        val conversiones = mapOf(
            R.id.eurosOrigen   to 1.0,
            R.id.dolaresOrigen to 1.08,
            R.id.librasOrigen  to 0.85,
            R.id.eurosDestino  to 1.0,
            R.id.dolaresDestino to 1.08,
            R.id.librasDestino  to 0.85
        )

        val aEuros = importe / (conversiones[idOrigen] ?: 1.0)
        return aEuros * (conversiones[idDestino] ?: 1.0)
    }
}