package com.cotel.composedice.diceroller

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.domain.rollDices

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun DiceRollerScreen() {
    val (state, setState) = remember { mutableStateOf(ViewState()) }

    WithConstraints {
        val boxHeight = with(DensityAmbient.current) { constraints.maxHeight.toDp() }

        BackdropScaffold(
            appBar = { TopAppBar(title = { Text(text = "Compose Dices") }, elevation = 4.dp) },
            backLayerBackgroundColor = Color.LightGray,
            backLayerContent = {
                DiceDrawer(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            frontLayerElevation = 8.dp,
            frontLayerScrimColor = Color.Transparent,
            peekHeight = boxHeight / 2,
            headerHeight = 64.dp,
            frontLayerContent = {
                RollerControls(
                    onNewRoll = {
                        val newRoll = rollDices(state.dicesToRoll)
                        setState(state.copy(roll = newRoll, shouldAnimateDices = true))
                    },
                    dicesToRollCounter = state.dicesToRoll,
                    minimumResult = state.minimumResult,
                    sorting = state.sorting,
                    removeDice = { setState(state.removeDiceToRoll()) },
                    addDice = { setState(state.addDiceToRoll()) },
                    incrementMinimumResult = { setState(state.incrementMinimumResult()) },
                    decrementMinimumResult = { setState(state.decrementMinimumResult()) },
                    onChangeSorting = { setState(state.changeSorting()) }
                )
            }
        )
    }
}
