package com.example.bgrem.data

import com.example.bgrem.domain.models.BackgroundResponse
import com.example.bgrem.domain.models.ImageFile
import com.example.bgrem.domain.models.JobResponse
import com.example.bgrem.domain.models.TaskResponse
import com.example.bgrem.domain.repositories.ItemBgRepository
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

object ItemBgRepositoryImpl : ItemBgRepository {
    override suspend fun getBackgrounds(): Response<List<BackgroundResponse>> {
        return RetrofitInstance.api.getBackgrounds()
    }

    override suspend fun createJob(file: MultipartBody.Part): Response<JobResponse> {
        return RetrofitInstance.api.createJob(file)
    }

    override suspend fun getJob(jobId: String): Response<JobResponse> {
        return RetrofitInstance.api.getJob(jobId)
    }

    override suspend fun createTask(
        job: String,
        task_type: String,
        bgId: String
    ): Response<TaskResponse> {
        return RetrofitInstance.api.createTask(job, task_type, bgId)
    }

    override suspend fun getTask(task: String): Response<TaskResponse> {
        return RetrofitInstance.api.getTask(task)
    }
}