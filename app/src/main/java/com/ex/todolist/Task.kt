package com.ex.todolist

data class Task(
    val id: Int,
    val title: String,
    val date: String,
    val category: String,
    val isComplete: Boolean = false
)
