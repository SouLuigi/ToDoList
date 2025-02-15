package com.ex.todolist

import java.sql.Date

data class Task(
    val id: Int,
    val title: String,
    val date: Date,
    val category: String,
    val isComplete: Boolean = false
)
