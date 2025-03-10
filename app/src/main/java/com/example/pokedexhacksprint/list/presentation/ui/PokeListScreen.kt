package com.example.pokedexhacksprint.list.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedexhacksprint.R
import com.example.pokedexhacksprint.common.model.PokeDto
import com.example.pokedexhacksprint.list.presentation.PokeListViewModel


@Composable
fun PokeListScreen(
    navController: NavController,
    viewModel: PokeListViewModel
) {
    val Pokemons by viewModel.uiPokemons.collectAsState()

    PokeListContent(
        PokemonsList = Pokemons
    )
}

@Composable
private fun PokeListContent(
    PokemonsList: List<PokeDto>
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Título fixo antes da lista
        item {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 40.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.SemiBold,
                text = "PokeDex",
            )
        }

        // Lista de Pokémons
        items(PokemonsList) { pokeDto ->
            PokeItem(pokeDto = pokeDto)
        }
    }
}

@Composable
private fun PokeItem(
    pokeDto: PokeDto
){
    AsyncImage(
        modifier = Modifier
            .padding(end = 4.dp)
            .width(120.dp)
            .height(150.dp),
        contentScale = ContentScale.Crop,
        model = pokeDto.url,
        contentDescription = "${pokeDto.name} Poster image"
    )

    Spacer(
        modifier = Modifier.padding(8.dp)
    )

    Text(
        text = pokeDto.name
    )
}