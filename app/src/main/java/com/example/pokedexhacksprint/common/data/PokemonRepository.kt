package com.example.pokedexhacksprint.common.data


import android.util.Log
import com.example.pokedexapp.PokemonDao
import com.example.pokedexapp.PokemonDto
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val apiService: PokedexApi,
    private val pokemonDao: PokemonDao
) {

    val pokemons: Flow<List<PokemonEntity>> = pokemonDao.getPokemonList()


    suspend fun fetchPokemons() {
        try {
            val response = apiService.getPokemonList()
            if (response.isSuccessful) {


                val pokemonList = response.body()?.results?.map { pokemonDto ->
                    PokemonEntity(
                        id = pokemonDto.id,
                        name = pokemonDto.name,
                        weight = pokemonDto.weight.toDouble() / 10.0,
                        height = pokemonDto.height.toDouble() / 10.0,
                        types = pokemonDto.types.joinToString { it.type.name },
                        stats = pokemonDto.stats.joinToString { "${it.stat.name}:${it.base_stat}" },
                        frontDefault = pokemonDto.frontFullDefault
                    )
                } ?: emptyList()

                pokemonDao.insertAll(pokemonList)
            } else {
                Log.e("PokemonRepository", "Erro na resposta da API: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Erro ao buscar dados da API", e)
        }
    }
}
