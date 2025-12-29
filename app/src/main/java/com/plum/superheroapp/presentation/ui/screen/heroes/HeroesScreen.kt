package com.plum.superheroapp.presentation.ui.screen.heroes

import android.content.Intent
import android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.plum.superheroapp.R
import com.plum.superheroapp.presentation.exception.errorStringResource
import com.plum.superheroapp.presentation.ui.screen.composables.LoadingBox
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.HERO
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.RETRY_BUTTON
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.SQUAD
import com.plum.superheroapp.presentation.ui.theme.SuperheroappTheme
import com.plum.superheroapp.presentation.ui.theme.contentSize10
import com.plum.superheroapp.presentation.ui.theme.contentSize16
import com.plum.superheroapp.presentation.ui.theme.contentSize4
import com.plum.superheroapp.presentation.ui.theme.contentSpacing1
import com.plum.superheroapp.presentation.ui.theme.contentSpacing2
import com.plum.superheroapp.presentation.ui.theme.contentSpacing4
import com.plum.superheroapp.presentation.ui.theme.contentSpacing5

@Composable
fun HeroesScreen(
    viewModel: HeroesViewModel = hiltViewModel(),
    navigateToHeroDetailsScreen: (Int) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect { error ->
            Toast.makeText(
                context,
                context.resources.errorStringResource(error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationFlow.collect { target ->
            if (target is NavigationTarget.HeroDetails)
                navigateToHeroDetailsScreen(target.id)
        }
    }


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HeroesState.Content -> {
            HeroesContent(
                squad = state.squad,
                heroes = state.heroes,
                navigateToHeroDetails = { viewModel.add(HeroesEvent.SelectHero(it)) },
                fetchHeroes = { viewModel.add(HeroesEvent.FetchHeroes(it)) },
            )
        }

        HeroesState.Loading -> {
            LoadingBox()
        }

        HeroesState.Error -> {
            ErrorState(
                fetchHeroes = { viewModel.add(HeroesEvent.FetchHeroes(it)) },
                showLoading = { viewModel.add(HeroesEvent.ShowLoading) }
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeroesContent(
    squad: List<Hero>,
    heroes: List<Hero>,
    navigateToHeroDetails: (Int) -> Unit,
    fetchHeroes: (Int) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.hero_squad_maker),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W500,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(contentSpacing4)
        ) {
            if (squad.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.my_squad),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W500
                )

                Spacer(
                    modifier = Modifier.height(contentSpacing2)
                )

                Squad(
                    squad = squad,
                    navigateToHeroDetails = navigateToHeroDetails
                )
            }

            Spacer(modifier = Modifier.height(contentSpacing5))


            val listState = rememberSaveable(
                saver = LazyListState.Saver
            ) {
                LazyListState()
            }


            LazyColumn(
                state = listState
            ) {
                itemsIndexed(heroes) { index, hero ->

                    key(hero) {
                        HeroItem(
                            hero = hero,
                            onHeroClicked = navigateToHeroDetails
                        )
                    }

                    LaunchedEffect(index) {
                        if (index % 40 == 0) {
                            val page = (index/40 + 1)
                            fetchHeroes(page)
                        }
                    }
                }
            }
        }
    }

}


@Composable
private fun Squad(
    squad: List<Hero>,
    navigateToHeroDetails: (Int) -> Unit
) {


    LazyRow() {
        squad.forEach { hero ->

            item(key = hero.id) {
                SquadMember(
                    hero = hero,
                    navigateToHeroDetails = navigateToHeroDetails
                )
            }

        }
    }

}


@Composable
private fun SquadMember(
    hero: Hero,
    navigateToHeroDetails: (Int) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = contentSpacing1)
            .width(contentSize16)
            .clickable {
                navigateToHeroDetails(hero.id)
             }
            .testTag(SQUAD+hero.id)
    ) {

        hero.image?.let {
            HeroImage(
                url = hero.image,
                size = contentSize16
            )
        }

        Spacer(
            modifier = Modifier.height(contentSpacing1)
        )


        Text(
            text = hero.name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

}


@Composable
private fun HeroItem(
    hero: Hero,
    onHeroClicked: (Int) -> Unit
) {
    val imageSize = contentSize10
    val spaceSize = contentSize4

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = contentSpacing4)
                .clickable { onHeroClicked(hero.id) }
                .testTag(HERO+hero.id),
            verticalAlignment = Alignment.CenterVertically
        ) {

            hero.image?.let {
                HeroImage(
                    url = hero.image,
                    size = imageSize
                )
            }

            Spacer(modifier = Modifier.width(contentSpacing4))


            Text(
                text = hero.name,
            )

            Spacer(modifier = Modifier.height(spaceSize))

        }

        HorizontalDivider(
            modifier = Modifier.padding(start = imageSize + spaceSize)
        )

    }

}


@Composable
private fun HeroImage(
    url: String,
    size: Dp
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(MaterialTheme.colorScheme.onSurfaceVariant),
        error = ColorPainter(MaterialTheme.colorScheme.onSurfaceVariant),
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}


@Composable
private fun ErrorState(
    fetchHeroes: (Int) -> Unit,
    showLoading: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.internet_off),
        )

        Spacer(modifier = Modifier.height(contentSpacing4))
        val context = LocalContext.current

        Row {
            Button(
                onClick = {
                    context.startActivity(
                        Intent(ACTION_WIFI_SETTINGS)
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.open_wifi)
                )
            }

            Spacer(modifier = Modifier.width(contentSpacing1))

            Button(
                onClick = {
                    context.startActivity(
                        Intent(ACTION_DATA_ROAMING_SETTINGS)
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.open_roaming_data)
                )
            }

        }


        Spacer(modifier = Modifier.height(contentSpacing4))

        Button(
            onClick = {
                showLoading()
                fetchHeroes(1)
            },
            modifier = Modifier.testTag(RETRY_BUTTON)
        ) {
            Text(
                text = stringResource(R.string.retry)
            )
        }
    }
}


@Preview
@Composable
private fun HeroesScreenPreview() {
    SuperheroappTheme {
        HeroesContent(
            squad = getSquad(),
            heroes = getHeroes(),
            navigateToHeroDetails = {},
            fetchHeroes = {},
        )
    }
}


private fun getSquad(): List<Hero> {
    return listOf(
        Hero(
            id = 22,
            name = "Able",
            image = "https://static.wikia.nocookie.net/disney/images/a/af/Able.png"
        ),
        Hero(
            id = 33,
            name = "Prince Achmed",
            image = "https://static.wikia.nocookie.net/disney/images/7/76/Aladdin-disneyscreencaps.com-1123.jpg"
        ),

    )
}

private fun getHeroes(): List<Hero> {
    return listOf(
        Hero(
            id = 6,
            name = "'Olu Mel",
            image = "https://static.wikia.nocookie.net/disney/images/6/61/Olu_main.png"
        ),
        Hero(
            id = 7,
            name = ".GIFfany",
            image = "https://static.wikia.nocookie.net/disney/images/5/51/Giffany.png"
        ),

    )
}

class HeroesScreenConstants private constructor() {
    companion object {
        const val HERO = "hero"
        const val SQUAD = "squad"

        const val RETRY_BUTTON = "retry_button"
    }
}