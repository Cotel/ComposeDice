package com.cotel.composedice.domain

enum class Sorting {
    NO_SORTING, ASC, DESC;

    fun nextSorting(): Sorting = when (this) {
        NO_SORTING -> ASC
        ASC -> DESC
        DESC -> NO_SORTING
    }
}