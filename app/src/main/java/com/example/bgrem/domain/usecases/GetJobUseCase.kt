package com.example.bgrem.domain.usecases

import com.example.bgrem.domain.repositories.ItemBgRepository

class GetJobUseCase(private val repository: ItemBgRepository) {
    suspend fun execute(job:String) = repository.getJob(job)
}