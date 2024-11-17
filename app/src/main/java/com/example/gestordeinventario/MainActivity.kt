package com.example.gestordeinventario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.gestordeinventario.core.navigation.NavigationWrapper
import com.example.gestordeinventario.ui.students_list.Student
import com.example.gestordeinventario.ui.students_list.StudentsListScreen
import com.example.gestordeinventario.ui.students_list.StudentsListViewModel
import com.example.gestordeinventario.ui.theme.GestorDeInventarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorDeInventarioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationWrapper()
                }
            }
        }
    }
}
