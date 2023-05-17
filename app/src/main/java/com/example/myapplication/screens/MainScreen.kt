package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.*

@Composable
fun MainScreen(navController:NavController) {
    var list = listOf<Block>().toMutableStateList()
    var markCount = 0
    var currentMark = "m$markCount"

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.075f)
                .background(Color.DarkGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Hello")
            Text(text = "World",
                modifier = Modifier.clickable {
                    navController.navigate("output_screen/${output(list)}")
                    }
                )
        }
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
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .padding(5.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .clickable {
                        list.add(BlockIf(currentMark))
                        list.add(BlockEnd(currentMark))
                        markCount++
                        currentMark = "m$markCount"
                               },
                contentAlignment = Alignment.Center
            ){
                Text(text = "Условие")
            }
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .padding(5.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .clickable { list.add(BlockOutput()) },
                contentAlignment = Alignment.Center

            ){
                Text(text = "Вывод")
            }
        }
    }
}