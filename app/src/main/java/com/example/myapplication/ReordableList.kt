package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ReorderableList(
    items: List<Block>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    var overscrollJob by remember { mutableStateOf<Job?>(null) }

    val reorderableListState = rememberReorderableListState(onMove = onMove)

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        reorderableListState.onDrag(offset)

                        if (overscrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        reorderableListState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overscrollJob =
                                    scope.launch { reorderableListState.lazyListState.scrollBy(it) }
                            }
                            ?: run { overscrollJob?.cancel() }
                    },
                    onDragStart = { offset -> reorderableListState.onDragStart(offset) },
                    onDragEnd = { reorderableListState.onDragInterrupted() },
                    onDragCancel = { reorderableListState.onDragInterrupted() }
                )
            }
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .background(Color.LightGray),
        state = reorderableListState.lazyListState,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        itemsIndexed(items) { index, item ->

            val modifier = if(index == reorderableListState.currentIndexOfDraggedItem) Modifier.background(
                Color.Red)
            else Modifier
            Card(
                modifier = Modifier
                    .composed {
                        val offsetOrNull =
                            reorderableListState.elementDisplacement.takeIf {
                                index == reorderableListState.currentIndexOfDraggedItem
                            }

                        Modifier
                            .graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                            .background(Color.Red)
                    }
                    .padding(horizontal = 5.dp)
                    .height(70.dp)
                    .fillMaxWidth()
            ) {
                when (item) {
                    is BlockInit -> BlockInit(item)
                    else -> Text("someothershit")
                }
                //checkId(item,modifier)
            }
        }
    }
}