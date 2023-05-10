package com.example.myapplication

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}
@Composable
fun drawBlock(
    block: Block,
    items: MutableList<Block>,
    index: Int
) {
    val localContext = LocalContext.current
    var offsetX = remember{
        mutableStateOf(0f)
    }
    var offsetY = remember{
        mutableStateOf(0f)
    }
    var isDragged = remember {
        mutableStateOf(false)
    }
    var premennaya = offsetY.value


    Card(
        modifier = Modifier
            .offset {
                IntOffset(x=offsetX.value.roundToInt(),y=offsetY.value.roundToInt())
            }
            .pointerInput(Unit){
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        isDragged.value = true
                        premennaya = offsetY.value
                                  },
                    onDragEnd = {
                        isDragged.value = false
                        premennaya -= offsetY.value
                        var t = (convertPixelsToDp(abs(premennaya),localContext)/80).toInt()
                        var lol = if (premennaya>0) 0 else 1
                        Log.d("MyLog","t=$t lol=$lol")
                        if(t>0){
                            if(lol==1){
                                if(items.size>index+t) {
                                    items.add(index + t, items[index])
                                    items.removeAt(index)
                                }
                                else{
                                    items.add(items[index])
                                    items.removeAt(index)
                                }
                            }
                            else{
                                if(index-t>=0) {
                                    items.add(index - t, items[index])
                                    items.removeAt(index)
                                }
                            }
                        }
                        else{
                            var kek = items[index]
                            items.removeAt(index)
                            items.add(index, kek)
                        }

                    }
                ) { change, dragAmount ->
                    change.consume()
                    //offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                    //Log.d("MyLog","Y:${offsetY.value} dragAmount: ${dragAmount.y}")
                }

            }
            .fillMaxWidth()
            .height(block.height)
            .background(Color.LightGray.copy(alpha = 0.2f))
    ){
        when(block.type){
            "Block" -> {
                Text(text = "Block")
            }
            "Other" -> {
                Text(text = "Other")
            }
        }
    }
}

