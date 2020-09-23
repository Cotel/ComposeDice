package com.cotel.composedice.diceroller

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.R

@Composable
fun Counter(
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(shape = CircleShape, border = BorderStroke(1.dp, Color.Black)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            RoundImage {
                Image(
                    asset = vectorResource(id = R.drawable.ic_remove),
                    colorFilter = ColorFilter(Color.Black, BlendMode.SrcIn),
                    modifier = Modifier.clickable(onClick = onDecrement)
                )
            }

            Spacer(modifier = Modifier.preferredWidth(16.dp))

            Text(
                text = count.toString(),
                modifier = Modifier
                    .preferredWidth(24.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.preferredWidth(16.dp))

            RoundImage {
                Image(
                    asset = vectorResource(id = R.drawable.ic_add),
                    colorFilter = ColorFilter(Color.Black, BlendMode.SrcIn),
                    modifier = Modifier.clickable(onClick = onIncrement)
                )
            }
        }
    }
}

@Composable
private fun RoundImage(image: @Composable () -> Unit) {
    Surface(shape = CircleShape, color = Color.Transparent) { image() }
}

// Previews

@Preview
@Composable
private fun CounterPreview() {
    Counter(count = 10, onDecrement = {}, onIncrement = {})
}