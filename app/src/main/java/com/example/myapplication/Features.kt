package com.example.myapplication


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
fun BlockInit(block: BlockInit) {
    var firstValue by remember { mutableStateOf("") }
    var secondValue by remember { mutableStateOf("") }
    firstValue = block.firstValue
    secondValue = block.secondValue
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.45f),
            value = firstValue,
            onValueChange = {
                firstValue = it
                block.firstValue = it
            },
            placeholder = { Text("Название") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
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
            value = secondValue,
            onValueChange = {
                secondValue = it
                block.secondValue = it
            },
            placeholder = { Text("Значение") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
    }
}


@Composable
fun BlockArrayDeclaration(block: BlockArrayDeclaration) {
    var firstValue by remember { mutableStateOf("") }
    var secondValue by remember { mutableStateOf("") }
    firstValue = block.name
    secondValue = block.size
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.45f),
            value = firstValue,
            onValueChange = {
                firstValue = it
                block.name = it
            },
            placeholder = { Text("Название") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            text = "size:"
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            value = secondValue,
            onValueChange = {
                secondValue = it
                block.size = it
            },
            placeholder = { Text("Размер") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun BlockArrayOfArrayDeclaration(block: BlockArrayOfArrayDeclaration) {
    var firstValue by remember { mutableStateOf("") }
    var secondValue by remember { mutableStateOf("") }
    var thirdValue by remember { mutableStateOf("") }
    firstValue = block.name
    secondValue = block.firstSize
    thirdValue = block.secondSize
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.40f),
            value = firstValue,
            onValueChange = {
                firstValue = it
                block.name = it
            },
            placeholder = { Text("Название") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            text = ":"
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .width(55.dp),
            value = secondValue,
            onValueChange = {
                secondValue = it
                block.firstSize = it
            },
            placeholder = { Text("n") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text("x")
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .width(55.dp),
            value = thirdValue,
            onValueChange = {
                thirdValue = it
                block.secondSize = it
            },
            placeholder = { Text("m") },
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun BlockDeclaration(block: BlockDeclaration) {
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
fun BlockIf(block: BlockIf) {
    var value by remember { mutableStateOf("") }
    value = block.value
    val focusManager = LocalFocusManager.current
    Row(verticalAlignment = Alignment.CenterVertically) {
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
        Text(text = ":")
        Text(text = "Begin ${block.mark}")
    }
}

@Composable
fun BlockWhile(block: BlockWhile) {
    var value by remember { mutableStateOf("") }
    value = block.value
    val focusManager = LocalFocusManager.current
    Row(verticalAlignment = Alignment.CenterVertically) {
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
        Text(text = ":")
        Text(text = "Begin ${block.mark}")
    }
}

@Composable
fun BlockFor(block: BlockFor) {
    var firstValue by remember { mutableStateOf("") }
    var secondValue by remember { mutableStateOf("") }
    var thirdValue by remember { mutableStateOf("") }
    firstValue = block.init
    secondValue = block.condition
    thirdValue = block.increment
    val focusManager = LocalFocusManager.current
    Column(Modifier.padding(end = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("For")
            OutlinedTextField(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxHeight()
                    .weight(0.5f),
                value = firstValue,
                onValueChange = {
                    firstValue = it
                    block.init = it
                },
                placeholder = { Text("init") },
                shape = RoundedCornerShape(5.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Text(";")
            OutlinedTextField(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxHeight()
                    .weight(1f),
                value = secondValue,
                onValueChange = {
                    secondValue = it
                    block.condition = it
                },
                placeholder = { Text("condition") },
                shape = RoundedCornerShape(5.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Text(";")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically

        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxHeight()
                    .width(70.dp),
                value = thirdValue,
                onValueChange = {
                    thirdValue = it
                    block.increment = it
                },
                placeholder = { Text("i=i+1") },
                shape = RoundedCornerShape(5.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Text(text = "Begin ${block.mark}")
        }
    }
}

@Composable
fun BlockEnd(block: BlockEnd) {
    Text(text = "End ${block.mark}")
}

@Composable
fun BlockOutput(block: BlockOutput) {
    var value by remember { mutableStateOf(block.value) }
    value = block.value
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
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
        Image(
            painterResource(id = R.drawable.print),
            contentDescription = "print",
            modifier = Modifier
                .size(30.dp)
        )
    }
}

@Composable
fun BlockElse(block: BlockElse) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Else")
        Text(text = ":")
        Text(text = "Begin ${block.mark}")
    }
}

fun output(items: MutableList<Block>): String {
    var line = ""
    val outputList = mutableListOf<String>()
    val mapOfVariables = mutableMapOf<String, Any>()
    for (item in items) {
        when (item) {
            is BlockInit -> {
                line += "${item.firstValue}=${item.secondValue};"
            }

            is BlockDeclaration -> {
                val list = item.value.split(",")
                for (variable in list) {
                    mapOfVariables[variable] = "0"
                }
            }

            is BlockIf -> {
                line += "?(${item.value}){"
            }

            is BlockWhile -> {
                if (item.value != "")
                    line += "#(${item.value}){"
            }

            is BlockEnd -> {
                if (item.linkedBlock is BlockFor)
                    line += "${item.linkedBlock.increment};"
                line += "}"
            }

            is BlockOutput -> {
                line += "p(${item.value});"
            }

            is BlockArrayDeclaration -> {
                if (item.name.isNotEmpty() && item.size.isNotEmpty()) {
                    val list = mutableListOf<String>()
                    for (i in 0 until item.size.toInt()) {
                        list.add((0..50).random().toString())
                    }
                    mapOfVariables[item.name] = list
                }
            }

            is BlockArrayOfArrayDeclaration -> {
                if (item.name.isNotEmpty() && item.firstSize.isNotEmpty() && item.secondSize.isNotEmpty()) {
                    val list: MutableList<MutableList<String>> = mutableListOf()
                    for (i in 0 until item.firstSize.toInt()) {
                        list.add(mutableListOf())
                        for (j in 0 until item.secondSize.toInt())
                            list[i].add((0..9).random().toString())
                    }
                    mapOfVariables[item.name] = list
                }
            }

            is BlockFor -> {
                if (item.condition != "")
                    line += "${item.init};#(${item.condition}){"
            }

            is BlockElse -> {
                line += "!{"
            }
        }
    }
    Log.d("MyLog", "line = $line")
    var out = ""
    try {
        val polis = stringToPolis(line)
        Log.d("polis", polis.toString())
        translation(polis, out = outputList, variables = mapOfVariables)
        out += "Программа выполнена успешно\n\n"
        for (i in outputList)
            out += if (i == """\n""") "\n"
            else
                "$i "
    } catch (e: Exception) {
        throw e
        out = "error"
    }

    Log.d("outsss", out)
    return out
}