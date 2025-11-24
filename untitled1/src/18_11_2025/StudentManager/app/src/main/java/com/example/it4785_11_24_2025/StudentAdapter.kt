package com.example.it4785_11_24_2025

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onStudentClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStudentName: TextView = itemView.findViewById(R.id.tvStudentName)
        private val tvStudentId: TextView = itemView.findViewById(R.id.tvStudentId)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student) {
            tvStudentName.text = student.name
            tvStudentId.text = student.id

            itemView.setOnClickListener {
                onStudentClick(student)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(student)
            }
        }
    }
}