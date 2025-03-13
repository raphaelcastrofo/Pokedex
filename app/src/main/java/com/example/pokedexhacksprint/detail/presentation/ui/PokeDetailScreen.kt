package com.example.pokedexhacksprint.detail.presentation.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedexapp.PokemonDto
import com.example.pokedexhacksprint.R
import com.example.pokedexhacksprint.detail.presentation.PokeDetailViewModel


@Composable
fun PokeDetailScreen(
    pokeName: String,
    navHostController: NavHostController,
    detailViewModel: PokeDetailViewModel
){
    val PokemonDto by detailViewModel.uiPoke.collectAsState()

    LaunchedEffect(pokeName) {
        detailViewModel.cleanPokemonId()
        detailViewModel.fetchMovieDetail(pokeName)
    }

    PokemonDto?.let{ pokemon ->
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            Row (
                modifier = Modifier.fillMaxWidth().background(Color.DarkGray),
                verticalAlignment = Alignment.CenterVertically

            ){
                IconButton(onClick = {
                    detailViewModel.cleanPokemonId()
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"

                    )
                }

                Text(
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.padding(start = 4.dp),
                    text = pokemon.name)

            }
            PokedexScreen(pokemon)
        }
    }
}

@Composable
fun PokedexScreen(pokemon: PokemonDto) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        TopBar(pokemon.name, pokemon.id)
        Box(
            modifier = Modifier
                .height(180.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF878686)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = pokemon.sprites.front_default,
                contentDescription = "Pokemon Image",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Crop

            )
        }
        PokemonInfo(pokemon.name, pokemon.weight, pokemon.height)
        pokemon.stats.forEach { stat ->
            StatBar(
                label = stat.stat.name.capitalize(),
                value = stat.base_stat,
                statName = stat.stat.name

            )
        }
    }
}

@Composable
fun TopBar(name: String, id: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Pokedex", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "#$id", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun PokemonInfo(name: String, weight: Int, height: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Weight: ${weight} KG", color = Color.White)
            Text(text = "Height: ${height} M", color = Color.White)
        }
    }
}


@Composable
fun PokemonInfo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "charizard", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Row {
            TypeBadge("flying", Color(0xFF81D4FA))
            Spacer(modifier = Modifier.width(8.dp))
            TypeBadge("fire", Color(0xFFE57373))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Text(text = "90.5 KG", color = Color.White)
            Text(text = "1.7 M", color = Color.White)
        }
    }
}



@Composable
fun TypeBadge(type: String, color: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.background(color) // Definindo a cor correta
    ) {
        Text(
            text = type,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BaseStats(statName: String, value: Int) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Base Stats",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = statName, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            progress = value / 300f,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = Color.Red
        )

    }
}

// Mapeando as cores para cada stat
val statColorMap = mapOf(
    "hp" to Color(0xFFFC4C4C), // Vermelho para HP
    "attack" to Color(0xFF4C91FC), // Azul para Ataque
    "defense" to Color(0xFF8BC34A), // Verde para Defesa
    "special-attack" to Color(0xFFFFC107), // Amarelo para Ataque Especial
    "special-defense" to Color(0xFF9C27B0), // Roxo para Defesa Especial
    "speed" to Color(0xFF00BCD4) // Ciano para Velocidade
)


@Composable
fun StatBar(label: String, value: Int, statName: String) {
    val color = statColorMap[statName] ?: Color.Gray
    val maxValue = 255f
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text(text = label, color = Color.White)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = value / maxValue,
                color = color,
                trackColor = Color.Gray,
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$value / $maxValue",
                color = Color.White,
                fontSize = 12.sp)
        }
    }
}




