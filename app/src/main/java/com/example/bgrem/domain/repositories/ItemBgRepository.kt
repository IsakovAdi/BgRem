package com.example.bgrem.domain.repositories

import com.example.bgrem.domain.models.BackgroundResponse
import com.example.bgrem.domain.models.ImageFile
import com.example.bgrem.domain.models.JobResponse
import com.example.bgrem.domain.models.TaskResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

interface ItemBgRepository {
    suspend fun getBackgrounds():Response<List<BackgroundResponse>>
    suspend fun createJob(file:MultipartBody.Part):Response<JobResponse>
    suspend fun getJob(jobId:String):Response<JobResponse>
    suspend fun createTask(job:String, task_type:String, bgId:String):Response<TaskResponse>
    suspend fun getTask(task:String):Response<TaskResponse>
}