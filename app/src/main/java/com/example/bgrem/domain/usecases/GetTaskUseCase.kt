package com.example.bgrem.domain.usecases

import com.example.bgrem.domain.repositories.ItemBgRepository

class GetTaskUseCase(private val repository: ItemBgRepository) {
    suspend fun execute(task: String) = repository.getTask(task)
}