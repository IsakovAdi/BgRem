package com.example.bgrem.domain.usecases

import com.example.bgrem.domain.repositories.ItemBgRepository
import okhttp3.MultipartBody

class CreateJobUseCase(private val repository: ItemBgRepository){
    suspend fun execute(file: MultipartBody.Part) = repository.createJob(file)
}