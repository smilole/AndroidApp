package com.example.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.Block
import com.example.myapplication.BlockInit
import com.example.myapplication.ReorderableList
import com.example.myapplication.move
import com.example.myapplication.stringToPolis
import com.example.myapplication.translation

@Composable
fun Screen() {
    var list = listOf<Block>(Block(), Block(), BlockInit("1"),BlockInit("2")).toMutableStateList()

    val polis = stringToPolis("i=0;j=0;tm=0;#(i<size-1){#(j<size-i-1){?(arr[j+1]>arr[j]){tm=arr[j+1];arr[j+1]=arr[j];arr[j]=tm;}j=j+1;}j=0;i=i+1;};i=size-1;#(i>0|i~0){p(arr[i]);i=i-1}")
    val out = mutableListOf<String>()
    val variables = mutableMapOf(
        "i" to 0,
        "j" to 0,
        "tm" to 0,
        "size" to 5,
        "arr" to mutableListOf("0", "3", "2", "10", "1"),
    )
    translation(polis = polis,out = out, variables = variables)



    val context = LocalContext.current
    Column {
        Text("$variables$out")

        ReorderableList(
            items = list,
            onMove = { fromIndex, toIndex ->
                list.move(fromIndex, toIndex) }
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
                .background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray)
                    .clickable { list += BlockInit("+") },
                contentAlignment = Alignment.Center

            ){
                Text(text = "Инициализация")
            }
        }
    }
}