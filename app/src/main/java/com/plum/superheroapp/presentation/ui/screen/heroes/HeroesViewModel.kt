package com.plum.superheroapp.presentation.ui.screen.heroes

import androidx.lifecycle.viewModelScope
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.executeAsFlow
import com.plum.superheroapp.domain.interactor.FetchHeroes
import com.plum.superheroapp.domain.interactor.GetHeroes
import com.plum.superheroapp.domain.interactor.GetSquad
import com.plum.superheroapp.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
open class HeroesViewModel @Inject constructor(
    private val getHeroes: GetHeroes,
    private val getSquad: GetSquad,
    private val fetchHeroes: FetchHeroes
): BlocViewModel<HeroesEvent, HeroesState, NavigationTarget>() {

    private val heroesFlow = getHeroes.execute(Unit)
        .map { it.getOrThrow() }
        .map { heroes ->
            heroes.toHeroItems()
        }
        .catch { addError(it) }

    private val squadFlow = getSquad.execute(Unit)
        .map { it.getOrThrow() }
        .map { heroes ->
            heroes.toHeroItems()
        }
        .catch { addError(it) }

    private val throwableFlow = MutableStateFlow<Throwable?>(null)

    private val isLoadingFlow = MutableSharedFlow<Boolean>()

    val fetchHeroesFlow = fetchHeroes.executeAsFlow(FetchHeroes.Params())
        .map { it.getOrThrow() }
        .catch {
            addError(it)
            throwableFlow.emit(it)
        }


    override val _uiState: StateFlow<HeroesState> = combine(
        heroesFlow.onStart { emit(listOf()) },
        squadFlow.onStart { emit(listOf()) },
        fetchHeroesFlow.onStart { emit(Unit) },
        throwableFlow,
        isLoadingFlow.onStart { emit(false) }
    ) { heroes, squad, _, throwable, isLoading ->

        val isErrorState = heroes.isEmpty() && throwable is UnknownHostException

        when {
            isErrorState -> HeroesState.Error
            isLoading -> HeroesState.Loading
            else -> {
                HeroesState.Content(
                    heroes = heroes,
                    squad = squad,
                )
            }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HeroesState.Loading
    )


    init {
        on(HeroesEvent.SelectHero::class) {
            _navigationFlow.emit(NavigationTarget.HeroDetails(it.id))
        }

        on(HeroesEvent.FetchHeroes::class) {
            fetchHeroes.execute(FetchHeroes.Params(it.page))
                .fold(
                    onSuccess = {
                        throwableFlow.emit(null)
                        isLoadingFlow.emit(false)
                    },
                    onFailure = { addError(it) }
                )
        }

        on(HeroesEvent.ShowLoading::class) {
            isLoadingFlow.emit(true)
        }
    }


}



sealed interface HeroesEvent {
    data class SelectHero(val id: Int): HeroesEvent
    data class FetchHeroes(val page: Int): HeroesEvent
    object ShowLoading: HeroesEvent
}


sealed interface HeroesState {
    data class Content(
        val heroes: List<Hero>,
        val squad: List<Hero>,
    ): HeroesState

    object Error: HeroesState
    object Loading: HeroesState
}

sealed interface NavigationTarget {
    data class HeroDetails(val id: Int): NavigationTarget
}



data class Hero(
    val id: Int,
    val name: String,
    val image: String?
)


fun List<HeroDomainEntity>.toHeroItems(): List<Hero> {
    return this.map { hero ->
        Hero(
            id = hero.id,
            name = hero.name,
            image = hero.imageUrl
        )
    }
}
