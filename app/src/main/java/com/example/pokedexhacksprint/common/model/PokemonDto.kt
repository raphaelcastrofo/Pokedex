package com.example.pokedexapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedexhacksprint.common.data.PokemonEntity
import kotlinx.coroutines.flow.Flow

data class PokemonDto(
    val id: Int,
    val name: String,
    val url: String?,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val sprites: Sprites

)  {

    val frontFullDefault: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getPokemonIdFromUrl(url)}.png"


    private fun getPokemonIdFromUrl(url: String?): Int {
        return url?.split("pokemon/")?.getOrNull(1)?.split("/")?.getOrNull(0)?.toIntOrNull() ?: id
    }

    data class TypeSlot(val type: PokemonType)
    data class PokemonType(val name: String)

    data class StatSlot(val base_stat: Int, val stat: Stat)
    data class Stat(val name: String)

    data class Sprites(val front_default: String)
}

/*@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemon(): List<PokemonEntity>
}*/


@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun getPokemonList(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)
}

