package com.ex.todolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

abstract class MainActivity : AppCompatActivity(), AddNewTask.AddTaskListener {

    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)

        taskAdapter = TaskAdapter(emptyList())
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.tasks.observe(this) { tasks ->
            taskAdapter.tasks = tasks
            taskAdapter.notifyDataSetChanged()
        }
        val buttonNewTask: MaterialButton = findViewById(R.id.buttonNewTask)
        buttonNewTask.setOnClickListener {
            buttonNewTask.setOnClickListener {
                val dialog = AddNewTask()
                dialog.listener = this
                dialog.show(supportFragmentManager, "AddNewTask")
            }
        }
    }

    fun onTaskAdded(title: String, category: String, date: String) {
        taskViewModel.addTask(title, category, date)
    }
}
