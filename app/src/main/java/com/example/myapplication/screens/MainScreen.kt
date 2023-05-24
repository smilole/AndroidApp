package com.example.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.*
import com.example.myapplication.R

@Composable
fun MainScreen(navController: NavController) {


    val context = LocalContext.current
    val listViewModel: MainViewModel = viewModel()

    //val list = mutableListOf<Block>().toMutableStateList()
    var markCount = 0
    var currentMark = "m0"

    Box() {

        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = colorResource(id = R.color.pink_200))
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "CatBlock",
                    modifier = Modifier.clickable {
                        listViewModel.list.clear()
                    })
                Row(Modifier
                    .clickable {
                        val out = output(listViewModel.list)
                        if (out == "error") {
                            Toast.makeText(context, "Что-то не так", Toast.LENGTH_LONG).show()
                        } else
                            navController.navigate("output_screen/$out")
                    }, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Run")
                    Image(
                        painter = painterResource(id = R.drawable.run_button),
                        contentDescription = "runButton",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.pink_500))
            ) {
                Image(
                    painterResource(id = R.drawable.blesk),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.BottomEnd),
                    contentDescription = "blesk",
                    contentScale = ContentScale.Crop
                    //modifier = Modifier.
                )
                ReorderableList(
                    items = listViewModel.list,
                    onMove = { fromIndex, toIndex -> listViewModel.list.move(fromIndex, toIndex) }
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .background(color = colorResource(id = R.color.pink_200)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { listViewModel.list.add(BlockDeclaration()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "Объявление")
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { listViewModel.list.add(BlockInit()) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "Инициализация")
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockIf = BlockIf(currentMark)
                            listViewModel.list.add(blockIf)
                            listViewModel.list.add(BlockEnd(currentMark, blockIf))
                            markCount++
                            currentMark = "m$markCount"
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "Условие")
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockWhile = BlockWhile(currentMark)
                            listViewModel.list.add(blockWhile)
                            listViewModel.list.add(BlockEnd(currentMark, blockWhile))
                            markCount++
                            currentMark = "m$markCount"
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "While")
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockFor = BlockFor(currentMark)
                            listViewModel.list.add(blockFor)
                            listViewModel.list.add(BlockEnd(currentMark, blockFor))
                            markCount++
                            currentMark = "m$markCount"
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "For")
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { listViewModel.list.add(BlockOutput()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "Вывод")
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { listViewModel.list.add(BlockArrayDeclaration()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)

                    )
                    Text(text = "Массив")
                }
            }
        }

        Image(
            painterResource(id = R.drawable.walking_cat),
            contentDescription = "walkingCat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(80.dp)
                .padding(end = 20.dp, bottom = 120.dp)
        )

    }

}