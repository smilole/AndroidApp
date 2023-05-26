package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R

@Composable
fun OutputScreen(navController: NavController, output: String) {

    Box {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = colorResource(id = R.color.pink_200))
                    .padding(start = 20.dp),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "backArrow",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.navigate("main_screen")
                        }
                )
                Text(
                    "Output",
                    style = TextStyle
                        (
                        fontFamily = FontFamily(Font(R.font.consolas)),
                        fontSize = 20.sp
                    ),
                )
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
                Text(
                    output,
                    Modifier.padding(20.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.consolas)),
                        fontSize = 20.sp
                    ),
                )


            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = colorResource(id = R.color.pink_200))
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "CatBlock",
                    style = TextStyle
                        (
                        fontFamily = FontFamily(Font(R.font.consolas)),
                        fontSize = 20.sp
                    ),
                )
            }
        }
        Image(
            painterResource(id = R.drawable.cat_output),
            contentDescription = "cat_output",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)
                .padding(end = 20.dp, bottom = 40.dp)
        )
    }


}

@Composable
@Preview
fun Preview() {
    OutputScreen(navController = rememberNavController(), output = "1 2 10 7 helloworld")
}