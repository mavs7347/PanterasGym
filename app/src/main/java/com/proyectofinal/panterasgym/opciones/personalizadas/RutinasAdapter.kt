package com.example.grid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog

class RutinasAdapter(
    private val rutinas: List<Rutina>,
    private val listener: RutinaClickListener
) : RecyclerView.Adapter<RutinasAdapter.RutinaViewHolder>() {

    interface RutinaClickListener {
        fun onEliminarClick(position: Int)
        fun onModificarClick(position: Int)
        fun onVerEjerciciosClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rutina, parent, false)
        return RutinaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val rutina = rutinas[position]
        holder.tvNombreRutina.text = rutina.nombre
        holder.tvDescripcion.text = rutina.descripcion
        holder.tvFechaCreacion.text = rutina.fechaCreacion
        holder.tvDuracion.text = rutina.duracion

        holder.btnVerEjercicios.setOnClickListener {
            listener.onVerEjerciciosClick(position)
        }
        holder.btnEliminarRutina.setOnClickListener {
            listener.onEliminarClick(position)
        }
        holder.btnModificarRutina.setOnClickListener {
            listener.onModificarClick(position)
        }
    }

    override fun getItemCount(): Int {
        return rutinas.size
    }

    inner class RutinaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreRutina: TextView = itemView.findViewById(R.id.tv_nombre_rutina)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tv_descripcion)
        val tvFechaCreacion: TextView = itemView.findViewById(R.id.tv_fecha_creacion)
        val tvDuracion: TextView = itemView.findViewById(R.id.tv_duracion)
        val btnVerEjercicios: Button = itemView.findViewById(R.id.btn_ver_ejercicios)
        val btnEliminarRutina: Button = itemView.findViewById(R.id.btn_eliminar)
        val btnModificarRutina: Button = itemView.findViewById(R.id.btn_modificar)
    }
}

