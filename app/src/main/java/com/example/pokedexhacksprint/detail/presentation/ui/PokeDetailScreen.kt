package com.example.pokedexhacksprint.detail.presentation.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokedexapp.PokemonDto
import com.example.pokedexhacksprint.R
import com.example.pokedexhacksprint.detail.presentation.PokeDetailViewModel
import com.example.pokedexhacksprint.ui.theme.PokedexHackSprintTheme


@Composable
fun PokeDetailScreen(
    pokeName: String,
    navHostController: NavHostController,
    detailViewModel: PokeDetailViewModel
){
    val PokemonDto by detailViewModel.uiPoke.collectAsState()
    detailViewModel.fetchMovieDetail(pokeName)

    PokemonDto?.let{
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
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
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.name)

            }
            PokedexScreen(it)
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
        PokemonImage()
        PokemonInfo(pokemon.name, pokemon.weight, pokemon.height)
        BaseStats()
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
fun PokemonImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF878787)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.charizard),
            contentDescription = "charizar"
        )
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
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Text(text = "${weight}KG", color = Color.White)
            Text(text = "${height}M", color = Color.White)
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
fun BaseStats() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Base Stats", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        StatBar("HP", 168, 300, Color.Red)
        StatBar("ATK", 205, 300, Color.Yellow)
        StatBar("DEF", 64, 300, Color.Blue)
        StatBar("SPD", 204, 300, Color.Green)
        StatBar("EXP", 295, 1000, Color(0xFF4CAF50))
    }
}



@Composable
fun StatBar(label: String, value: Int, max: Int, color: Color) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, color = Color.White)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = { value / max.toFloat() },
                color = color,
                trackColor = Color.Gray,
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$value/$max", color = Color.White, fontSize = 12.sp)
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun BaseStatsPreview() {
//    PokedexHackSprintTheme {
//        PokedexScreen(
//            pokemonName = "Charizard",
//            pokemonId = 6,
//            weight = 90.5f,
//            height = 1.7f
//        )
//    }
//}




