package com.example.pokedexhacksprint.list.presentation.ui


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedexapp.PokemonDto
import com.example.pokedexhacksprint.R
import com.example.pokedexhacksprint.list.presentation.PokeListViewModel


@Composable
fun PokeListScreen(
    navController: NavHostController,
    viewModel: PokeListViewModel
    //onClick: (PokemonDto) -> Unit,
) {

    val uiPokemons by viewModel.uiPokemons.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        val pokemonFont = FontFamily(
            Font(R.font.pokemon_hollow)
        )
        Text(
            text = "Pokedex",
            fontFamily = pokemonFont,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = Color(0xFF3C4A59)
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        PokemonListContent(
            uiPokemons = uiPokemons
        )
        /* {
           itemClicked ->
        navController.navigate(route = "movieDetail/${itemClicked.id}")
    }*/
    }
}
@Composable
private fun PokemonListContent(
    uiPokemons: List<PokemonDto>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(uiPokemons) { pokemon ->
            PokemonItem(
                pokemonDto = pokemon,
                //onClick = onClick
            )
        }
    }
}

@Composable
fun PokemonItem(
    pokemonDto: PokemonDto,
    //onClick: (PokemonDto) -> Unit,
    ) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            //.clickable { onClick(pokemonDto) }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
        ) {
        Column(
            modifier = Modifier
                .background(Color.DarkGray) // Fundo colorido
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .width(120.dp)
                    .height(120.dp),
                contentScale = ContentScale.Crop,
                model = pokemonDto.frontFullDefault,
                contentDescription = "${pokemonDto.name} Poster image"
            )
            Text(
                text = pokemonDto.name.capitalize(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00BCD4)
            )
        }
    }
}


