package com.cotel.composedice.diceroller

import androidx.compose.animation.animate
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.R
import com.cotel.composedice.domain.rollDices

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun DiceRollerScreen() {
    val (state, setState) = remember { mutableStateOf(ViewState()) }
    val fabRotation = animatedFloat(initVal = 0f)

    WithConstraints {
        val boxHeight = with(DensityAmbient.current) { constraints.maxHeight.toDp() }

        BackdropScaffold(
            appBar = { TopAppBar(title = { Text(text = "Compose Dices") }, elevation = 4.dp) },
            backdropScaffoldState = rememberBackdropState(initialValue = BackdropValue.Revealed),
            backLayerBackgroundColor = Color.LightGray,
            backLayerContent = {
                Stack {
                    DiceDrawer(
                        state = state,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    FloatingActionButton(
                        backgroundColor = Color.Black,
                        elevation = 4.dp,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                            .drawLayer(rotationZ = fabRotation.value),
                        onClick = {
                            val newRoll = rollDices(state.dicesToRoll)
                            setState(state.copy(roll = newRoll, shouldAnimateDices = true))

                            fabRotation.animateTo(
                                targetValue = 360f,
                                anim = tween(600),
                                onEnd = { _, _ -> fabRotation.snapTo(0f) }
                            )
                        }
                    ) {
                        Icon(
                            asset = vectorResource(id = R.drawable.ic_rerroll),
                            tint = Color.White
                        )
                    }
                }
            },
            frontLayerElevation = 8.dp,
            frontLayerScrimColor = Color.Transparent,
            peekHeight = boxHeight - 320.dp,
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
                    isClassified = state.isClassified,
                    removeDice = { setState(state.removeDiceToRoll()) },
                    addDice = { setState(state.addDiceToRoll()) },
                    incrementMinimumResult = { setState(state.incrementMinimumResult()) },
                    decrementMinimumResult = { setState(state.decrementMinimumResult()) },
                    onChangeSorting = { setState(state.changeSorting()) },
                    onChangeClassified = { setState(state.changeClassified(it)) }
                )
            }
        )
    }
}
