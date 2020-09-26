package com.cotel.composedice.diceroller

import com.cotel.composedice.domain.DiceNumber
import com.cotel.composedice.domain.Sorting
import com.cotel.composedice.domain.rollDices
import kotlin.math.max
import kotlin.math.min

data class ViewState(
    val dicesToRoll: Int = 10,
    val minimumResult: Int = 1,
    val shouldAnimateDices: Boolean = true,
    val isClassified: Boolean = false,
    val sorting: Sorting = Sorting.NO_SORTING,
    val roll: List<DiceNumber> = rollDices(dicesToRoll)
) {
    val sortedRoll: List<DiceNumber>
        get() = when (sorting) {
            Sorting.NO_SORTING -> roll
            Sorting.ASC -> roll.sortedBy { it.value }
            Sorting.DESC -> roll.sortedByDescending { it.value }
        }

    val classifiedSortedRoll: List<List<DiceNumber>>
        get() = roll.groupBy { it.value }.values.toList().let { roll ->
            when (sorting) {
                Sorting.NO_SORTING -> roll
                Sorting.ASC -> roll.sortedBy { it.first().value }
                Sorting.DESC -> roll.sortedByDescending { it.first().value }
            }
        }

    fun changeClassified(newValue: Boolean): ViewState =
        copy(
            isClassified = newValue,
            sorting = if (sorting != Sorting.NO_SORTING) sorting else Sorting.ASC,
            shouldAnimateDices = false
        )

    fun changeSorting(): ViewState =
        copy(
            sorting = sorting.nextSorting(omitNoSorting = isClassified),
            shouldAnimateDices = false
        )

    fun addDiceToRoll(): ViewState =
        copy(dicesToRoll = dicesToRoll + 1)

    fun removeDiceToRoll(): ViewState =
        copy(dicesToRoll = max(dicesToRoll - 1, 0))

    fun incrementMinimumResult(): ViewState =
        copy(minimumResult = min(minimumResult + 1, 6))

    fun decrementMinimumResult(): ViewState =
        copy(minimumResult = max(minimumResult - 1, 1))
}