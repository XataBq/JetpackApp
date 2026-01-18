package com.example.testapp.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class HomeViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val email = savedStateHandle.get<String>("email").orEmpty()
}