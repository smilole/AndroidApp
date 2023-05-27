package com.example.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.*
import com.example.myapplication.R
import io.paperdb.Paper

@Composable
fun MainScreen(navController: NavController) {

    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    //val list = mutableListOf<Block>().toMutableStateList()
    var markCount = 0
    var currentMark = "m0"
    Paper.init(context)

    Box {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = colorResource(id = R.color.pink_200))
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.catblock),
                    modifier = Modifier.clickable {
                        mainViewModel.list.clear()
                    },
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.consolas)), fontWeight = FontWeight.Bold
                    ),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.menu_icon),
                        contentDescription = "menuButton",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                Paper
                                    .book()
                                    .write(mainViewModel.programName, mainViewModel.list)
                                mainViewModel.fileIsChosen = false
                            })
                    Image(painter = painterResource(id = R.drawable.save_icon),
                        contentDescription = "saveButton",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                mainViewModel.saveDialogIsOpen = true
                            })
                    Image(painter = painterResource(id = R.drawable.run_button),
                        contentDescription = "runButton",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                Paper
                                    .book()
                                    .write(mainViewModel.programName, mainViewModel.list)
                                val out = output(mainViewModel.list)
                                if (out == "error") {
                                    Toast
                                        .makeText(context, "Что-то не так", Toast.LENGTH_LONG)
                                        .show()
                                } else navController.navigate("output_screen/$out")
                            })
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
                ReorderableList(items = mainViewModel.list,
                    onMove = { fromIndex, toIndex -> mainViewModel.list.move(fromIndex, toIndex) })

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
                        .clickable { mainViewModel.list.add(BlockDeclaration()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.obyavl),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { mainViewModel.list.add(BlockInit()) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.init),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockIf = BlockIf(currentMark)
                            mainViewModel.list.add(blockIf)
                            mainViewModel.list.add(BlockEnd(currentMark, blockIf))
                            markCount++
                            currentMark = "m$markCount"
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.uslov),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockWhile = BlockWhile(currentMark)
                            mainViewModel.list.add(blockWhile)
                            mainViewModel.list.add(BlockEnd(currentMark, blockWhile))
                            markCount++
                            currentMark = "m$markCount"
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.whil),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockFor = BlockFor(currentMark)
                            mainViewModel.list.add(blockFor)
                            mainViewModel.list.add(BlockEnd(currentMark, blockFor))
                            markCount++
                            currentMark = "m$markCount"
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.forcycle),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { mainViewModel.list.add(BlockOutput()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.vivod),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { mainViewModel.list.add(BlockArrayDeclaration()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.massiv),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable { mainViewModel.list.add(BlockArrayOfArrayDeclaration()) },
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)

                    )
                    Text(
                        text = stringResource(R.string.dvum_massiv),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }

                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .padding(5.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val blockElse = BlockElse(currentMark)
                            mainViewModel.list.add(blockElse)
                            mainViewModel.list.add(BlockEnd(currentMark, blockElse))
                            markCount++
                            currentMark = "m$markCount"
                        }, contentAlignment = Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.block_cats),
                        contentDescription = "walkingCat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                    Text(
                        text = stringResource(R.string.els),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.consolas)),
                            fontWeight = FontWeight.Bold
                        ),
                    )
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

        if (mainViewModel.saveDialogIsOpen) Box(Modifier.align(Alignment.Center)) {
            SaveDialog(programName = mainViewModel.programName,
                onProgramNameChanged = { mainViewModel.programName = it },
                onSaveButtonClicked = {
                    Paper.book().write(mainViewModel.programName, mainViewModel.list)
                    mainViewModel.saveDialogIsOpen = false
                })
        }
        if (!mainViewModel.fileIsChosen) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.pink_500))
            ) {
                Column(
                    Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(4.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.create_icon),
                        contentDescription = "createIcon"
                    )
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.pink_500)),
                        onClick = {
                            mainViewModel.fileIsChosen = true
                            mainViewModel.saveDialogIsOpen = true
                        },
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text("Создать файл")
                    }
                    Text(
                        "или",
                        style = TextStyle
                            (
                            fontFamily = FontFamily(Font(R.font.consolas))
                        ),
                    )
                    Text(
                        "Открыть имеющийся:\n",
                        style = TextStyle
                            (
                            fontFamily = FontFamily(Font(R.font.consolas)),
                        ),
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        val programs = Paper.book().allKeys
                        if (programs.isEmpty()) {
                            Text("Сохраненных программ еще нет", color = Color.Gray)
                        } else {
                            for (i in programs) Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.width(200.dp)
                            ) {
                                Text(
                                    i,
                                    modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.pink_200),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .clickable {
                                            mainViewModel.programName = i
                                            mainViewModel.list.clear()
                                            Paper
                                                .book()
                                                .read(i, listOf<Block>())
                                                ?.let { mainViewModel.list.addAll(it) }
                                            mainViewModel.fileIsChosen = true
                                        }
                                        .padding(10.dp),
                                    style = TextStyle
                                        (
                                        fontFamily = FontFamily(Font(R.font.consolas)),
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                                Image(Icons.Default.Delete,
                                    contentDescription = "delete",
                                    modifier = Modifier.clickable {
                                        Paper.book().delete(i)
                                        mainViewModel.saveDialogIsOpen =
                                            !mainViewModel.saveDialogIsOpen
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SaveDialog(
    modifier: Modifier = Modifier,
    programName: String,
    onProgramNameChanged: (String) -> Unit,
    onSaveButtonClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(4.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Сохранение",
            style = TextStyle
                (
                fontFamily = FontFamily(Font(R.font.consolas)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
        )
        OutlinedTextField(
            value = programName,
            onValueChange = onProgramNameChanged,
            placeholder = { Text("Название программы") },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.pink_200)
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Button(
            onClick = onSaveButtonClicked,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink_200))
        ) {
            Text("ОК")
        }
    }
}