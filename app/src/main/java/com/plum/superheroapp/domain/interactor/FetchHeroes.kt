package com.plum.superheroapp.domain.interactor

import com.plum.superheroapp.domain.SuspendUseCase
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.executor.IoDispatcher
import com.plum.superheroapp.domain.repository.HeroRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FetchHeroes @Inject constructor(
    private val heroRepository: HeroRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, Unit>(dispatcher) {

    override suspend fun invoke(params: Unit) {
        heroRepository.fetchHeroes()
    }

}