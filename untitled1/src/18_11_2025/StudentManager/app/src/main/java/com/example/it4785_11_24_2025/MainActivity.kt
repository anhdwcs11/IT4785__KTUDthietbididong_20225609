package com.example.it4785_11_24_2025

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Data class can be simplified when not used with Compose state.
// `var` is used to allow modification of student properties after creation.
data class Student(var id: String, var name: String)

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var etStudentName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView

    private val students = mutableListOf<Student>()
    private lateinit var studentAdapter: StudentAdapter
    private var selectedStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view from the XML layout file
        setContentView(R.layout.activity_main)

        // Initialize views from the layout
        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // Initially, the update button is disabled
        btnUpdate.isEnabled = false

        // Setup the adapter with callbacks for item clicks
        studentAdapter = StudentAdapter(
            students,
            onStudentClick = { student ->
                // When a student is clicked, populate the fields for an update
                etStudentId.setText(student.id)
                etStudentName.setText(student.name)
                selectedStudent = student
                btnUpdate.isEnabled = true
            },
            onDeleteClick = { student ->
                // When delete is clicked, find and remove the student
                val position = students.indexOf(student)
                if (position != -1) {
                    students.removeAt(position)
                    studentAdapter.notifyItemRemoved(position)
                    // If the deleted student was the one selected, clear the fields
                    if (selectedStudent == student) {
                        clearSelection()
                    }
                    Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                }
            }
        )

        // Setup RecyclerView
        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Handle the "Add" button click
        btnAdd.setOnClickListener {
            val id = etStudentId.text.toString().trim()
            val name = etStudentName.text.toString().trim()

            if (id.isNotBlank() && name.isNotBlank()) {
                // Check if the student ID already exists
                if (students.any { it.id == id }) {
                    Toast.makeText(this, "Mã số sinh viên đã tồn tại", Toast.LENGTH_SHORT).show()
                } else {
                    val newStudent = Student(id, name)
                    students.add(newStudent)
                    studentAdapter.notifyItemInserted(students.size - 1)
                    clearInputFields()
                    etStudentId.requestFocus() // Move cursor back to the ID field
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle the "Update" button click
        btnUpdate.setOnClickListener {
            val updatedId = etStudentId.text.toString().trim()
            val updatedName = etStudentName.text.toString().trim()

            if (updatedId.isNotBlank() && updatedName.isNotBlank()) {
                selectedStudent?.let { studentToUpdate ->
                    val originalId = studentToUpdate.id
                    val position = students.indexOf(studentToUpdate)

                    // Check if the new ID is different and if it already exists for another student
                    val isNewIdDuplicate = students.any { it.id == updatedId && it.id != originalId }

                    if (isNewIdDuplicate) {
                        Toast.makeText(this, "Mã số sinh viên mới đã tồn tại", Toast.LENGTH_SHORT).show()
                    } else if (position != -1) {
                        // Update the student's data
                        studentToUpdate.id = updatedId
                        studentToUpdate.name = updatedName
                        studentAdapter.notifyItemChanged(position)
                        Toast.makeText(this, "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
                        clearSelection()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearInputFields() {
        etStudentId.text.clear()
        etStudentName.text.clear()
    }

    private fun clearSelection() {
        clearInputFields()
        selectedStudent = null
        btnUpdate.isEnabled = false
    }
}
