package com.junpu.calculation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.junpu.log.L
import com.junpu.log.logStackTrace
import java.util.*

/**
 * Oral Calculation ViewModel
 * @author junpu
 * @date 2020/7/17
 */
class OralViewModel(application: Application, private val handler: SavedStateHandle) :
    AndroidViewModel(application) {

    companion object {
        const val SHP_NAME_ORAL = "oral_calculation"
        const val HIGH_SCORE = "high_score"
        const val CUR_SCORE = "cur_score"
        const val LEFT_NUMBER = "left_number"
        const val RIGHT_NUMBER = "right_number"
        const val OPERATOR = "operator"
        const val ANSWER = "answer"
    }

    private val sharedPreferences by lazy {
        getApplication<App>().getSharedPreferences(SHP_NAME_ORAL, Context.MODE_PRIVATE)
    }

    private val stringResource = { resId: Int ->
        getApplication<App>().getString(resId)
    }

    init {
        if (!handler.contains(HIGH_SCORE)) {
            handler.run {
                set(HIGH_SCORE, sharedPreferences.getInt(HIGH_SCORE, 0))
                set(CUR_SCORE, 0)
                set(LEFT_NUMBER, 0)
                set(RIGHT_NUMBER, 0)
                set(OPERATOR, "")
                set(ANSWER, stringResource(R.string.my_answer))
            }
        }
    }

    val highScore
        get() = handler.getLiveData<Int>(HIGH_SCORE)
    val curScore
        get() = handler.getLiveData<Int>(CUR_SCORE)
    val leftNumber
        get() = handler.getLiveData<Int>(LEFT_NUMBER)
    val rightNumber
        get() = handler.getLiveData<Int>(RIGHT_NUMBER)
    val operator
        get() = handler.getLiveData<String>(OPERATOR)
    val answer
        get() = handler.getLiveData<String>(ANSWER)
    var isWin = false

    fun save() {
        sharedPreferences.edit()
            .putInt(HIGH_SCORE, highScore.value ?: 0)
            .apply()
    }

    /**
     * 新一轮挑战开始
     */
    fun newRound() {
        isWin = false
        clear()
        generateQuestion()
    }

    /**
     * 数字键盘
     */
    fun number(num: Int) {
        val result = if (answer.value == stringResource(R.string.my_answer) ||
            answer.value == stringResource(R.string.right)
        ) {
            StringBuilder()
        } else {
            StringBuilder(answer.value.toString())
        }
        result.append(num)
        answer.value = result.toString()
    }

    /**
     * 清除
     */
    fun clear() {
        answer.value = stringResource(R.string.my_answer)
    }

    /**
     * 提交
     */
    fun submit(callback: () -> Unit) {
        if (answer.value == stringResource(R.string.my_answer) || answer.value == stringResource(R.string.right)) return
        try {
            val result = answer.value?.toInt() ?: 0
            val leftNum = leftNumber.value ?: 0
            val rightNum = rightNumber.value ?: 0
            if (result == if (operator.value == "+") leftNum + rightNum else leftNum - rightNum) {
                // right
                answer.value = stringResource(R.string.right)
                curScore.value = (curScore.value ?: 0) + 1
                L.vv(curScore.value)
                if ((curScore.value ?: 0) > (highScore.value ?: 0)) {
                    isWin = true
                    highScore.value = curScore.value
                    save()
                }
                generateQuestion()
            } else {
                // wrong
                callback()
            }
        } catch (e: Exception) {
            e.logStackTrace()
        }
    }

    /**
     * 新问题
     */
    fun generateQuestion() {
        val level = 20
        val num1: Int
        val num2: Int
        Random().run {
            num1 = nextInt(level) + 1
            num2 = nextInt(level) + 1
        }
        if (num1 > num2) {
            leftNumber.value = num2
            rightNumber.value = num1 - num2
            operator.value = "+"
        } else {
            leftNumber.value = num2
            rightNumber.value = num2 - num1
            operator.value = "-"
        }
    }

}