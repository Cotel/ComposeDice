package com.cotel.composedice.diceroller

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.align
import androidx.compose.foundation.layout.RowScope.align
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.domain.DiceNumber
import com.cotel.composedice.domain.rollDices

@Composable
fun DiceDrawer(
    state: ViewState,
    itemsPerRow: Int = 5,
    modifier: Modifier = Modifier
) {
    val chunkedRolls = state.sortedRoll.chunked(itemsPerRow)
    val classifiedRoll = state.classifiedSortedRoll
        .map { it.chunked(itemsPerRow) }

    ScrollableColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        if (state.isClassified) {
            classifiedRoll.forEachIndexed { index, numberGroup ->
                if (index != 0) Divider()
                numberGroup.forEach { chunk ->
                    SingleChunkDiceDrawer(
                        chunk = chunk,
                        minimumResult = state.minimumResult,
                        shouldAnimateDices = state.shouldAnimateDices
                    )
                }
            }
        } else {
            chunkedRolls.forEach { chunk ->
                SingleChunkDiceDrawer(
                    chunk = chunk,
                    minimumResult = state.minimumResult,
                    shouldAnimateDices = state.shouldAnimateDices
                )
            }
        }
    }
}

@OptIn(ExperimentalLayout::class)
@Composable
private fun SingleChunkDiceDrawer(
    chunk: List<DiceNumber>,
    minimumResult: Int,
    shouldAnimateDices: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.wrapContentWidth(Alignment.Start)) {
        chunk.forEach { number ->
            key(number) {
                Dice(
                    number = number,
                    shouldAnimate = shouldAnimateDices,
                    isEnabled = number.value >= minimumResult,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Previews

@Preview
@Composable
private fun OneChunkDiceDrawerPreview() {
    DiceDrawer(state = ViewState(roll = rollDices()))
}

@Preview
@Composable
private fun ClassifiedDiceDrawerPreview() {
    DiceDrawer(state = ViewState(roll = rollDices(), isClassified = true))
}