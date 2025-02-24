package com.ex.todolist

data class Task(
    val id: Int,
    val title: String,
    val date: String,
    val category: String,
    val isComplete: Boolean = false
) {
    init {
        require(title.isNotBlank()) { "O título da tarefa não pode estar vazio." }
        require(date.isNotBlank()) { "A data da tarefa não pode estar vazia." }
        require(category.isNotBlank()) { "A categoria da tarefa não pode estar vazia." }
    }
}