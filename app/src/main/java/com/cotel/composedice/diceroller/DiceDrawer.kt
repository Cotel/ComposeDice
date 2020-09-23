package com.cotel.composedice.diceroller

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.domain.rollDices

@Composable
fun DiceDrawer(
    state: ViewState,
    itemsPerRow: Int = 5,
    modifier: Modifier = Modifier
) {
    val minimumResult = state.minimumResult
    val chunkedRolls = state.sortedRoll.chunked(itemsPerRow)

    ScrollableColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        chunkedRolls.forEach { chunk ->
            Row(modifier = Modifier.fillMaxWidth()) {
                chunk.forEach { number ->
                    Dice(
                        number = number,
                        isEnabled = number.value >= minimumResult,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

// Previews

@Preview
@Composable
private fun DiceDrawerPreview() {
    DiceDrawer(state = ViewState(roll = rollDices()))
}