package com.plum.superheroapp.presentation.ui.screen.details

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BackHand
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.presentation.navigation.HeroDetailsScreen
import com.plum.superheroapp.presentation.ui.screen.composables.LoadingBox
import com.plum.superheroapp.presentation.ui.screen.heroes.Hero
import com.plum.superheroapp.presentation.ui.theme.SuperheroappTheme
import com.plum.superheroapp.presentation.ui.theme.contentSize10
import com.plum.superheroapp.presentation.ui.theme.contentSize12
import com.plum.superheroapp.presentation.ui.theme.contentSize2
import com.plum.superheroapp.presentation.ui.theme.contentSize4
import com.plum.superheroapp.presentation.ui.theme.contentSpacing2
import com.plum.superheroapp.presentation.ui.theme.contentSpacing4
import com.plum.superheroapp.presentation.ui.theme.contentSpacing6

@Composable
fun HeroDetailsScreen(
    viewModel: HeroDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {


    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect { error ->
            println("error:" + error.message)
            Toast.makeText(
                context,
                error.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    when (val state = uiState) {
        is HeroDetailsState.Content -> {
            HeroDetailsContent(
                hero = state.hero,
                navigateBack = navigateBack
            )
        }

        HeroDetailsState.Loading -> {
            LoadingBox()
        }
    }
}

@Composable
private fun HeroDetailsContent(
    hero: HeroDomainEntity,
    navigateBack: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box() {
            AsyncImage(
                model = hero.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
            )

            BackButton(
                onClick = navigateBack,
                modifier = Modifier.align(TopStart)
                .padding(top = contentSpacing6, start = contentSpacing4)
                .size(contentSize12))
        }



        Text(
            text = hero.name,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(
                start = contentSpacing4,
                top = contentSpacing6,
                bottom = contentSpacing6
            )
        )


        HireButton(
            onClick = {}
        )

        Spacer(modifier = Modifier.height(contentSpacing6))

        HeroDetailsContainer(hero)
        
    }

}

@Composable
private fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier

    ) {
        Icon(
            imageVector = Icons.Outlined.Close,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "close"
        )
    }
}


@Composable
private fun HeroDetailsContainer(
    hero: HeroDomainEntity
) {

    Column(
        modifier = Modifier.padding(horizontal = contentSpacing4)
    ) {
        if (hero.films.isNotEmpty()) {
            Text(
                text = "Films: ${hero.films.joinToString(", ")}"
            )
        }

        if (hero.tvShows.isNotEmpty()) {
            Text(
                text = "Tv Shows: ${hero.tvShows.joinToString(", ")}"
            )
        }

        if (hero.videoGames.isNotEmpty()) {
            Text(
                text = "Video Games: ${hero.videoGames.joinToString(", ")}"
            )
        }

        if (hero.allies.isNotEmpty()) {
            Text(
                text = "Allies: ${hero.allies.joinToString(", ")}"
            )
        }

        if (hero.enemies.isNotEmpty()) {
            Text(
                text = "Enemies: ${hero.enemies.joinToString(", ")}"
            )
        }
    }
}

@Composable
private fun HeroDetail(
    label: String,
    details: List<String>
) {
    Text(
        text = "$label: ${details.joinToString(", ")}"
    )
}


@Composable
private fun HireButton(
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = contentSize4),
        shape = RoundedCornerShape(contentSize2)
    ) {
        Icon(
            imageVector = Icons.Outlined.BackHand,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(contentSpacing2))

        Text("Hire to Squad")
    }
}


@Preview
@Composable
private fun HeroDetailsScreenPreview() {
    SuperheroappTheme {
        HeroDetailsContent(
            navigateBack = {},
            hero = HeroDomainEntity(
                id = 16,
                allies = emptyList(),
                enemies = emptyList(),
                films = listOf("Cheetah"),
                imageUrl = "https://static.wikia.nocookie.net/disney/images/3/3a/Abdullah.jpg",
                name = "Abdullah",
                parkAttractions = emptyList(),
                shortFilms = emptyList(),
                tvShows = emptyList(),
                url = "https://api.disneyapi.dev/characters/16",
                videoGames = emptyList(),
                isSquadMember = false
            )
        )
    }
}