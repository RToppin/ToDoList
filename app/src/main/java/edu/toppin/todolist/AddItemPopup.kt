package edu.toppin.todolist

import ItemViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AddItemPopUp(viewModel: ItemViewModel, onDismiss: () -> Unit) {
    var itemName by remember { mutableStateOf("") } // State variable for Name input
    var description by remember { mutableStateOf("") } // State variable for Description input
    var daysToComplete by remember { mutableStateOf("") } // State variable for Days input


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add Item") },
        text = {
            Column {
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Task Name") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Task Description") }
                )
                TextField(
                    value = daysToComplete,
                    onValueChange = { newText ->
                        // Only update if the input is numeric
                        if (newText.isEmpty() || newText.all { it.isDigit() }) {
                            daysToComplete = newText
                        }
                    },
                    label = { Text("Time to Complete") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {

                    viewModel.insert(
                        Item(
                            name = itemName,
                            description = description,
                            isChecked = false,
                            daysToComplete = daysToComplete.toInt()
                        )
                    )
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
