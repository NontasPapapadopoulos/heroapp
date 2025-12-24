package com.plum.superheroapp.presentation.ui.screen.heroes

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.plum.superheroapp.presentation.ui.screen.composables.LoadingBox
import com.plum.superheroapp.presentation.ui.theme.SuperheroappTheme
import com.plum.superheroapp.presentation.ui.theme.contentSize10
import com.plum.superheroapp.presentation.ui.theme.contentSize15
import com.plum.superheroapp.presentation.ui.theme.contentSpacing4
import com.plum.superheroapp.presentation.ui.theme.contentSpacing5
import com.plum.superheroapp.presentation.ui.theme.contentSpacing6

@Composable
fun HeroesScreen(
    viewModel: HeroesViewModel = hiltViewModel(),
    navigateToHeroDetailsScreen: (Int) -> Unit
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

        is HeroesState.Content -> {
            HeroesContent(
                squad = state.squad,
                heroes = state.heroes,
                navigateToHeroDetails = navigateToHeroDetailsScreen
            )
        }

        HeroesState.Loading -> {
            LoadingBox()
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeroesContent(
    squad: List<Hero>,
    heroes: List<Hero>,
    navigateToHeroDetails: (Int) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Superhero Squad Maker",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
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
                    text = "My Squad",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )


                Squad(
                    squad = squad,
                    navigateToHeroDetails = navigateToHeroDetails
                )
            }

            Spacer(modifier = Modifier.height(contentSpacing5))


            LazyColumn() {
                heroes.forEach { hero ->

                    item {
                        HeroItem(
                            hero = hero,
                            onHeroClicked = navigateToHeroDetails
                        )
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
        modifier = Modifier.clickable {
            navigateToHeroDetails(hero.id)
        }
    ) {

        hero.image?.let {
            HeroImage(
                url = hero.image,
                size = contentSize15
            )
        }


        Text(
            text = hero.name
        )
    }

}


@Composable
private fun HeroItem(
    hero: Hero,
    onHeroClicked: (Int) -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = contentSpacing4)
                .clickable { onHeroClicked(hero.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {

            hero.image?.let {
                HeroImage(
                    url = hero.image,
                    size = contentSize15
                )
            }

            Spacer(modifier = Modifier.width(contentSpacing4))


            Text(
                text = hero.name,
            )

            Spacer(modifier = Modifier.height(contentSpacing4))

        }

        HorizontalDivider(
            modifier = Modifier.padding(start = contentSize15 + contentSpacing4)
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
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )


}


@Preview
@Composable
private fun HeroesScreenPreview() {
    SuperheroappTheme {
        HeroesContent(
            squad = getSquad(),
            heroes = getHeroes(),
            navigateToHeroDetails = {}
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
        Hero(
            id = 44,
            name = "Adira",
            image = "https://static.wikia.nocookie.net/disney/images/f/fa/Adira_Tangled.jpeg"
        ),
        Hero(
            id = 43,
            name = "Adelbert's Father",
            image = "https://static.wikia.nocookie.net/disney/images/b/b2/Profile-_Adelbert%27s_Father.jpeg"
        ),
        Hero(
            id = 13,
            name = "A.B.E.",
            image = "https://static.wikia.nocookie.net/disney/images/2/20/ABE.jpg"
        )
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
        Hero(
            id = 10,
            name = "627",
            image = "https://static.wikia.nocookie.net/disney/images/8/80/Profile_-_627.png"
        ),
        Hero(
            id = 12,
            name = "90's Adventure Bear",
            image = "https://static.wikia.nocookie.net/disney/images/3/3f/90%27s_Adventure_Bear_profile.png"
        ),
        Hero(
            id = 13,
            name = "A.B.E.",
            image = "https://static.wikia.nocookie.net/disney/images/2/20/ABE.jpg"
        ),
        Hero(
            id = 15,
            name = "A.R.F.",
            image = "https://static.wikia.nocookie.net/disney/images/b/ba/A.R.F.png"
        ),
        Hero(
            id = 16,
            name = "Abdullah",
            image = "https://static.wikia.nocookie.net/disney/images/3/3a/Abdullah.jpg"
        )
    )
}