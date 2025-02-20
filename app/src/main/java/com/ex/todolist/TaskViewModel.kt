package com.ex.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        _tasks.value = emptyList()
    }

    fun addTask(title: String, date: String, category: String) {
        val currentTasks = _tasks.value ?: emptyList()
        val newId = if (currentTasks.isNotEmpty()) currentTasks.maxOf { it.id } + 1 else 1
        val newTask = Task(id = newId, title = title, category = category, date = date, isComplete = false)
        _tasks.value = currentTasks + newTask
    }

    fun toggleTaskCompletion(task: Task) {
        val currentTasks = _tasks.value ?: emptyList()
        val updatedTasks = currentTasks.map {
            if (it.id == task.id) it.copy(isComplete = !it.isComplete)
            else it
        }
        _tasks.value = updatedTasks
    }

    fun deleteTask(task: Task) {
        val currentTasks = _tasks.value ?: emptyList()
        _tasks.value = currentTasks.filter { it.id != task.id }
    }
}