package com.example.pokedexhacksprint.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database([PokemonEntity::class], version = 1)
abstract class PokemonNowDataBase : RoomDatabase(){
    abstract fun PokemonNowDao(): PokemonNowDao

}
