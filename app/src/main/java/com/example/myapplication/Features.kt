package com.example.myapplication

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.sql.Blob
import kotlin.math.abs
import kotlin.math.roundToInt

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
fun BlockInit(block:BlockInit){
    var value by remember { mutableStateOf(String()) }
    block.firstValue = value
    Row(){
        OutlinedTextField(
            modifier = Modifier
                .width(100.dp)
                .padding(5.dp)
                .background(Color.Red)
            //.fillMaxSize()
            ,
            value=value,
            onValueChange = {value = it },
            placeholder = {Text("Введите переменные через запятую")},
            shape = RoundedCornerShape(5.dp),
        )
    }

}

/*
@Composable
fun checkId(item:ReorderItem,modifier:Modifier){
    when(item.id){
        0 -> {
            var value by remember { mutableStateOf(String()) }
            Row(){
            OutlinedTextField(
                modifier = Modifier
                    .width(100.dp)
                    .padding(5.dp)
                    .background(Color.Red)
                    //.fillMaxSize()
                        ,
                value=value,
                onValueChange = {newText->
                    if(newText.last() !in listOf('0','1','2','3','4','5','6','7','8','9')) value = newText
                },
                placeholder = {Text("Введите переменные через запятую")},
                shape = RoundedCornerShape(5.dp),
            )
            }
        }
        else -> {
            Text("This is block number ${item.id}", modifier = modifier)
        }
    }
}

 */