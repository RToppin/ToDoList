package edu.toppin.todolist

import ItemViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the database and DAO
        val itemDao = ItemDatabase.getDatabase(applicationContext).itemDao()

        // Initialize the ViewModel with the ViewModelFactory
        itemViewModel = ViewModelProvider(this, ItemViewModelFactory(itemDao))
            .get(ItemViewModel::class.java)

        setContent {

            var showPopup by remember { mutableStateOf(false) }

            ItemListScreen(itemViewModel = itemViewModel, onAddItem = { showPopup = true })

            // Show the popup when showPopup is true
            if (showPopup) {
                AddItemPopUp( viewModel = itemViewModel, onDismiss = { showPopup = false })
            }

        }
    }
}
