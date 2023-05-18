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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Math.random
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
    Log.d("moves", "$from $to")

    val element = this.removeAt(from) ?: return
    this.add(to, element)
}


@Composable
fun BlockInit(block:BlockInit){
    var firstValue by remember { mutableStateOf("") }
    var secondValue by remember { mutableStateOf("") }
    firstValue =  block.firstValue
    secondValue = block.secondValue
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.45f),
            value=firstValue,
            onValueChange = {
                firstValue = it
                block.firstValue = it
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
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            value=secondValue,
            onValueChange = {
                secondValue = it
                block.secondValue = it
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


@Composable
fun BlockArrayDeclaration(block:BlockArrayDeclaration){
    var firstValue by remember { mutableStateOf("") }
    var secondValue by remember { mutableStateOf("") }
    firstValue =  block.name
    secondValue = block.size
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.45f),
            value=firstValue,
            onValueChange = {
                firstValue = it
                block.name= it
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
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            value=secondValue,
            onValueChange = {
                secondValue = it
                block.size = it
            },
            placeholder = {Text("Размер")},
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone =  {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun BlockDeclaration(block:BlockDeclaration) {
    var value by remember { mutableStateOf("") }
    value = block.value
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        value = value,
        onValueChange = {
            value = it
            block.value = it
                        },
        placeholder = { Text("variable1,variable2") },
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })
    )
}

@Composable
fun BlockIf(block:BlockIf){
    var value by remember { mutableStateOf("") }
    value = block.value
    val focusManager = LocalFocusManager.current
    Row(verticalAlignment = Alignment.CenterVertically){
        Text("If")
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.8f),
            value = value,
            onValueChange = {
                value = it
                block.value = it
                            },
            placeholder = { Text("Условие") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Text(text=":")
        Text(text="Begin ${block.mark}")
    }
}

@Composable
fun BlockWhile(block:BlockWhile){
    var value by remember { mutableStateOf("") }
    value = block.value
    val focusManager = LocalFocusManager.current
    Row(verticalAlignment = Alignment.CenterVertically){
        Text("While")
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.8f),
            value = value,
            onValueChange = {
                value = it
                block.value = it
            },
            placeholder = { Text("Условие") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Text(text=":")
        Text(text="Begin ${block.mark}")
    }
}

@Composable
fun BlockEnd(block:BlockEnd){
    Text(text = "End ${block.mark}")
}

@Composable
fun BlockOutput(block:BlockOutput){
    var value by remember { mutableStateOf(block.value) }
    value = block.value
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        value = value,
        onValueChange = {
            value = it
            block.value = it
                        },
        placeholder = { Text("Вывод") },
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })
    )
}

fun output(items: MutableList<Block>):String{
    var line = ""
    val outputList = mutableListOf<String>()
    val mapOfVariables = mutableMapOf<String,Any>()
    for(item in items){
        when(item){
            is BlockInit -> {
                line+="${item.firstValue}=${item.secondValue};"
            }
            is BlockDeclaration -> {
                val list = item.value.split(",")
                for (variable in list){
                    mapOfVariables[variable] = 0
                }
            }
            is BlockIf -> {
                line+="?(${item.value}){"
            }
            is BlockWhile -> {
                line+="#(${item.value}){"
            }
            is BlockEnd -> {
                line+="}"
            }
            is BlockOutput -> {
                line+="p(${item.value});"
            }
            is BlockArrayDeclaration -> {
                val list = mutableListOf<String>()
                for(i in 0 until item.size.toInt()){
                    list.add((0..50).random().toString())
                }
                mapOfVariables[item.name] = list
            }
        }
    }
    Log.d("MyLog","line = $line")

    val polis = stringToPolis(line)
    translation(polis,out = outputList, variables = mapOfVariables)

    var out = "Программа выполнена успешно\n\n"
    for(i in outputList)
        out+="$i "
    return out
}