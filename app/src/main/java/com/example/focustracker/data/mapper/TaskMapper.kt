package com.example.focustracker.data.mapper

import com.example.focustracker.data.entity.TaskEntityDbModel
import com.example.focustracker.domain.Task


fun Task.toTaskEntityDbModel(): TaskEntityDbModel {
    return TaskEntityDbModel(
        id = this.id,
        name = this.name,
        isDone = this.isDone,
        timeCreation = this.timeCreation,
        completedTime = this.completedTime
    )
}

fun TaskEntityDbModel.toTask(): Task {
    return Task(
        id = this.id,
        name = this.name,
        isDone = this.isDone,
        timeCreation = this.timeCreation,
        completedTime = this.completedTime
    )
}
