package com.proyectofinal.panterasgym.opciones

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Ejercicio
import com.proyectofinal.panterasgym.clases.Rutina
import com.proyectofinal.panterasgym.clases.RutinaInfo
import com.proyectofinal.panterasgym.databinding.FragmentRutinasRecomendadasBinding

class RutinasRecomendadasFragment : Fragment() {
    // Variables Globales
    private var _binding: FragmentRutinasRecomendadasBinding? = null
    private val binding get() = _binding!!
    // Componentes
    /*
    private lateinit var rnombre: TextView
    private lateinit var rduracion: TextView
    private lateinit var rdescripcion: TextView
    private lateinit var rcreacion: TextView */

    companion object {
        fun newInstance() = RutinasRecomendadasFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRutinasRecomendadasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Componentes
        /*
        rnombre = binding.txtTituloCardio
        rdescripcion = binding.txtDescripcionCardio
        rduracion = binding.txtDuracionCardio
        rcreacion = binding.txtCreacionCardio */

        // Rutina de cardio
        binding.txtTituloCardio.text = "Rutina: Cardio"
        binding.txtDescripcionCardio.text = "Descripcion: Rutina recomendada para realizar ejercicios de cardio. " +
                "Una serie de ejercicios para calentar el cuerpo y quemar la grasa"
        binding.txtCreacionCardio.text = "Creacion: Predeterminada"
        binding.txtDuracionCardio.text = "Duracion: 35 min"

        //Rutina de abdomen
        binding.txtTituloAdbomen.text = "Rutina: Abdomen"
        binding.txtDescripcionAbdomen.text = "Descripcion: Rutina recomendada para realizar ejercicios de Abdomen. " +
                "Recomendada para definir la figura del abdomen"
        binding.txtCreacionAbdomen.text = "Creacion: Predeterminada"
        binding.txtDuracionAbdomen.text = "Duracion: 20min"

        //rutina de bicep:
        binding.txtTituloBicep.text = "Rutina: Bicep"
        binding.txtDescripcionBicep.text = "Descripcion: Rutina recomendada para realizar ejercicios de Bicep. " +
                "Especializada en ganar fuerzqa, aumentar la musculatura y definir musculo del bicep"
        binding.txtCreacionBicep.text = "Creacion: Predeterminada"
        binding.txtDuracionBicep.text = "20min"

        binding.imgRutinaCardio.setOnClickListener() {
            val intent = Intent(activity, RutinaInfo::class.java)
            //Ejercicio 1 Bicicleta
            intent.putExtra("eNombre1", "Bicicleta")
            var mensajeEj1 = "· Cuando vaya a pedalear, preste atención a sus rodillas." +
                    "Ellas no deben, en ningún caso, estar abiertas, pues eso las" +
                    "obligará aún más. Compruebe siempre que su cadera y sus" +
                    "rodillas están alineadas con la bicicleta." +
                    "\n" +
                    "· Usted debe mantener su columna recta, sin dejar el pecho" +
                    "más adelante que el resto del cuerpo. Por supuesto, para" +
                    "conseguir agarrar el manillar, tendrás que mantener una" +
                    "ligera inclinación. Pero eso no impide que usted mantenga" +
                    "la espalda siempre erguida, con la cabeza siempre mirando" +
                    "hacia adelante." +
                    "\n" +
                    "· Sus piernas no deben, al final del pedal, estar" +
                    "completamente extendidas. Es necesario tener una ligera" +
                    "flexión, de aproximadamente 15°, del muslo con la pierna."
            intent.putExtra("eDescripcion1", mensajeEj1)
            intent.putExtra("eRepeticiones1", "Durante 15min")
            intent.putExtra("ePeso1", "No aplica")
            intent.putExtra("eSeries1", "Durante 15min")
            val imgUri1 = "android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_bicicleta"
            intent.putExtra("imgEjercicio1", imgUri1)
            val img2Uri1 = "android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_bicicleta2"
            intent.putExtra("img2Ejercicio1", img2Uri1)

            //Ejercicio 2 Jumping Jack
            intent.putExtra("eNombre2", "Jumping Jack")
            var mensajeEj2 = "· Este ejercicio puede sustituir los aparatos de cardio como" +
                    "la bicicleta y la estera." +
                    "\n" +
                    "· Haga el movimiento sincronizado de abre y cierra de" +
                    "los brazos y piernas abriendo un arco paralelo al sentido" +
                    "longitudinal del cuerpo, de forma que las palmas de las" +
                    "manos se junten sobre la cabeza." +
                    "\n" +
                    "· Procure dejar sus piernas levemente flexionadas."
            intent.putExtra("eDescripcion2", mensajeEj2)
            intent.putExtra("eRepeticiones2", "12")
            intent.putExtra("ePeso2", "No aplica")
            intent.putExtra("eSeries2", "4")
            val imgUri2 ="android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_jumpingjack"
            intent.putExtra("imgEjercicio2", imgUri2)
            val img2Uri2 ="android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_jumpingjack2"
            intent.putExtra("img2Ejercicio2", img2Uri2)

            //Ejercicio 3 Maquina de Correr
            intent.putExtra("eNombre3", "Maquina de Correr")
            var mensajeEj3 = ". Trote o camine a un ritmo lento por 5 minutos para" +
                    "calentar" +
                    "\n" +
                    "· Terminado el entrenamiento, no descuida el descenso." +
                    "Así como la del cuerpo en la fase inicial de la carrera," +
                    "enfriar la musculatura es igualmente importante, para" +
                    "que usted baje el ritmo cardíaco, lo que previene mareos" +
                    "o incluso la sensación de que usted todavía se está" +
                    "moviendo incluso fuera de la estera." +
                    "\n" +
                    "· Para mejorar la respuesta de su cuerpo al entrenamiento," +
                    "defina una inclinacion hasta el 2%. Como no hay resistencia" +
                    "del aire en ambientes cerrados, esta suave inclinación" +
                    "simula la carrera al aire libre. Al mismo tiempo, no defina" +
                    "inclinaciones muy empinadas (más del 7%), ya que esto" +
                    "puede sobrecargar el tendón de Aquiles y las pantorrillas."
            intent.putExtra("eDescripcion3", mensajeEj3)
            intent.putExtra("eRepeticiones3", "Durante 15min")
            intent.putExtra("ePeso3", "No aplica")
            intent.putExtra("eSeries3", "Durante 15min")
            val imgUri3 ="android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_maquinacorrer"
            intent.putExtra("imgEjercicio3", imgUri3)
            val img2Uri3 ="android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_maquinacorrer2"
            intent.putExtra("img2Ejercicio3", img2Uri3)

            //Ejercicio 4 Saltar la cuerda
            intent.putExtra("eNombre4", "Saltar la Cuerda")
            var mensajeEj4 = ". La cuerda del tamaño correcto te permite completar sus" +
                    "saltos sin prender tu pie en la cuerda durante las vueltas." +
                    "\n" +
                    ". Intervalos son la mejor forma de saltar la cuerda para" +
                    "adelgazar, aumentando la quema de calorías y conseguir" +
                    "los resultados que usted quiere. Intervalos mezclan" +
                    "pequeñas explosiones de alta intensidad con pequeños" +
                    "períodos de intensidad moderada para aumentar y" +
                    "disminuir su tasa cardiaca." +
                    "\n" +
                    ". Las personas que sufren lesiones en los tobillos, rodillas," +
                    "cadera y columna deben tener cuidado antes de iniciar el" +
                    "entrenamiento"
            intent.putExtra("eDescripcion4", mensajeEj4)
            intent.putExtra("eRepeticiones4", "12")
            intent.putExtra("ePeso4", "No aplica")
            intent.putExtra("eSeries4", "4")
            val imgUri4 = "android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_saltarcuerda"
            intent.putExtra("imgEjercicio4", imgUri4)
            val img2Uri4 ="android.resource://com.proyectofinal.panterasgym/drawable/imgcardio_saltarcuerda2"
            intent.putExtra("img2Ejercicio4", img2Uri4)
            startActivity(intent)
        }

        binding.imgRutinaAbdomen.setOnClickListener {
            val intent = Intent(activity, RutinaInfo::class.java)
            //Ejercicio 1 Abdominal Crunch
            intent.putExtra("eNombre1", "Abdominal Crunch")
            var mensajeEj1 = "· Mantenga los pies totalmente apoyados en el suelo" +
                    "durante el ejercicio." +
                    "\n" +
                    ". Mantenga las piernas unidas." +
                    "\n" +
                    "· Mantenga la mirada recta durante el movimiento." +
                    "\n" +
                    ". No apoye la cabeza con las manos para facilitar el" +
                    "movimiento." +
                    "\n" +
                    "· Mantenga el abdomen contraído durante todo el" +
                    "movimiento."
            intent.putExtra("eDescripcion1", mensajeEj1)
            intent.putExtra("eRepeticiones1", "12")
            intent.putExtra("ePeso1", "No aplica")
            intent.putExtra("eSeries1", "4")
            val imgUri1 = "android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_abdominalcrunch"
            intent.putExtra("imgEjercicio1", imgUri1)
            val img2Uri1 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_abdominalcrunch2"
            intent.putExtra("img2Ejercicio1", img2Uri1)

            //Ejercicio 2 Elevacion Pierna Recta
            intent.putExtra("eNombre2", "Elevacion Pierna Recta")
            var mensajeEj2 = ". Mantenga los pies juntos y las rodillas ligeramente" +
                    "flexionadas durante el ejercicio." +
                    "\n" +
                    ". La parte superior del tronco debe permanecer siempre en" +
                    "contacto con el suelo." +
                    "\n" +
                    "· Para mejor provecho del abdomen, no deje que las" +
                    "piernas se apoyan en el suelo cuando las baja." +
                    "\n" +
                    "· Usar las pesas de tobillo para intensificaR el ejercicio. Se" +
                    "recomienda aumentar el peso si puede hacer más de 20" +
                    "repeticiones."
            intent.putExtra("eDescripcion2", mensajeEj2)
            intent.putExtra("eRepeticiones2", "20")
            intent.putExtra("ePeso2", "Incremental")
            intent.putExtra("eSeries2", "4")
            val imgUri2 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_abdominalcrunch"
            intent.putExtra("imgEjercicio2", imgUri2)
            val img2Uri2 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_abdominalcrunch"
            intent.putExtra("img2Ejercicio2", img2Uri2)

            //Ejercicio 3 Extencion del tronco
            intent.putExtra("eNombre3", "Extencion del tronco")
            var mensajeEj3 = "· Ajuste los soportes acolchados de la parte inferior de las" +
                    "piernas de forma que la presión quede distribuida de forma" +
                    "equilibrada por la parte inferior del cuerpo." +
                    "\n" +
                    "· No forme en el movimiento de descenso si siente un" +
                    "ligero estiramiento de los isquiotibiales y en la zona" +
                    "lumbar." +
                    "\n" +
                    "· La amplitud completa del movimiento variará de persona" +
                    "a persona."
            intent.putExtra("eDescripcion3", mensajeEj3)
            intent.putExtra("eRepeticiones3", "12")
            intent.putExtra("ePeso3", "Incremental")
            intent.putExtra("eSeries3", "4")
            val imgUri3 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_extenciontronco"
            intent.putExtra("imgEjercicio3", imgUri3)
            val img2Uri3 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_extenciontronco2"
            intent.putExtra("img2Ejercicio3", img2Uri3)

            //Ejercicio 4 Tocar talones
            intent.putExtra("eNombre4", "Tocar talones")
            var mensajeEj4 = "· Mantenga los pies planos en el piso durante todo el" +
                    "movimiento." +
                    "\n" +
                    "· Mantenga el abdomen contraído durante el movimiento." +
                    "\n" +
                    "· Mueva todo el tronco, evite mover solo el cuello."
            intent.putExtra("eDescripcion4", mensajeEj4)
            intent.putExtra("eRepeticiones4", "12")
            intent.putExtra("ePeso4", "No aplica")
            intent.putExtra("eSeries4", "4")
            val imgUri4 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_tocartalones"
            intent.putExtra("imgEjercicio4", imgUri4)
            val img2Uri4 ="android.resource://com.proyectofinal.panterasgym/drawable/imgabdomen_tocartalones2"
            intent.putExtra("img2Ejercicio4", img2Uri4)
            startActivity(intent)
        }

        binding.imgRutinaBicep.setOnClickListener {
            val intent = Intent(activity, RutinaInfo::class.java)
            //Ejercicio 1 Curl Bicep Bar
            intent.putExtra("eNombre1", "Curl Bicep Bar")
            var mensajeEj1 = "· un agarre más abierto concentra el esfuerzo en la región" +
                    "interna del bíceps (cabeza corta), mientras que un agarre" +
                    "cerrado trabaja el exterior del bíceps (cabeza larga)." +
                    "\n" +
                    "· En la rosca directa con barra, la barra debe moverse hacia" +
                    "arriba y hacia abajo en un arco cerca del cuerpo. Para aislar" +
                    "el bíceps, el movimiento debe ocurrir en el codo y no en el" +
                    "hombro." +
                    "\n" +
                    "· Mantenga el cuerpo firme durante el movimiento, evite" +
                    "balancearse hacia atrás."
            intent.putExtra("eDescripcion1", mensajeEj1)
            intent.putExtra("eRepeticiones1", "12")
            intent.putExtra("ePeso1", "Incrementable")
            intent.putExtra("eSeries1", "4")
            val imgUri1 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_curlbicepbarra"
            intent.putExtra("imgEjercicio1", imgUri1)
            val img2Uri1 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_curlbicepbarra2"
            intent.putExtra("img2Ejercicio1", img2Uri1)

            //Ejercicio 2 Curl Bicep Polea
            intent.putExtra("eNombre2", "Curl Bicep Polea")
            var mensajeEj2 = "· un agarre más abierto concentra el esfuerzo en la región" +
                    "interna del bíceps (cabeza corta), mientras que un agarre" +
                    "cerrado trabaja el exterior del bíceps (cabeza larga)." +
                    "\n" +
                    "· Para aislar el bíceps, el movimiento debe ocurrir en el" +
                    "codo y no en el hombro." +
                    "\n" +
                    "· Mantenga el cuerpo firme durante el movimiento, evite" +
                    "balancearse hacia atrás."
            intent.putExtra("eDescripcion2", mensajeEj2)
            intent.putExtra("eRepeticiones2", "12")
            intent.putExtra("ePeso2", "Incremental")
            intent.putExtra("eSeries2", "4")
            val imgUri2 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_curlbiceppolea"
            intent.putExtra("imgEjercicio2", imgUri2)
            val img2Uri2 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_curlbiceppolea2"
            intent.putExtra("img2Ejercicio2", img2Uri2)

            //Ejercicio 3 Flexiones
            intent.putExtra("eNombre3", "Flexiones ")
            var mensajeEj3 = "· Mantenga el abdomen contraído durante todo el ejercicio." +
                    "\n" +
                    "· Los descensos se deben realizar lentamente para una" +
                    "mayor resistencia del ejercicio." +
                    "\n" +
                    "· No deje que su pectoral se apoya en el suelo." +
                    "\n" +
                    "· No estire completamente los codos, manténgalos" +
                    "ligeramente flexionados." +
                    "\n" +
                    "· Deje las piernas completamente estiradas durante el" +
                    "ejercicio."
            intent.putExtra("eDescripcion3", mensajeEj3)
            intent.putExtra("eRepeticiones3", "12")
            intent.putExtra("ePeso3", "No aplica")
            intent.putExtra("eSeries3", "4")
            val imgUri3 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_flexiones"
            intent.putExtra("imgEjercicio3", imgUri3)
            val img2Uri3 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_flexiones2"
            intent.putExtra("img2Ejercicio3", img2Uri3)

            //Ejercicio 4 Flexion con mancuerna
            intent.putExtra("eNombre4", "Flexion con mancuerna")
            var mensajeEj4 = "· Al realizar este ejercicio con pesas de apoyo, el énfasis" +
                    "en el pectoral es mayor, pues usted podrá realizar el" +
                    "movimiento de descenso por debajo de la línea en el" +
                    "cuerpo." +
                    "\n" +
                    "· Mantenga el abdomen contraído durante todo el ejercicio." +
                    "\n" +
                    "· Los descensos se deben realizar lentamente para una" +
                    "mayor resistencia del ejercicio." +
                    "\n" +
                    "· No deje que su pectoral se apoya en el suelo." +
                    "\n" +
                    "· No estire completamente los codos, manténgalos" +
                    "ligeramente flexionados." +
                    "\n" +
                    "· Deje las piernas completamente estiradas durante el" +
                    "ejercicio."
            intent.putExtra("eDescripcion4", mensajeEj4)
            intent.putExtra("eRepeticiones4", "12")
            intent.putExtra("ePeso4", "Incremental")
            intent.putExtra("eSeries4", "4")
            val imgUri4 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_flexionmancuerna"
            intent.putExtra("imgEjercicio4", imgUri4)
            val img2Uri4 ="android.resource://com.proyectofinal.panterasgym/drawable/imgbicep_flexionmancuerna2"
            intent.putExtra("img2Ejercicio4", img2Uri4)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}