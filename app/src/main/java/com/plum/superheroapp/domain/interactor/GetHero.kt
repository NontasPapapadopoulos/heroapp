package com.plum.superheroapp.domain.interactor

import com.plum.superheroapp.domain.FlowUseCase
import com.plum.superheroapp.domain.SuspendUseCase
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.executor.IoDispatcher
import com.plum.superheroapp.domain.repository.HeroRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHero @Inject constructor(
    private val heroRepository: HeroRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<HeroDomainEntity, GetHero.Params>(dispatcher) {

    override fun invoke(params: Params): Flow<HeroDomainEntity> {
       return heroRepository.getHero(params.id)
    }

    data class Params(val id: Int)

}