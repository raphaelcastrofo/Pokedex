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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.pokedexhacksprint.common.data.PokemonEntity
import com.example.pokedexhacksprint.common.data.PokemonRepository
import com.example.pokedexhacksprint.common.data.NetworkUtils
import kotlinx.coroutines.delay


class PokeListViewModel (
    private val listService: ListService
): ViewModel() {

    private val _UiPokemons = MutableStateFlow<List<PokemonDto>>(emptyList())
    val uiPokemons: StateFlow<List<PokemonDto>> = _UiPokemons
    private var currentOffset = 0

    private val _UiAllPokemons = MutableStateFlow<List<PokemonDto>>(emptyList())
    val uiAllPokemons: StateFlow<List<PokemonDto>> = _UiAllPokemons

    private val _searchError = MutableStateFlow<String?>(null)
    val searchError: StateFlow<String?> = _searchError

    private var isSearching = false

    init {
        fetchPokemons()
    }


    fun fetchPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPokemons(limit = 20, offset = currentOffset)

            if (response.isSuccessful) {
                val pokemons = response.body()?.results

                if (pokemons != null) {

                    _UiAllPokemons.value = _UiAllPokemons.value + pokemons


                    if (!isSearching) {
                        _UiPokemons.value = _UiAllPokemons.value
                    }

                    currentOffset += 20
                }
            } else {
                Log.d("PokeListViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }


    fun searchPokemon(query: String) {
        if (query.isEmpty()) {

            _UiPokemons.value = _UiAllPokemons.value
            _searchError.value = null
            isSearching = false
        } else {

            isSearching = true

            val filteredPokemons = _UiAllPokemons.value.filter {
                it.name.contains(query, ignoreCase = true)
            }

            if (filteredPokemons.isNotEmpty()) {

                _UiPokemons.value = filteredPokemons
                _searchError.value = null
            } else {

                _UiPokemons.value = emptyList()
                _searchError.value = "Nenhum Pokémon encontrado"
            }
        }
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



