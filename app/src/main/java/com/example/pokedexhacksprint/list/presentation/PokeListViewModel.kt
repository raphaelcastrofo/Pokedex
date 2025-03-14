package com.example.pokedexhacksprint.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexapp.PokemonDto
import com.example.pokedexhacksprint.common.data.RetrofitClient
import com.example.pokedexhacksprint.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import com.example.pokedexhacksprint.common.data.PokemonEntity
import com.example.pokedexhacksprint.common.data.PokemonRepository
import com.example.pokedexhacksprint.common.data.NetworkUtils


class PokeListViewModel (
    private val listService: ListService
): ViewModel() {

    private val _UiPokemons = MutableStateFlow<List<PokemonDto>>(emptyList())
    val uiPokemons: StateFlow<List<PokemonDto>> = _UiPokemons
    private var currentOffset = 0 // para controlar o deslocamento dos pokemons

    init {
        fetchPokemons()
    }

    // Funcao que carrega pokemons com limite e deslocamento
    fun fetchPokemons(){
        viewModelScope.launch (Dispatchers.IO){
            val response = listService.getPokemons(limit = 20, offset = currentOffset)

            if (response.isSuccessful){
                val pokemons = response.body()?.results

                if (pokemons != null){
                    _UiPokemons.value = _UiPokemons.value + pokemons
                    currentOffset += 20 // Atualiza o offset para carregar os proximos pokemons
                }
            } else {
                Log.d("PokeListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    fun fetchMorePokemons(){
        fetchPokemons()
    }


    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return PokeListViewModel(
                    listService
                ) as T
            }
        }
    }
}



