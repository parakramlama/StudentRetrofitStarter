package com.kiran.student.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kiran.student.R
import com.kiran.student.api.StudentAPI
import com.kiran.student.entity.Student
import com.kiran.student.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentAdapter(
    private val context: Context,
    private val lstStudents: MutableList<Student>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFname: TextView = view.findViewById(R.id.tvFullName)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvGender : TextView = view.findViewById(R.id.tvGender)
        val tvAddress : TextView = view.findViewById(R.id.tvAddress)
        val btnUpdate: ImageButton = view.findViewById(R.id.btnUpdate)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = lstStudents[position]
        holder.tvFname.text = student.fullname
        holder.tvAge.text = student.age.toString()
        holder.tvGender.text = student.gender
        holder.tvAddress.text = student.address

//        holder.btnUpdate.setOnClickListener {
//            val intent = Intent(context, UpdateStudentActivity::class.java)
//            intent.putExtra("student",student)
//            context.startActivity(intent)
//        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete student")
            builder.setMessage("Are you sure you want to delete ${student.fullname} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val studentRepository = StudentRepository()
                        val response = studentRepository.deleteStudent(student._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Student Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Main) {
                                lstStudents.remove(student)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return lstStudents.size
    }
}