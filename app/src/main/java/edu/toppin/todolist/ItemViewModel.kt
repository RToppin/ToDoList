import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.toppin.todolist.Item
import edu.toppin.todolist.ItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    // Observe the list of items from the database as a Flow
    val allItems: Flow<List<Item>> = itemDao.getAllItems()

    // Insert a new item
    fun insert(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    // Update an existing item
    fun update(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    // Delete an item
    fun delete(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
}
