package com.example.pokedexhacksprint.detail.presentation.ui

import android.graphics.drawable.Icon
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pokedexapp.PokemonDto
import com.example.pokedexhacksprint.R
import com.example.pokedexhacksprint.detail.data.DetailService
import com.example.pokedexhacksprint.detail.presentation.PokeDetailViewModel
import com.example.pokedexhacksprint.ui.theme.getTypeColor

@Composable
fun PokeDetailScreen(
    pokeName: String,
    navHostController: NavHostController,
    detailViewModel: PokeDetailViewModel
){
    val PokemonDto: PokemonDto? by detailViewModel.uiPoke.collectAsState()

    LaunchedEffect(pokeName) {
        detailViewModel.cleanPokemonId()
        detailViewModel.fetchMovieDetail(pokeName)
    }

    PokemonDto?.let{ pokemon ->
        Column (modifier = Modifier.fillMaxSize()){
            TopBar(pokemon,navHostController, detailViewModel)
            PokedexScreen(pokemon)
        }
    }
}

@Composable
fun TopBar(pokemon: PokemonDto,
           navHostController: NavHostController,
           detailViewModel: PokeDetailViewModel
) {
    val primaryTipe = pokemon.types.firstOrNull()?.type?.name ?: "normal"
    val cardBackgroundColor =  getTypeColor(primaryTipe)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFECECEC))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton( onClick =  {
            detailViewModel.cleanPokemonId()
            navHostController.popBackStack()
        },
            modifier = Modifier.padding(top = 16.dp)
        ){
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = " Back Button ")
        }
    }
}

@Composable
fun PokedexScreen(pokemon: PokemonDto) {
    val primaryTipe = pokemon.types.firstOrNull()?.type?.name ?: "normal"
    val cardBackgroundColor =  getTypeColor(primaryTipe)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))

    ) {

        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(cardBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = pokemon.sprites.front_default,
                contentDescription = "Pokemon Image",
                modifier = Modifier
                    .fillMaxSize()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "#${pokemon.id}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
            )
        }

        Text(
            text = pokemon.name.capitalize(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = cardBackgroundColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ){
            pokemon.types.forEachIndexed { _, type ->
                TypeBadge(type = type.type.name,
                    color = getTypeColor(type.type.name)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        PokemonInfo(pokemon.weight, pokemon.height)

        Spacer(modifier = Modifier
            .height(24.dp))

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
fun TypeBadge(type: String, color: Color) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(color, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = type.uppercase(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

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
        .padding(vertical = 4.dp, horizontal = 16.dp)
    ){
        Text(text = label, color = Color.DarkGray)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = value / maxValue,
                color = color,
                trackColor = Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$value / $maxValue",
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonInfo(weight: Int, height: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Weight: ${weight} KG",
                color = Color.DarkGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Height: ${height} M",
                color = Color.DarkGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}











