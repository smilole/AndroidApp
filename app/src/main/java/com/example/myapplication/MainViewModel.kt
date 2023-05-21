package com.example.myapplication

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

val list = mutableListOf<Block>().toMutableStateList()


}