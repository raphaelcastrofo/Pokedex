package com.example.pokedexhacksprint.common.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexapp.PokemonDto

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Double,
    val weight: Double,
    val types: String,
    val stats: String,
    val frontDefault: String



)
fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height.toDouble() / 10.0,
        weight = weight.toDouble() / 10.0,
        types = types.joinToString { it.type.name },
        stats = stats.joinToString { "${it.stat.name}: ${it.base_stat}" },
        frontDefault = frontFullDefault
    )
}




