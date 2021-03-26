package com.junpu.wordbook.repository

import android.content.Context
import com.junpu.wordbook.db.Word
import com.junpu.wordbook.db.WordDatabase

/**
 * 单词仓库
 * @author junpu
 * @date 2020/7/23
 */
class WordRepository(private val context: Context) {

    private val wordDao by lazy { WordDatabase.getInstance(context).wordDao }

    /**
     * 获取所有单词
     */
    val allWords
        get() = wordDao.getAllWords()

    /**
     * 模糊查询
     */
    fun fuzzySearchWords(condition: String?) = wordDao.fuzzySearchWords("%$condition%")

    /**
     * 添加单词
     */
    fun addWord(vararg word: Word) {
        wordDao.insert(*word)
    }

    /**
     * 更改单词
     */
    fun update(vararg word: Word) {
        wordDao.update(*word)
    }

    /**
     * 删除单词
     */
    fun deleteWord(vararg word: Word) {
        wordDao.delete(*word)
    }

    /**
     * 清除单词
     */
    fun clear() {
        wordDao.clear()
    }
}