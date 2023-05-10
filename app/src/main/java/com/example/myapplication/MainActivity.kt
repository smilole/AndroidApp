package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round
import kotlin.math.roundToInt


class Block1(
    var innerText: String = "Block",
    var blockColor: Color = Color.Red,
    var blockValue: Int = 0
){
    @Composable
    fun drawBlock(){

        var offsetX = remember{
            mutableStateOf(0f)
        }
        var offsetY = remember{
            mutableStateOf(0f)
        }
        var isDragged = remember {
            mutableStateOf(false)
        }

        Card(modifier = Modifier
            .offset {
                IntOffset(x=offsetX.value.roundToInt(),y=offsetY.value.roundToInt())
            }
            .pointerInput(Unit){
                detectDragGesturesAfterLongPress(
                    onDragStart = {isDragged.value = true},
                    onDragEnd = {isDragged.value = false}
                ) { change, dragAmount ->
                    change.consume()
                    //offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }

            }
            .fillMaxWidth()
            .height(50.dp)
            .background(color = if(!isDragged.value) blockColor else Color.Cyan),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ){
           Box(modifier = Modifier
               .fillMaxSize()
           ){
                Text(text="block")
           }
        }
    }
}

class Block


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            listOfItems()
        }
    }
}

@Composable
fun iternalContent(){

    var blocks = remember{
        mutableStateListOf(Block1())
    }

    @Composable
    fun addButton(tText: String){
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(4.dp))
                .background(Color.Gray)
                .clickable { blocks.add(Block1()) },
            contentAlignment = Alignment.Center
        ){
            Text(text = tText, modifier = Modifier.padding(30.dp))
        }
    }

    Column(){
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center

        ) {
            for (item in blocks){
                item.drawBlock()
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(Color.DarkGray)
            ,
        ){
            addButton( "Create Block")
            addButton( "Create Block")
            addButton( "Create Block")
            addButton( "Create Block")
        }
    }
}

@Composable
fun listOfItems(){
    var items by remember {
        mutableStateOf(
            listOf<Block>()
        )
    }
    Column() {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items, { it }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .padding(24.dp),
                    text = "I love $it"
                )
            }
            item() {

            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(Color.DarkGray),
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(Color.Gray)
                    .clickable {
                               items+=Block()
                               },
                contentAlignment = Alignment.Center
            ){
                Text(text = "Hello", modifier = Modifier.padding(30.dp))
            }
        }
    }
}