package edu.toppin.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM item_table ORDER BY timestamp ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM item_table WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): Item

    @Query("UPDATE item_table SET isChecked = :isChecked WHERE id = :itemId")
    suspend fun updateCheckedState(itemId: Int, isChecked: Boolean)

}