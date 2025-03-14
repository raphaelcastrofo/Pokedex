package com.example.pokedexhacksprint.common.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: String,
    val stats: String,
    val frontDefault: String
)
