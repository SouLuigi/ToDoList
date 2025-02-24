package com.ex.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: List<Task>,
    private val onTaskClickListener: (Task) -> Unit,
    private val onCheckBoxClickListener: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitleTask)
        private val categoryTextView: TextView = itemView.findViewById(R.id.textViewCategory)
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
        private val completedCheckBox: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)

        fun bind(task: Task) {
            titleTextView.text = task.title
            categoryTextView.text = task.category
            dateTextView.text = task.date
            completedCheckBox.isChecked = task.isComplete

            itemView.setOnClickListener {
                onTaskClickListener(task)
            }

            completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onCheckBoxClickListener(task, isChecked)
            }
        }
    }

    fun updateTasks(newTasks: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(TaskDiffCallback(tasks, newTasks))
        tasks = newTasks
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    private class TaskDiffCallback(
        private val oldTasks: List<Task>,
        private val newTasks: List<Task>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldTasks.size
        override fun getNewListSize() = newTasks.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldTasks[oldItemPosition].id == newTasks[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldTasks[oldItemPosition] == newTasks[newItemPosition]
        }
    }
}