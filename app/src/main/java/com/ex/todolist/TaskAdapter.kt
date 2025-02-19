package com.ex.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    var tasks: List<Task>,
    private val onTaskClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitleTask: TextView = itemView.findViewById(R.id.textViewTitleTask)
        private val textViewCategory: TextView = itemView.findViewById(R.id.textViewCategory)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)

        fun bind(task: Task) {
            textViewTitleTask.text = task.title
            textViewCategory.text = task.category
            textViewDate.text = task.date.toString()
            checkBoxCompleted.isChecked = task.isComplete

            checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                onTaskClicked(task.copy(isComplete = isChecked))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}