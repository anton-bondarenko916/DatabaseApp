package com.eligon.database

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eligon.database.databinding.ItemCustomBinding
import com.eligon.database.model.Student
import com.eligon.database.model.StudentEntity

class StudentsAdapter(
    private val actionListener: StudentActionListener
) : RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder>(), View.OnClickListener {

    var students: List<Student> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val student = v.tag as Student
        when (v.id) {
            R.id.btn_more -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onStudentDetails(student)
            }
        }
    }

    override fun getItemCount(): Int = students.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.btnMore.setOnClickListener(this)

        return StudentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
        val student = students[position]
        with(holder.binding) {
            holder.itemView.tag = student
            btnMore.tag = student

            studentName.text = student.name
            studentAge.text = "Age: ${student.age}"
            studentWeight.text = "Weight: ${student.weight} kg"
            studentHeight.text = "Height: ${student.height} sm"
            if (student.photo.isNotBlank()) {
                Glide.with(studentIcon.context)
                    .load(student.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(studentIcon)
            } else {
                Glide.with(studentIcon.context).clear(studentIcon)
                studentIcon.setImageResource(R.drawable.ic_baseline_person_24)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val student = view.tag as Student

        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_REMOVE -> {
                    actionListener.onStudentDelete(student)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    class StudentsViewHolder(
        val binding: ItemCustomBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ID_REMOVE = 3
    }
}