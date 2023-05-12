package com.example.myapplication

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/*fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}
@Composable
fun drawBlock(
    block: Block,
    items: MutableList<Block>,
    index: Int
) {
    val localContext = LocalContext.current
    var offsetX = remember{
        mutableStateOf(0f)
    }
    var offsetY = remember{
        mutableStateOf(0f)
    }
    var isDragged = remember {
        mutableStateOf(false)
    }
    var premennaya = offsetY.value


    Card(
        modifier = Modifier
            .offset {
                IntOffset(x=offsetX.value.roundToInt(),y=offsetY.value.roundToInt())
            }
            .pointerInput(Unit){
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        isDragged.value = true
                        premennaya = offsetY.value
                                  },
                    onDragEnd = {
                        isDragged.value = false
                        premennaya -= offsetY.value
                        var t = (convertPixelsToDp(abs(premennaya),localContext)/80).toInt()
                        var lol = if (premennaya>0) 0 else 1
                        Log.d("MyLog","t=$t lol=$lol")
                        if(t>0){
                            if(lol==1){
                                if(items.size>index+t) {
                                    items.add(index + t, items[index])
                                    items.removeAt(index)
                                }
                                else{
                                    items.add(items[index])
                                    items.removeAt(index)
                                }
                            }
                            else{
                                if(index-t>=0) {
                                    items.add(index - t, items[index])
                                    items.removeAt(index)
                                }
                            }
                        }
                        else{
                            var kek = items[index]
                            items.removeAt(index)
                            items.add(index, kek)
                        }

                    }
                ) { change, dragAmount ->
                    change.consume()
                    //offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                    //Log.d("MyLog","Y:${offsetY.value} dragAmount: ${dragAmount.y}")
                }

            }
            .fillMaxWidth()
            .height(block.height)
            .background(Color.LightGray.copy(alpha = 0.2f))
    ){
        when(block.type){
            "Block" -> {
                Text(text = "Block")
            }
            "Other" -> {
                Text(text = "Other")
            }
        }
    }
}
*/

/*
    LazyListItemInfo.index is the item's absolute index in the list

    Based on the item's "relative position" with the "currently top" visible item,
    this returns LazyListItemInfo corresponding to it
*/
fun LazyListState.getVisibleItemInfoFor(absoluteIndex: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(absoluteIndex - this.layoutInfo.visibleItemsInfo.first().index)
}

/*
  Bottom offset of the element in Vertical list
*/
val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

/*
   Moving element in the list
*/
fun <T> MutableList<T>.move(from: Int, to: Int) {
    if (from == to)
        return

    val element = this.removeAt(from) ?: return
    this.add(to, element)
}
@Composable
fun rememberReorderableListState(
    lazyListState: LazyListState = rememberLazyListState(),
    onMove: (Int, Int) -> Unit,
): ReorderableListState {
    return remember { ReorderableListState(lazyListState = lazyListState, onMove = onMove) }
}

class ReorderableListState(
    val lazyListState: LazyListState,
    private val onMove: (Int, Int) -> Unit
) {
    var draggedDistance by mutableStateOf(0f)

    // used to obtain initial offsets on drag start
    var initiallyDraggedElement by mutableStateOf<LazyListItemInfo?>(null)

    var currentIndexOfDraggedItem by mutableStateOf<Int?>(null)

    val initialOffsets: Pair<Int, Int>?
        get() = initiallyDraggedElement?.let { Pair(it.offset, it.offsetEnd) }

    val elementDisplacement: Float?
        get() = currentIndexOfDraggedItem
            ?.let { lazyListState.getVisibleItemInfoFor(absoluteIndex = it) }
            ?.let { item -> (initiallyDraggedElement?.offset ?: 0f).toFloat() + draggedDistance - item.offset }

    val currentElement: LazyListItemInfo?
        get() = currentIndexOfDraggedItem?.let {
            lazyListState.getVisibleItemInfoFor(absoluteIndex = it)
        }

    var overscrollJob by mutableStateOf<Job?>(null)

    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                currentIndexOfDraggedItem = it.index
                initiallyDraggedElement = it
            }
    }

    fun onDragInterrupted() {
        draggedDistance = 0f
        currentIndexOfDraggedItem = null
        initiallyDraggedElement = null
        overscrollJob?.cancel()
    }

    fun onDrag(offset: Offset) {
        draggedDistance += offset.y

        initialOffsets?.let { (topOffset, bottomOffset) ->
            val startOffset = topOffset + draggedDistance
            val endOffset = bottomOffset + draggedDistance

            currentElement?.let { hovered ->
                lazyListState.layoutInfo.visibleItemsInfo
                    .filterNot { item -> item.offsetEnd < startOffset || item.offset > endOffset || hovered.index == item.index }
                    .firstOrNull { item ->
                        val delta = startOffset - hovered.offset
                        when {
                            delta > 0 -> (endOffset > item.offsetEnd)
                            else -> (startOffset < item.offset)
                        }
                    }
                    ?.also { item ->
                        currentIndexOfDraggedItem?.let { current -> onMove.invoke(current, item.index) }
                        currentIndexOfDraggedItem = item.index
                    }
            }
        }
    }

    fun checkForOverScroll(): Float {
        return initiallyDraggedElement?.let {
            val startOffset = it.offset + draggedDistance
            val endOffset = it.offsetEnd + draggedDistance

            return@let when {
                draggedDistance > 0 -> (endOffset - lazyListState.layoutInfo.viewportEndOffset).takeIf { diff -> diff > 0 }
                draggedDistance < 0 -> (startOffset - lazyListState.layoutInfo.viewportStartOffset).takeIf { diff -> diff < 0 }
                else -> null
            }
        } ?: 0f
    }
}

@Composable
fun ReorderableList(
    items: List<ReorderItem>,
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

                        reorderableListState.checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let { overscrollJob = scope.launch { reorderableListState.lazyListState.scrollBy(it) } }
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
    ) {
        itemsIndexed(items) { index, item ->
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
                    }
                    .padding(5.dp)
                    .height(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
            ) {
                Row(){
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp)
                            .background(Color.DarkGray)
                    ){}
                    checkId(item)
                }
            }
        }
    }
}
@Composable
fun Screen() {
    var list = listOf<ReorderItem>(ReorderItem(0),ReorderItem(1),ReorderItem(2),ReorderItem(3)).toMutableStateList()

    Column {
        ReorderableList(
            items = list,
            onMove = { fromIndex, toIndex -> list.move(fromIndex, toIndex) }
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
                .background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .padding(5.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .clickable { list += ReorderItem(0) },
                contentAlignment = Alignment.Center

            ){
                Text(text = "Объявление")
            }
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .padding(5.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .clickable { list += ReorderItem(1) },
                contentAlignment = Alignment.Center

            ){
                Text(text = "Инициализация")
            }
        }
    }
}

@Composable
fun checkId(item:ReorderItem){
    when(item.id){
        0 -> {
            var value by remember { mutableStateOf(item.line) }
            val focusManager = LocalFocusManager.current
                TextField(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize(),
                    value=value,
                    onValueChange = {newText->
                        value = newText
                        item.line = newText
                    },
                    placeholder = {Text("Введите переменные через запятую")},
                    shape = RoundedCornerShape(5.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone =  {
                        focusManager.clearFocus()

                    })
                )
        }
        1 -> {
            var varName by remember { mutableStateOf(item.varName) }
            var varValue by remember { mutableStateOf(item.varValue) }
            val focusManager = LocalFocusManager.current
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                TextField(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(0.45f),
                    value=varName,
                    onValueChange = {newText->
                        varName = newText
                        item.varName = newText
                    },
                    placeholder = {Text("Название")},
                    shape = RoundedCornerShape(5.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone =  {
                        focusManager.clearFocus()
                    })
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    text = "="
                )
                TextField(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    value=varValue,
                    onValueChange = {newText->
                        varValue = newText
                        item.varValue = newText
                    },
                    placeholder = {Text("Значение")},
                    shape = RoundedCornerShape(5.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone =  {
                        focusManager.clearFocus()
                    })
                )
            }
        }
        else -> {
            Text("This is block number ${item.id}")
        }
    }
}