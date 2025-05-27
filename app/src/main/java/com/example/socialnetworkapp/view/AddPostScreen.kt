package com.example.socialnetworkapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddPostScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
        Text("Tiêu đề", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Đây là nội dung bên trong AddPostScreen.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}