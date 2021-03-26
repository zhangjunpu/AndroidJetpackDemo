package com.junpu.wordbook.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.junpu.wordbook.db.Word
import com.junpu.wordbook.repository.WordRepository
import kotlin.concurrent.thread

/**
 *
 * @author junpu
 * @date 2020/7/23
 */
class WordViewModel(app: Application) : AndroidViewModel(app) {

    private val repository by lazy { WordRepository(getApplication()) }

    /**
     * 当前单词列表
     */
    var curWords: LiveData<List<Word>>? = null

    /**
     * 所有单词列表
     */
    val allWords by lazy { repository.allWords }

    /**
     * 模糊查询单词列表
     */
    fun fuzzySearchWords(condition: String?) = repository.fuzzySearchWords(condition)

    /**
     * 添加单词
     */
    fun addWord(vararg word: Word) {
        thread { repository.addWord(*word) }
    }

    /**
     * 修改单词
     */
    fun updateWord(vararg word: Word) {
        thread { repository.update(*word) }
    }

    /**
     * 删除单词
     */
    fun deleteWord(vararg word: Word) {
        thread { repository.deleteWord(*word) }
    }

    /**
     * 清理单词列表
     */
    fun clear() {
        thread { repository.clear() }
    }
}