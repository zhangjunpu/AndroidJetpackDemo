package com.junpu.wordbook.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.junpu.wordbook.R
import com.junpu.wordbook.db.Word
import kotlinx.android.synthetic.main.dialog_add_word.*

/**
 * 添加单词对话框
 * @author junpu
 * @date 2020/7/23
 */
class AddWordDialog : Dialog {

    private var submitListener: ((AddWordDialog, Word) -> Unit)? = null
    private var cancelListener: ((AddWordDialog) -> Unit)? = null

    constructor(context: Context) : super(context, 0)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_word)
        btnCancel.setOnClickListener {
            cancelListener?.invoke(this)
            cancel()
            reset()
        }
        btnSubmit.setOnClickListener {
            val english = editEnglish.text.toString()
            val chinese = editChinese.text.toString()
            if (english.isBlank()) {
                editEnglish.requestFocus()
                return@setOnClickListener
            }
            if (chinese.isBlank()) {
                editChinese.requestFocus()
                return@setOnClickListener
            }
            submitListener?.invoke(this, Word(englishName = english, chineseName = chinese))
            cancel()
            reset()
        }
    }

    private fun reset() {
        editEnglish.text = null
        editChinese.text = null
    }

    fun doOnCancel(callback: ((AddWordDialog) -> Unit)? = null) {
        cancelListener = callback
    }

    fun doOnSubmit(callback: ((AddWordDialog, Word) -> Unit)? = null) {
        submitListener = callback
    }

    class Builder(private val context: Context) {
        private var submitListener: ((AddWordDialog, Word) -> Unit)? = null
        private var cancelListener: ((AddWordDialog) -> Unit)? = null

        fun doOnCancel(callback: ((AddWordDialog) -> Unit)? = null) = apply {
            cancelListener = callback
        }

        fun doOnSubmit(callback: (AddWordDialog, Word) -> Unit) = apply {
            submitListener = callback
        }

        fun create(): AddWordDialog = AddWordDialog(
            context
        ).apply {
            doOnSubmit(this@Builder.submitListener)
            doOnCancel(this@Builder.cancelListener)
        }

        fun show() = create().apply {
            show()
        }
    }
}