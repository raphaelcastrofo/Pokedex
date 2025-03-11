package com.example.pokedexhacksprint.list.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
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

) {
    val uiPokemons by viewModel.uiPokemons.collectAsState()
    val pokemonFontSolid = FontFamily(Font(R.font.pokemon_solid))
    val pokemonFontHollow = FontFamily(Font(R.font.pokemon_hollow))
    val listState = rememberLazyGridState()

    // Carregar mais pokemons quando scrollar
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect{ lastVisibleItemIndex ->
                if (lastVisibleItemIndex == uiPokemons.size - 1){
                    viewModel.fetchMorePokemons() // chama para carregar mais pokemons
                }
            }
    }

    PokemonListContent(
        pokemonFontSolid = pokemonFontSolid,
        pokemonFontHollow = pokemonFontHollow,
        pokemonDto = uiPokemons,
        listState = listState
    ) { itemClicked ->
        navController.navigate(route = "pokeDetail/${itemClicked.name}")
    }
}



@Composable
private fun PokemonListContent(
    pokemonFontSolid: FontFamily,
    pokemonFontHollow: FontFamily,
    pokemonDto: List<PokemonDto>,
    listState: LazyGridState,
    onClick: (PokemonDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color(0xFF767676))
    ) {

        // Camada amarela do texto
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .background(Color(0xFFe74343))
            .padding(top = 16.dp)
        ) {
            Text(
                text = "Pokedex",
                fontFamily = pokemonFontSolid,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 64.sp,
                    color = Color(0xFFffe800) // Cor amarela
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )

            // Camada de contorno do texto (cor azul)
            Text(
                text = "Pokedex",
                fontFamily = pokemonFontHollow,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold ,
                    fontSize = 64.sp,
                    color = Color(0xFF2553ff) //contorno
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }

        SearchBar(
            hint = "Search",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) { query ->

        }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF767676)),
        contentPadding = PaddingValues(8.dp),
        state = listState
    ) {
        items(pokemonDto) { pokemonDto ->
            PokemonItem(
                pokemonDto = pokemonDto,
                onClick = onClick
            )
        }
    }
}}

@Composable
fun PokemonItem(
    pokemonDto: PokemonDto,
    onClick: (PokemonDto) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick.invoke(pokemonDto) }
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
                text = pokemonDto.name.replaceFirstChar { it.uppercase() }, // Deixa a primeira letra do nome maiscula
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00BCD4)
            )
        }
    }
}


