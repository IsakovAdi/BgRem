package com.example.bgrem.presentation.fragments.loadingFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bgrem.data.ItemBgRepositoryImpl
import com.example.bgrem.domain.models.JobResponse
import com.example.bgrem.domain.models.TaskResponse
import com.example.bgrem.domain.usecases.CreateJobUseCase
import com.example.bgrem.domain.usecases.CreateTaskUseCase
import com.example.bgrem.domain.usecases.GetJobUseCase
import com.example.bgrem.domain.usecases.GetTaskUseCase
import com.example.bgrem.presentation.DownloadTask
import com.example.bgrem.presentation.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class LoadingFragmentViewModel : ViewModel() {
    private val repository = ItemBgRepositoryImpl
    private val createJobUseCase = CreateJobUseCase(repository)
    private val createTaskUseCase = CreateTaskUseCase(repository)
    private val getTaskUseCase = GetTaskUseCase(repository)
    private val getJobUseCase = GetJobUseCase(repository)

    private val _task: MutableLiveData<TaskResponse> = MutableLiveData()
    val task: LiveData<TaskResponse> get() = _task

    private val _job: MutableLiveData<Event<JobResponse>> = MutableLiveData()
    val job: LiveData<Event<JobResponse>> get() = _job

    private val _errorJob: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _errorJob

    private val _taskError: MutableLiveData<String> = MutableLiveData()
    val taskError: LiveData<String> get() = _taskError

    fun createTask(job: String, taskType: String, bgId: String) = viewModelScope.launch {
        val response = createTaskUseCase.execute(job, taskType, bgId)
        if (response.isSuccessful) {
            _task.value = response.body()
        } else {
            _taskError.value = response.errorBody().toString()
        }
    }

    fun getTask(task: String) = viewModelScope.launch {
        val response = getTaskUseCase.execute(task)
        if (response.isSuccessful) {
            _task.value = response.body()
        } else {
            _taskError.value = response.errorBody().toString()
        }
    }

    fun createJob(file: MultipartBody.Part) = viewModelScope.launch {
        val response = createJobUseCase.execute(file)
        if (response.isSuccessful && response.body()!!.is_portrait) {
            _job.postValue(Event(response.body()!!))
        } else {
            _errorJob.value = response.errorBody().toString()
        }
    }

    fun getJob(job: String) = viewModelScope.launch {
        val response = getJobUseCase.execute(job)
        if (response.isSuccessful && response.body()!!.is_portrait) {
            _job.postValue(Event(response.body()!!))
        } else {
            _errorJob.value = response.errorBody().toString()
        }
    }
}