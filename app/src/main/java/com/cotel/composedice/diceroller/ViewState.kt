package com.cotel.composedice.diceroller

import com.cotel.composedice.domain.DiceNumber
import com.cotel.composedice.domain.Sorting
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class ViewState(
    val dicesToRoll: Int = 10,
    val minimumResult: Int = 1,
    val sorting: Sorting = Sorting.NO_SORTING,
    val roll: List<DiceNumber> = emptyList()
) {
    val sortedRoll: List<DiceNumber>
        get() = when (sorting) {
            Sorting.NO_SORTING -> roll
            Sorting.ASC -> roll.sortedBy { it.value }
            Sorting.DESC -> roll.sortedByDescending { it.value }
        }

    fun changeSorting(): ViewState =
        copy(sorting = sorting.nextSorting())

    fun addDiceToRoll(): ViewState =
        copy(dicesToRoll = dicesToRoll + 1)

    fun removeDiceToRoll(): ViewState =
        copy(dicesToRoll = max(dicesToRoll - 1, 0))

    fun incrementMinimumResult(): ViewState =
        copy(minimumResult = min(minimumResult + 1, 6))

    fun decrementMinimumResult(): ViewState =
        copy(minimumResult = max(minimumResult - 1, 1))
}