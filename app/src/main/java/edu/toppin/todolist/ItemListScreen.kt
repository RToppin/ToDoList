package edu.toppin.todolist

import ItemViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

@Composable
fun ItemListScreen(itemViewModel: ItemViewModel, onAddItem: () -> Unit) {
    val itemList by itemViewModel.allItems.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddItem() }) {
                Icon(Icons.Filled.Add, "Add Task")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(80, 80, 80, 255))
        ) {
            items(items = itemList, key = { it.id }) { item ->
                ItemRow(item, itemViewModel)
            }
        }
    }
}

@Composable
fun ItemRow(item: Item, itemViewModel: ItemViewModel) {
    val isChecked by remember { mutableStateOf(item.isChecked) }
    var percentageRemaining by remember { mutableStateOf(item.percentageRemaining) }

    LaunchedEffect(item) {
        while (true) {
            val currentTimeMillis = System.currentTimeMillis()
            val duration = item.daysToComplete * 1000L
            val endTimeMillis = item.timestamp + duration

            percentageRemaining = max(0f, min(1f, (endTimeMillis - currentTimeMillis).toFloat() / duration))
            delay(1000)
        }
    }

    val color = Color(
        red = (255 * (1 - percentageRemaining)).toInt(),
        green = (255 * percentageRemaining).toInt(),
        blue = 0
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(50.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    val updatedItem = item.copy(isChecked = it)
                    itemViewModel.update(updatedItem)
                }
            )
            Text(
                text = item.name,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
            IconButton(onClick = { itemViewModel.delete(item) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete task")
            }
        }
    }
}
