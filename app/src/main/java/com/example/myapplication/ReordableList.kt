package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ReorderableList(
    items: MutableList<Block>,
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
            .fillMaxSize()
            ,
        state = reorderableListState.lazyListState,
    ) {
        itemsIndexed(items) { index, item ->
            var modifier = Modifier.height(85.dp)
            when (item){
                is BlockFor -> modifier = Modifier
            }
            Card(
                modifier = modifier
                    .composed {
                        val offsetOrNull =
                            reorderableListState.elementDisplacement.takeIf {
                                index == reorderableListState.currentIndexOfDraggedItem
                            }

                        Modifier
                            .graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                    }
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    //.height(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .border(1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painterResource(id = R.drawable.paw),
                        contentDescription = "paw",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(50.dp)
                            .padding(5.dp)
                    )
                    when(item){
                        is BlockInit -> BlockInit(item)
                        is BlockDeclaration -> BlockDeclaration(item)
                        is BlockIf -> BlockIf(item)
                        is BlockEnd -> BlockEnd(item)
                        is BlockOutput -> BlockOutput(item)
                        is BlockWhile -> BlockWhile(item)
                        is BlockArrayDeclaration -> BlockArrayDeclaration(item)
                        is BlockFor -> BlockFor(item)
                        is BlockElse -> BlockElse(item)
                        is BlockArrayOfArrayDeclaration -> BlockArrayOfArrayDeclaration(item)
                    }
                }
            }
        }
    }
}