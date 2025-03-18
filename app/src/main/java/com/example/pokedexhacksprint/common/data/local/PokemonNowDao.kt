package com.example.pokedexhacksprint.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao()
interface PokemonNowDao {
    @Query("Select * From pokemonentity Where:category = :category")
    fun getPokemonByCategory(category: String): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun getPokemonList(): Flow<List<com.example.pokedexhacksprint.common.data.PokemonEntity>>

    @Query("DELETE FROM pokemon_table")
    suspend fun clearPokemonTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<com.example.pokedexhacksprint.common.data.PokemonEntity>)
}