package com.cotel.composedice.domain

enum class Sorting {
    NO_SORTING, ASC, DESC;

    fun nextSorting(omitNoSorting: Boolean): Sorting = when (this) {
        NO_SORTING -> ASC
        ASC -> DESC
        DESC -> if (omitNoSorting) ASC else NO_SORTING
    }
}