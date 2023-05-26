package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val list = mutableListOf<Block>().toMutableStateList()
    var saveDialogIsOpen by mutableStateOf(false)
    var programName by mutableStateOf("")
    var fileIsChosen by mutableStateOf(false)

}