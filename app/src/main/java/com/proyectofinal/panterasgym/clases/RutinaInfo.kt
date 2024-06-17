package com.proyectofinal.panterasgym.clases

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.proyectofinal.panterasgym.R
import java.io.InputStream


class RutinaInfo : AppCompatActivity() {

    // Instancias
    private lateinit var txteNombre1: TextView
    private lateinit var txteNombre2: TextView
    private lateinit var txteNombre3: TextView
    private lateinit var txteNombre4: TextView

    private lateinit var imgEjercicio1: ImageView
    private lateinit var img2Ejercicio1: ImageView
    private lateinit var imgEjercicio2: ImageView
    private lateinit var img2Ejercicio2: ImageView
    private lateinit var imgEjercicio3: ImageView
    private lateinit var img2Ejercicio3: ImageView
    private lateinit var imgEjercicio4: ImageView
    private lateinit var img2Ejercicio4: ImageView

    private lateinit var txteDescripcion1: TextView
    private lateinit var txteDescripcion2: TextView
    private lateinit var txteDescripcion3: TextView
    private lateinit var txteDescripcion4: TextView

    private lateinit var txteRepeticiones1: TextView
    private lateinit var txteRepeticiones2: TextView
    private lateinit var txteRepeticiones3: TextView
    private lateinit var txteRepeticiones4: TextView

    private lateinit var txtePeso1: TextView
    private lateinit var txtePeso2: TextView
    private lateinit var txtePeso3: TextView
    private lateinit var txtePeso4: TextView

    private lateinit var series1: TextView
    private lateinit var series2: TextView
    private lateinit var series3: TextView
    private lateinit var series4: TextView

    fun setImageFromUri(imageView: ImageView, uri: Uri) {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imageView.setImageBitmap(bitmap)
        inputStream?.close()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutina_info)

        // Inicializar vistas
        txteNombre1 = findViewById(R.id.txteNombre1)
        txteNombre2 = findViewById(R.id.txteNombre2)
        txteNombre3 = findViewById(R.id.txteNombre3)
        txteNombre4 = findViewById(R.id.txteNombre4)

        imgEjercicio1 = findViewById(R.id.imgEjercicio1)
        img2Ejercicio1 = findViewById(R.id.img2Ejercicio1)
        imgEjercicio2 = findViewById(R.id.imgEjercicio2)
        img2Ejercicio2 = findViewById(R.id.img2Ejercicio2)
        imgEjercicio3 = findViewById(R.id.imgEjercicio3)
        img2Ejercicio3 = findViewById(R.id.img2Ejercicio3)
        imgEjercicio4 = findViewById(R.id.imgEjercicio4)
        img2Ejercicio4 = findViewById(R.id.img2Ejercicio4)

        txteDescripcion1 = findViewById(R.id.txteDescripcion1)
        txteDescripcion2 = findViewById(R.id.txteDescripcion2)
        txteDescripcion3 = findViewById(R.id.txteDescripcion3)
        txteDescripcion4 = findViewById(R.id.txteDescripcion4)

        txteRepeticiones1 = findViewById(R.id.txteRepeticiones1)
        txteRepeticiones2 = findViewById(R.id.txteRepeticiones2)
        txteRepeticiones3 = findViewById(R.id.txteRepeticiones3)
        txteRepeticiones4 = findViewById(R.id.txteRepeticiones4)

        txtePeso1 = findViewById(R.id.txtePeso1)
        txtePeso2 = findViewById(R.id.txtePeso2)
        txtePeso3 = findViewById(R.id.txtePeso3)
        txtePeso4 = findViewById(R.id.txtePeso4)

        series1 = findViewById(R.id.txteSeries1)
        series2 = findViewById(R.id.txteSeries2)
        series3 = findViewById(R.id.txteSeries3)
        series4 = findViewById(R.id.txteSeries4)

        var jcbEjercicio1: CheckBox = findViewById(R.id.jcbEjercicio1);
        var jcbEjercicio2: CheckBox = findViewById(R.id.jcbEjercicio2);
        var jcbEjercicio3: CheckBox = findViewById(R.id.jcbEjercicio3);
        var jcbEjercicio4: CheckBox = findViewById(R.id.jcbEjercicio4);

        // Obtener la información del Intent
        val infoR = intent.extras
        if (infoR != null) {
            // EJERCICIO 1
            val nombre1 = infoR.getString("eNombre1") ?: ""
            //val imgUri1 = infoR.getString("imgEjercicio1")?.toUri()
            //val img2Uri1 = infoR.getString("img2Ejercicio1")?.toUri()
            val imgPath1: String? = intent.getStringExtra("imgEjercicio1")
            val img2Path1: String? = intent.getStringExtra("img2Ejercicio1")
            val descripcion1 = infoR.getString("eDescripcion1") ?: ""
            val repeticiones1 = infoR.getString("eRepeticiones1") ?: ""
            val peso1 = infoR.getString("ePeso1") ?: ""
            val txteSeries1 = infoR.getString("eSeries1") ?: ""

            // EJERCICIO 2
            val nombre2 = infoR.getString("eNombre2") ?: ""
            //val imgUri2 = infoR.getString("imgEjercicio2")?.toUri()
            //val img2Uri2 = infoR.getString("img2Ejercicio2")?.toUri()
            val imgPath2: String? = intent.getStringExtra("imgEjercicio2")
            val img2Path2: String? = intent.getStringExtra("img2Ejercicio2")
            val descripcion2 = infoR.getString("eDescripcion2") ?: ""
            val repeticiones2 = infoR.getString("eRepeticiones2") ?: ""
            val peso2 = infoR.getString("ePeso2") ?: ""
            val txteSeries2 = infoR.getString("eSeries2") ?: ""

            // EJERCICIO 3
            val nombre3 = infoR.getString("eNombre3") ?: ""
            //val imgUri3 = infoR.getString("imgEjercicio3")?.toUri()
            //val img2Uri3 = infoR.getString("img2Ejercicio3")?.toUri()
            val imgPath3: String? = intent.getStringExtra("imgEjercicio3")
            val img2Path3: String? = intent.getStringExtra("img2Ejercicio3")
            val descripcion3 = infoR.getString("eDescripcion3") ?: ""
            val repeticiones3 = infoR.getString("eRepeticiones3") ?: ""
            val peso3 = infoR.getString("ePeso3") ?: ""
            val txteSeries3 = infoR.getString("eSeries3") ?: ""

            // EJERCICIO 4
            val nombre4 = infoR.getString("eNombre4") ?: ""
            //val imgUri4 = infoR.getString("imgEjercicio4")?.toUri()
            //val img2Uri4 = infoR.getString("img2Ejercicio4")?.toUri()
            val imgPath4: String? = intent.getStringExtra("imgEjercicio4")
            val img2Path4: String? = intent.getStringExtra("img2Ejercicio4")
            val descripcion4 = infoR.getString("eDescripcion4") ?: ""
            val repeticiones4 = infoR.getString("eRepeticiones4") ?: ""
            val peso4 = infoR.getString("ePeso4") ?: ""
            val txteSeries4 = infoR.getString("eSeries4") ?: ""

            // Asignar valores
            // EJERCICIO 1
            txteNombre1.text = "Nombre: $nombre1"
            //imgEjercicio1.setImageURI(imgUri1)
            //img2Ejercicio1.setImageURI(img2Uri1)
            if (imgPath1 != null) {
                val imgUri1: Uri = Uri.parse(imgPath1)
                imgEjercicio1.setImageURI(imgUri1)
                //imgEjercicio1.setImageResource(imgUri1)
            } else {
                imgEjercicio1.setImageResource(R.drawable.imgejercicio)
            }
            if (img2Path1 != null) {
                val img2Uri1: Uri = Uri.parse(img2Path1)
                img2Ejercicio1.setImageURI(img2Uri1)
            } else {
                img2Ejercicio1.setImageResource(R.drawable.imgejercicio)
            }
            txteDescripcion1.text = "Descripcion: $descripcion1"
            txteRepeticiones1.text = "Repeticiones: $repeticiones1"
            txtePeso1.text = "Peso: $peso1"
            series1.text = "Series: $txteSeries1"

            //EJERCICIO 2
            txteNombre2.text = "Nombre: $nombre2"
            //imgEjercicio2.setImageURI(imgUri2)
            //img2Ejercicio2.setImageURI(img2Uri2)
            if (imgPath2 != null) {
                val imgUri2: Uri = Uri.parse(imgPath2)
                imgEjercicio2.setImageURI(imgUri2)
            } else {
                imgEjercicio2.setImageResource(R.drawable.imgejercicio)
            }
            if (img2Path2 != null) {
                val img2Uri2: Uri = Uri.parse(img2Path2)
                img2Ejercicio2.setImageURI(img2Uri2)
            } else {
                img2Ejercicio2.setImageResource(R.drawable.imgejercicio)
            }
            txteDescripcion2.text = "Descripcion: $descripcion2"
            txteRepeticiones2.text = "Repeticiones: $repeticiones2"
            txtePeso2.text = "Peso: $peso2"
            series2.text = "Series: $txteSeries2"

            //EJERCICIO 3
            txteNombre3.text = "Nombre: $nombre3"
            //imgEjercicio3.setImageURI(imgUri3)
            //img2Ejercicio3.setImageURI(img2Uri3)
            if (imgPath3 != null) {
                val imgUri3: Uri = Uri.parse(imgPath3)
                imgEjercicio3.setImageURI(imgUri3)
            } else {
                imgEjercicio3.setImageResource(R.drawable.imgejercicio)
            }
            if (img2Path3 != null) {
                val img2Uri3: Uri = Uri.parse(img2Path3)
                img2Ejercicio3.setImageURI(img2Uri3)
            } else {
                img2Ejercicio3.setImageResource(R.drawable.imgejercicio)
            }
            txteDescripcion3.text = "Descripcion: $descripcion3"
            txteRepeticiones3.text = "Repeticiones: $repeticiones3"
            txtePeso3.text = "Peso: $peso3"
            series3.text = "Series: $txteSeries3"
            //imgEjercicio1.setImageIcon()

            //EJERCICIO 4
            txteNombre4.text = "Nombre: $nombre4"
            //imgEjercicio4.setImageURI(imgUri4)
            //img2Ejercicio4.setImageURI(img2Uri4)
            if (imgPath4 != null) {
                val imgUri4: Uri = Uri.parse(imgPath4)
                imgEjercicio4.setImageURI(imgUri4)
            } else {
                imgEjercicio4.setImageResource(R.drawable.imgejercicio)
            }
            if (img2Path4 != null) {
                val img2Uri4: Uri = Uri.parse(img2Path4)
                img2Ejercicio4.setImageURI(img2Uri4)
            } else {
                img2Ejercicio4.setImageResource(R.drawable.imgejercicio)
            }
            txteDescripcion4.text = "Descripcion: $descripcion4"
            txteRepeticiones4.text = "Repeticiones: $repeticiones4"
            txtePeso4.text = "Peso: $peso4"
            series4.text = "Series: $txteSeries4"
        }

        jcbEjercicio1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "Ejercicio 1 terminado", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        jcbEjercicio2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "Ejercicio 2 terminado", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        jcbEjercicio3.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "Ejercicio 3 terminado", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        jcbEjercicio4.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "Ejercicio 4 terminado", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}