package com.ex.todolist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (
    var tasks: List<Task>,
    private val onTaskClicked: (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitleTask: TextView = itemView.findViewById(R.id.textViewTitleTask)
    }
}