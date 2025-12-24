package com.plum.superheroapp.domain.interactor

import com.plum.superheroapp.domain.FlowUseCase
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.executor.IoDispatcher
import com.plum.superheroapp.domain.repository.HeroRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroes @Inject constructor(
    private val heroRepository: HeroRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<HeroDomainEntity>, Unit>(dispatcher) {

    override fun invoke(params: Unit): Flow<List<HeroDomainEntity>> {
       return heroRepository.getHeroes()
    }

}