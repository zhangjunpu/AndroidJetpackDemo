package com.junpu.wordbook.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * 单词本数据库
 * @author junpu
 * @date 2020/7/23
 */

private const val DB_NAME = "wordbook"
private const val DB_VERSION = 1
private const val TABLE_NAME = "words"

@Database(entities = [Word::class], version = DB_VERSION, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract val wordDao: WordDao

    companion object {

        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(WordDatabase::class) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): WordDatabase =
            Room.databaseBuilder(context.applicationContext, WordDatabase::class.java, DB_NAME)
                .build()
    }
}

@Dao
interface WordDao {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM $TABLE_NAME WHERE english_name LIKE :condition ORDER BY ID DESC")
    fun fuzzySearchWords(condition: String?): LiveData<List<Word>>

    @Insert
    fun insert(vararg word: Word)

    @Update
    fun update(vararg word: Word)

    @Delete
    fun delete(vararg word: Word)

    @Query("DELETE FROM $TABLE_NAME")
    fun clear()
}

@Entity(tableName = TABLE_NAME)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "english_name")
    var englishName: String? = null,
    @ColumnInfo(name = "chinese_name")
    var chineseName: String? = null,
    var grasp: Int = 0
)