package com.example.bgrem.domain.usecases

import com.example.bgrem.domain.repositories.ItemBgRepository

class GetBgColorsUseCase(private val repository: ItemBgRepository) {
    suspend fun execute() = repository.getBackgrounds()
}