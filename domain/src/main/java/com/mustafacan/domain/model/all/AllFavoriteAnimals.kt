package com.mustafacan.domain.model.all

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog

data class AllFavoriteAnimals(
    var dogs: List<Dog> = listOf(),
    var birds: List<Bird> = listOf(),
    var cats: List<Cat> = listOf()
) {
    fun getCount(): Int {
        return dogs.size + cats.size + birds.size
    }
}
