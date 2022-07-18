package com.example.bgrem.domain.usecases

import com.example.bgrem.domain.repositories.ItemBgRepository

class CreateTaskUseCase(private val repository: ItemBgRepository) {
    suspend fun execute(job: String, taskType: String, bgId: String) =
        repository.createTask(job, taskType, bgId)
}