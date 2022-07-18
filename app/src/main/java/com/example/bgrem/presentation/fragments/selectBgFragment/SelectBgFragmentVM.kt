package com.example.bgrem.presentation.fragments.selectBgFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bgrem.data.ItemBgRepositoryImpl
import com.example.bgrem.domain.models.BackgroundResponse
import com.example.bgrem.domain.models.JobResponse
import com.example.bgrem.domain.models.TaskResponse
import com.example.bgrem.domain.models.TaskStatus
import com.example.bgrem.domain.usecases.GetBgColorsUseCase
import com.example.bgrem.domain.usecases.GetJobUseCase
import com.example.bgrem.domain.usecases.GetTaskUseCase
import kotlinx.coroutines.launch

class SelectBgFragmentVM : ViewModel() {
    private val repository = ItemBgRepositoryImpl
    private val getJobUseCase = GetJobUseCase(repository)
    private val getBgColorsUseCase = GetBgColorsUseCase(repository)
    private val getTaskUseCase = GetTaskUseCase(repository)

    private val _bgList: MutableLiveData<List<BackgroundResponse>> = MutableLiveData()
    val bgList: LiveData<List<BackgroundResponse>> get() = _bgList

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error

    private val _job: MutableLiveData<JobResponse> = MutableLiveData()
    val job: LiveData<JobResponse> get() = _job

    private val _task: MutableLiveData<TaskResponse> = MutableLiveData()
    val task: LiveData<TaskResponse> get() = _task

    fun getJob(job: String) = viewModelScope.launch {
        val response = getJobUseCase.execute(job)
        if (response.isSuccessful) _job.value = response.body()
        else _error.value = response.errorBody().toString()
    }

    fun getTask(task:String) = viewModelScope.launch {
        val response = getTaskUseCase.execute(task)
        if (response.isSuccessful) _task.value = response.body()
        else _error.value = response.errorBody().toString()
    }

    fun getBgColors() = viewModelScope.launch {
        val response = getBgColorsUseCase.execute()
        if (response.isSuccessful) _bgList.value = response.body()
        else _error.value = response.errorBody().toString()
    }
}