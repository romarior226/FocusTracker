package com.example.focustracker.data.mapper

import com.example.focustracker.data.entity.HistoryEntityDbModel
import com.example.focustracker.data.entity.TaskEntityDbModel
import com.example.focustracker.data.network.TodoDto
import com.example.focustracker.domain.entity.Task
import com.example.focustracker.domain.entity.Todo


fun Task.toEntityDbModel(): TaskEntityDbModel {
    return TaskEntityDbModel(
        id = this.id,
        name = this.name,
        isDone = this.isDone,
        timeCreation = this.timeCreation,
        completedTime = this.completedTime,
        isFromNetwork = this.isFromNetwork
    )
}

fun TaskEntityDbModel.toTask(): Task {
    return Task(
        id = this.id,
        name = this.name,
        isDone = this.isDone,
        timeCreation = this.timeCreation,
        completedTime = this.completedTime,
        isFromNetwork = this.isFromNetwork

    )
}
fun HistoryEntityDbModel.toTask() : Task {
    return Task(
        id = this.id,
        name = this.name,
        isDone = true,
        timeCreation = this.timeCreation,
        completedTime = this.completedTime
    )
}
fun TodoDto.toTodo(): Todo {
    return Todo(
        id = this.id,
        title = this.title,
        completed = this.completed
    )
}
fun Task.toHistoryEntityDbModel() : HistoryEntityDbModel {
    return HistoryEntityDbModel(
        id = this.id,
        name = this.name,
        timeCreation = this.timeCreation,
        completedTime = this.completedTime
    )
}
