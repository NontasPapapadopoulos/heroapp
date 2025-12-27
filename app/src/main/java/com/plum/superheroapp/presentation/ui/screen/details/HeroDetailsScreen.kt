package com.plum.superheroapp.presentation.ui.screen.details

import android.widget.Toast
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.plum.superheroapp.R
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.presentation.ui.screen.composables.LoadingBox
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreenConstants.Companion.UPDATE_SQUAD_MEMBER_BUTTON
import com.plum.superheroapp.presentation.ui.theme.SuperheroappTheme
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
                navigateBack = navigateBack,
                updateSquadMember = { viewModel.add(HeroDetailsEvent.UpdateSquadMember(it)) }
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
    navigateBack: () -> Unit,
    updateSquadMember: (HeroDomainEntity) -> Unit
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


        UpdateSquadMemberButton(
            onClick = { updateSquadMember(hero) },
            isSquadMember = hero.isSquadMember
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
        if (hero.films.any { it.isNotEmpty() }) {
            Text(
                text = "${stringResource(R.string.films)} ${hero.films.joinToString(", ")}"
            )
        }

        if (hero.tvShows.any { it.isNotEmpty()}) {
            Text(
                text = "${stringResource(R.string.tv_shows)} ${hero.tvShows.joinToString(", ")}"
            )
        }

        if (hero.videoGames.any{ it.isNotEmpty() }) {
            Text(
                text = "${stringResource(R.string.video_games)} ${hero.videoGames.joinToString(", ")}"
            )
        }

        if (hero.allies.any { it.isNotEmpty() }) {
            Text(
                text = "${stringResource(R.string.allies)} ${hero.allies.joinToString(", ")}"
            )
        }

        if (hero.enemies.any { it.isNotEmpty() }) {
            Text(
                text = "${stringResource(R.string.enemies)}: ${hero.enemies.joinToString(", ")}"
            )
        }
    }
}


@Composable
private fun UpdateSquadMemberButton(
    onClick: () -> Unit,
    isSquadMember: Boolean
) {

    val containerColor = if (isSquadMember) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.primary

    val text = if (isSquadMember) stringResource(R.string.fire_from_squad)
    else stringResource(R.string.hide_to_squad)


    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .height(contentSize12)
            .padding(horizontal = contentSize4)
            .testTag(UPDATE_SQUAD_MEMBER_BUTTON),
        shape = RoundedCornerShape(contentSize2),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = containerColor
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.W500
        )
    }
}


@Preview
@Composable
private fun HeroDetailsScreenPreview() {
    SuperheroappTheme {
        HeroDetailsContent(
            navigateBack = {},
            updateSquadMember = {},
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


class HeroDetailsScreenConstants private constructor() {
    companion object {
        const val UPDATE_SQUAD_MEMBER_BUTTON = "update_squad_member_button"
    }
}