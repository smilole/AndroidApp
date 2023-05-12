package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.*

@Composable
fun Screen() {
    var list = listOf<Block>(BlockDeclaration(0),BlockDeclaration(1)).toMutableStateList()

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
                    .clickable { list.add(BlockDeclaration()) },
                contentAlignment = Alignment.Center

            ){
                Text(text = "Объявление")
            }
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .padding(5.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .clickable { list.add(BlockInit()) },
                contentAlignment = Alignment.Center
            ){
                Text(text = "Инициализация")
            }
        }
    }
}