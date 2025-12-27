package com.plum.superheroapp.presentation.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.interactor.GetHero
import com.plum.superheroapp.domain.interactor.UpdateHero
import com.plum.superheroapp.presentation.BlocViewModel
import com.plum.superheroapp.presentation.navigation.NavigationArgument
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
open class HeroDetailsViewModel @Inject constructor(
    private val getHero: GetHero,
    private val updateHero: UpdateHero,
    private val savedStateHandle: SavedStateHandle
) : BlocViewModel<HeroDetailsEvent, HeroDetailsState, NavigationTarget>() {

    private val heroId get() = savedStateHandle.get<Int>(NavigationArgument.HeroId.param)!!


    override val _uiState: StateFlow<HeroDetailsState> =
        getHero.execute(GetHero.Params(heroId))
            .map { it.getOrThrow() }
            .catch { addError(it) }
            .map { hero ->
                HeroDetailsState.Content(
                    hero = hero
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HeroDetailsState.Loading
            )


    init {
        on(HeroDetailsEvent.UpdateSquadMember::class) {
            updateHero.execute(UpdateHero.Params(it.hero))
                .fold(
                    onSuccess = {},
                    onFailure = { addError(it) }
                )
        }
    }

}


sealed interface HeroDetailsEvent {
    data class UpdateSquadMember(val hero: HeroDomainEntity): HeroDetailsEvent
}


sealed interface HeroDetailsState {
    data class Content(
        val hero: HeroDomainEntity
    ) : HeroDetailsState

    object Loading : HeroDetailsState
}


sealed interface NavigationTarget {
    object Heroes : NavigationTarget
}

