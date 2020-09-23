package com.cotel.composedice.domain

import kotlin.random.Random

sealed class DiceNumber(
    val value: Int,
    val rollHash: Long
) {
    class One(rollHash: Long) : DiceNumber(1, rollHash)
    class Two(rollHash: Long) : DiceNumber(2, rollHash)
    class Three(rollHash: Long) : DiceNumber(3, rollHash)
    class Four(rollHash: Long) : DiceNumber(4, rollHash)
    class Five(rollHash: Long) : DiceNumber(5, rollHash)
    class Six(rollHash: Long) : DiceNumber(6, rollHash)
}

fun rollDices(diceCount: Int = 10, randomSeed: Long = System.currentTimeMillis()): List<DiceNumber> {
    val random = Random(randomSeed)
    return (0 until diceCount).map {
        when (random.nextInt(1, 7)) {
            1 -> DiceNumber.One(randomSeed)
            2 -> DiceNumber.Two(randomSeed)
            3 -> DiceNumber.Three(randomSeed)
            4 -> DiceNumber.Four(randomSeed)
            5 -> DiceNumber.Five(randomSeed)
            6 -> DiceNumber.Six(randomSeed)
            else -> throw IllegalArgumentException()
        }
    }
}