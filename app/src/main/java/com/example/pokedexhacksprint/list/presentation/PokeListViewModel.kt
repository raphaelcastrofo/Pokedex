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
import com.example.pokedexapp.PokemonDto.Sprites
import com.example.pokedexapp.PokemonDto.StatSlot
import com.example.pokedexapp.PokemonDto.TypeSlot
import com.example.pokedexhacksprint.common.data.PokemonEntity
import com.example.pokedexhacksprint.common.data.PokemonRepository
import com.example.pokedexhacksprint.common.data.NetworkUtils
import com.example.pokedexhacksprint.common.data.PokemonListUiState
import com.example.pokedexhacksprint.common.data.PokemonUiData
import kotlinx.coroutines.delay
import kotlin.Int


class PokeListViewModel(
    private val listService: ListService
) : ViewModel() {


//    FOI ALTERADO PARA FAZER A CHAMADA OFFLINE


    private val _UiPokemons = MutableStateFlow<PokemonListUiState>(PokemonListUiState())
    val uiPokemons: StateFlow<PokemonListUiState> = _UiPokemons
    private var currentOffset = 0

    private val _UiAllPokemons = MutableStateFlow<PokemonListUiState>(PokemonListUiState())
    val uiAllPokemons: StateFlow<PokemonListUiState> = _UiAllPokemons


    //    _________________________________________________________________________


    private val _searchError = MutableStateFlow<String?>(null)
    val searchError: StateFlow<String?> = _searchError

    private var isSearching = false

    init {
        fetchPokemons()
    }


    //    FOI ALTERADO PARA FAZER A CHAMADA OFFLINE


    fun fetchPokemons() {
        viewModelScope.launch(Dispatchers.IO) {

            _UiAllPokemons.value = PokemonListUiState(isLoading = true)

            try {

                val response = listService.getPokemons(limit = 20, offset = currentOffset)
                if (response.isSuccessful) {
                    val pokemons = response.body()?.results
                    if (pokemons != null) {

                        val pokemonUiDataList =  pokemons.map { PokemonDto ->
                            PokemonUiData(
                                id = PokemonDto.id,
                                name = PokemonDto.name,
                                url = PokemonDto.url,
                                height = PokemonDto.height,
                                weight = PokemonDto.weight,
                                types = PokemonDto.types,
                                stats = PokemonDto.stats,
                                sprites = PokemonDto.sprites
                            )
                        }
                        _UiAllPokemons.value = PokemonListUiState(list =  pokemonUiDataList)


//                        _UiAllPokemons.value = _UiAllPokemons.value + pokemons
                        if (!isSearching) {
                            _UiPokemons.value = _UiAllPokemons.value
                        }
                        currentOffset += 20
                    }
                } else {
                    _UiAllPokemons.value = PokemonListUiState(isError = true)
                    Log.d("PokeListViewModel", "Request Error :: ${response.errorBody()}")
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
                _UiAllPokemons.value = PokemonListUiState(isError = true)
                Log.d("PokeListViewModel", "Error fetching Pokemons: ${ex.message}")
            }


        }
    }


    //    _________________________________________________________________________

    fun searchPokemon(query: String) {
        if (query.isEmpty()) {
            _UiPokemons.value = _UiAllPokemons.value
            _searchError.value = null
            isSearching = false
        } else {

            isSearching = true

            val filteredPokemons = _UiAllPokemons.value.list.filter {
                it.name.contains(query, ignoreCase = true)
            }

            if (filteredPokemons.isNotEmpty()) {
                _UiPokemons.value = PokemonListUiState(list = filteredPokemons)
                _searchError.value = null
            } else {
                _UiPokemons.value = PokemonListUiState(isError = true)
                _searchError.value = "Nenhum Pokémon encontrado"
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
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




