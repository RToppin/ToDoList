import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.toppin.todolist.Item
import edu.toppin.todolist.ItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: Flow<List<Item>> = itemDao.getAllItems()// Observe the list of items from the database as a Flow
    private val _item = MutableStateFlow<Item?>(null) // Holds the entire Item object
    val item: StateFlow<Item?> = _item  // Public read-only state for UI to observe

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

    fun getItem(item: Item) {
        viewModelScope.launch {
            _item.value = itemDao.getItemById(item.id)
        }
    }

    fun updateCheckedState(item: Item, checked: Boolean) {
        viewModelScope.launch {
            itemDao.updateCheckedState(item.id, checked)  // Persist the new checked state
        }
    }

}
