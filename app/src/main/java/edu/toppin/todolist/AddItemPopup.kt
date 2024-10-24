package edu.toppin.todolist

import ItemViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddItemPopUp( viewModel: ItemViewModel, onDismiss: () -> Unit) {
    var itemName by remember { mutableStateOf("") } // State variable for Name input
    var description by remember { mutableStateOf("") } // State variable for Description input

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add Item") },
        text = {
            Column{
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it},
                    label = { Text("Task Name") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Task Description") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {

                    viewModel.insert(Item(name = itemName, description = description, isChecked = false))
                    onDismiss() // Close the dialog
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
