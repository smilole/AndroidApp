package com.example.myapplication

sealed class Screen(val route:String){
    object MainScreen:Screen("main_screen")
    object OutputScreen:Screen("output_screen")
    object StartScreen:Screen("start_screen")
}