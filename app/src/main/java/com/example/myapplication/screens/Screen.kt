package com.example.myapplication.screens

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.Block
import com.example.myapplication.BlockInit
import com.example.myapplication.ReorderableList
import com.example.myapplication.move

@Composable
fun Screen() {
    var list = listOf<Block>(Block(), Block(), BlockInit()).toMutableStateList()

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
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray)
                    .clickable { list += BlockInit() },
                contentAlignment = Alignment.Center

            ){
                Text(text = "Инициализация")
            }
        }
    }
}