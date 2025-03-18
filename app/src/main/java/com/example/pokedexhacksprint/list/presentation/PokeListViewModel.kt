package com.example.pokedexhacksprint.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexhacksprint.common.data.RetrofitClient
import com.example.pokedexhacksprint.detail.data.PokemonListRepository
import com.example.pokedexhacksprint.list.data.ListService
import com.example.pokedexhacksprint.list.presentation.ui.PokemonListUiState
import com.example.pokedexhacksprint.list.presentation.ui.PokemonUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PokeListViewModel(
    private val repository: PokemonListRepository
) : ViewModel() {


    private val _UiPokemons = MutableStateFlow<PokemonListUiState>(PokemonListUiState())
    val uiPokemons: StateFlow<PokemonListUiState> = _UiPokemons
    private var currentOffset = 0

    private val _UiAllPokemons = MutableStateFlow<PokemonListUiState>(PokemonListUiState())
    val uiAllPokemons: StateFlow<PokemonListUiState> = _UiAllPokemons

    private val _searchError = MutableStateFlow<String?>(null)
    val searchError: StateFlow<String?> = _searchError

    private var isSearching = false

    init {
        fetchPokemons()
    }

    fun fetchPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNowPokemon()
            if (result.isSuccess) {
                val pokemons = result.getOrNull()?.results
                if (pokemons != null) {
                    val pokemonUiDataList = pokemons.map { PokemonDto ->

                        PokemonUiData(
                            id = PokemonDto.id,
                            name = PokemonDto.name,
                            url = PokemonDto.url,
                            height = PokemonDto.height,
                            weight = PokemonDto.weight,
                            types = PokemonDto.types,
                            stats = PokemonDto.stats,
                            sprites = PokemonDto.sprites,
                        )
                    }
                    _UiPokemons.value = PokemonListUiState(list = pokemonUiDataList)
                    if (!isSearching) {
                        currentOffset += 20
                    } else {
                        _UiPokemons.value = PokemonListUiState(list = pokemonUiDataList)
                    }
                }
            }
        }
    }


    fun searchPokemon(query: String) {
        if (query.isEmpty()) {

            _UiPokemons.value = _UiPokemons.value
            _searchError.value = null
            isSearching = false
        } else {

            isSearching = true

            val filteredPokemons = _UiAllPokemons.value.list.filter {
                it.name.contains(query, ignoreCase = true)
            }

            if (filteredPokemons.isNotEmpty()) {

                _UiPokemons.value = PokemonListUiState(isLoading = true)
                _searchError.value = null
            } else {


                _searchError.value = "Nenhum Pokémon encontrado"
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                val repository = PokemonListRepository(listService)
                return PokeListViewModel(
                    repository = PokemonListRepository(listService)
                )as T
            }
        }
    }
}




