package com.example.pokedexhacksprint

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedexhacksprint.list.presentation.PokeListViewModel
import com.example.pokedexhacksprint.list.presentation.ui.PokeListScreen

@Composable
fun PokedexApp(
    listViewModel: PokeListViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pokeList"){
        composable(route = "pokeList"){
            PokeListScreen(navController, listViewModel)
        }
    }
}