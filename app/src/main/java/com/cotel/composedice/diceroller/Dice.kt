package com.cotel.composedice.diceroller

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.R
import com.cotel.composedice.domain.DiceNumber
import kotlin.random.Random

@Composable
fun Dice(
    number: DiceNumber,
    shouldAnimate: Boolean,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val diceNumberDrawable = when (number) {
        is DiceNumber.One -> R.drawable.dice_one
        is DiceNumber.Two -> R.drawable.dice_two
        is DiceNumber.Three -> R.drawable.dice_three
        is DiceNumber.Four -> R.drawable.dice_four
        is DiceNumber.Five -> R.drawable.dice_five
        is DiceNumber.Six -> R.drawable.dice_six
    }.run { vectorResource(id = this) }

    val rotation = animatedFloat(0f)
    val randomSpins = remember(number.rollHash) { Random.nextInt(1, 4) }
    val randomDuration = remember(number.rollHash) { Random.nextInt(600, 1200) }
    if (shouldAnimate) rotation.animateTo(
        targetValue = 360f * randomSpins,
        anim = tween(
            durationMillis = randomDuration,
            easing = CubicBezierEasing(0.3f, 0.9f, 1f, 1f)
        )
    )

    Image(
        asset = diceNumberDrawable,
        alpha = if (isEnabled) 1f else 0.4f,
        modifier = modifier
            .size(80.dp)
            .drawLayer(rotationZ = rotation.value)
    )
}

// Previews

@Preview
@Composable
private fun DicePreview() {
    Dice(number = DiceNumber.Six(0L), shouldAnimate = false)
}

@Preview
@Composable
private fun DisableDicePreview() {
    Dice(number = DiceNumber.Six(0L), shouldAnimate = false, isEnabled = false)
}