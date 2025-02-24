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
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), AddNewTask.AddTaskListener {

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
        taskAdapter = TaskAdapter(
            emptyList(),
            onTaskClickListener = { task ->
                Snackbar.make(recyclerView, "Tarefa clicada: ${task.title}", Snackbar.LENGTH_SHORT).show()
            },
            onCheckBoxClickListener = { task, isChecked ->
                taskViewModel.toggleTaskCompletion(task)
            }
        )
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.tasks.observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
        }

        val buttonNewTask: MaterialButton = findViewById(R.id.buttonNewTask)
        buttonNewTask.setOnClickListener {
            val dialog = AddNewTask()
            dialog.listener = this
            dialog.show(supportFragmentManager, "AddNewTask")
        }
    }

    override fun onTaskAdded(task: Task) {
        taskViewModel.addTask(task.title, task.date, task.category)
        Snackbar.make(findViewById(R.id.main), "Tarefa adicionada!", Snackbar.LENGTH_SHORT).show()
    }
}