package js.pekah.todoApp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import js.pekah.todoApp.dto.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: Todo)

    @Query("select * from todoTable")
    fun list(): LiveData<MutableList<Todo>>

    @Query("select * from todoTable where id = (:id)")
    fun selectOne(id: Long): Todo

    @Update
    fun update(dto: Todo)

    @Delete
    fun delete(dto: Todo)
}