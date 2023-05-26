package com.example.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun StartScreen(navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.start_cat),
            contentScale = ContentScale.Crop,
            contentDescription = "start_cat",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Image(
                painterResource(id = R.drawable.cat_block),
                contentScale = ContentScale.Crop,
                contentDescription = "cat_block",
                modifier = Modifier.width(180.dp)
            )
            OutlinedButton(
                onClick = { navController.navigate("main_screen") },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.pink_200)),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.width(150.dp),
            ) {
                Text(
                    "Start",
                    style = TextStyle
                        (
                        fontFamily = FontFamily(Font(R.font.consolas)),
                        fontSize = 20.sp
                    ),
                )
            }
            Spacer(Modifier.height(50.dp))
        }
    }
}