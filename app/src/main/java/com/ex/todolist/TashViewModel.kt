package com.ex.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TashViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        _tasks.value = emptyList()
    }

    fun addTask(title: String) {
        val currentTasks = _tasks.value ?: emptyList()
        val newTask = Task(id = currentTasks.size + 1, title = title)
        _tasks.value = currentTasks + newTask
    }

    fun toggleTaskCompletion(task: Task) {
        val currentTasks = _tasks.value ?: emptyList()
        val updatedTasks = currentTasks.map {
            if (it.id == task.id) it.copy(isCompleted = !it.isCompleted)
            else it
        }
        _tasks.value = updatedTasks
    }

    fun deleteTask(task: Task) {
        val currentTasks = _tasks.value ?: emptyList()
        _tasks.value = currentTasks.filter { it.id != task.id }
    }
}