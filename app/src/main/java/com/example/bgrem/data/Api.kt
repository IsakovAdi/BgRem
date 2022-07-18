package com.example.bgrem.data

import com.example.bgrem.domain.models.BackgroundResponse
import com.example.bgrem.domain.models.JobResponse
import com.example.bgrem.domain.models.TaskResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("bg/")
    suspend fun getBackgrounds(): Response<List<BackgroundResponse>>

    @Headers("Accept:application/json")
    @Multipart
    @POST("jobs/")
    suspend fun createJob(
        @Part file: MultipartBody.Part,
    ): Response<JobResponse>

    @GET("jobs/{job}")
    suspend fun getJob(
        @Path("job") id: String
    ): Response<JobResponse>

    @Headers("accept: application/json", "Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("tasks/")
    suspend fun createTask(
        @Field("job") job: String,
        @Field("task_type") task_type: String,
        @Field("bg_id") bg_id: String
    ): Response<TaskResponse>

    @GET("tasks/{task}")
    suspend fun getTask(
        @Path("task") task: String
    ): Response<TaskResponse>
}