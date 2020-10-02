package com.cotel.composedice.diceroller

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.align
import androidx.compose.foundation.layout.RowScope.align
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.cotel.composedice.R
import com.cotel.composedice.domain.Sorting

@Composable
fun RollerControls(
    dicesToRollCounter: Int,
    minimumResult: Int,
    sorting: Sorting,
    isClassified: Boolean,
    onNewRoll: () -> Unit,
    addDice: () -> Unit,
    removeDice: () -> Unit,
    incrementMinimumResult: () -> Unit,
    decrementMinimumResult: () -> Unit,
    onChangeSorting: () -> Unit,
    onChangeClassified: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(topLeft = 8.dp, topRight = 8.dp),
        modifier = modifier.wrapContentHeight().fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RoundedDash(modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Roller Controls",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            CounterControl(
                title = "Dices to roll",
                counter = dicesToRollCounter,
                onIncrement = addDice,
                onDecrement = removeDice
            )

            Spacer(modifier = Modifier.height(16.dp))

            CounterControl(
                title = "Minimum result",
                counter = minimumResult,
                onIncrement = incrementMinimumResult,
                onDecrement = decrementMinimumResult
            )

            Spacer(modifier = Modifier.height(16.dp))

            SortingControl(sorting = sorting, onChangeSorting = onChangeSorting)

            Spacer(modifier = Modifier.height(16.dp))

            ClassifiedControl(isChecked = isClassified, onChange = onChangeClassified)

            Button(
                onClick = onNewRoll,
                backgroundColor = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "New roll",
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }
        }

    }
}

@Composable
private fun RowControl(
    title: String,
    control: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title, modifier = Modifier.weight(1f))
        control()
    }
}

@Composable
private fun CounterControl(
    title: String,
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    RowControl(title = title) {
        Counter(
            count = counter,
            onIncrement = onIncrement,
            onDecrement = onDecrement,
            modifier = Modifier.preferredHeight(32.dp)
        )
    }
}

@Composable
private fun SortingControl(
    sorting: Sorting,
    onChangeSorting: () -> Unit
) {
    RowControl(title = "Sorting") {
        OutlinedButton(
            onClick = onChangeSorting,
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .preferredSize(width = 136.dp, height = 32.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = when (sorting) {
                    Sorting.NO_SORTING -> "No sorting"
                    Sorting.ASC -> "Ascending"
                    Sorting.DESC -> "Descending"
                },
                textAlign = if (sorting == Sorting.NO_SORTING) TextAlign.Center else TextAlign.Start,
                style = MaterialTheme.typography.button,
                modifier = Modifier.weight(1f),
                color = Color.Black,
                fontSize = 11.sp
            )

            if (sorting == Sorting.ASC) {
                Image(
                    asset = vectorResource(id = R.drawable.ic_sort),
                    colorFilter = ColorFilter(Color.Black, BlendMode.SrcIn),
                    modifier = Modifier
                        .drawLayer(rotationZ = 180f, rotationY = 180f)
                )
            } else if (sorting == Sorting.DESC) {
                Image(
                    asset = vectorResource(id = R.drawable.ic_sort),
                    colorFilter = ColorFilter(Color.Black, BlendMode.SrcIn)
                )
            }
        }
    }
}

@Composable
fun ClassifiedControl(
    isChecked: Boolean,
    onChange: (Boolean) -> Unit
) {
    RowControl(title = "Classified") {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onChange,
            modifier = Modifier.preferredHeight(32.dp)
        )
    }
}


@Composable
private fun RoundedDash(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.preferredHeight(4.dp).preferredWidth(32.dp),
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(percent = 50)
    )
}

// Previews

@Preview
@Composable
private fun RollerControlsPreview() {
    RollerControls(
        dicesToRollCounter = 10,
        minimumResult = 1,
        sorting = Sorting.NO_SORTING,
        isClassified = true,
        onNewRoll = {},
        addDice = {},
        removeDice = {},
        incrementMinimumResult = {},
        decrementMinimumResult = {},
        onChangeSorting = {},
        onChangeClassified = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CounterControlPreview() {
    CounterControl(
        title = "Counter control",
        counter = 10,
        onIncrement = {},
        onDecrement = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SortingControlPreview() {
    Column {
        SortingControl(sorting = Sorting.NO_SORTING, onChangeSorting = {})
        SortingControl(sorting = Sorting.ASC, onChangeSorting = {})
        SortingControl(sorting = Sorting.DESC, onChangeSorting = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ClassifiedControlPreview() {
    ClassifiedControl(isChecked = true, onChange = {})
}
