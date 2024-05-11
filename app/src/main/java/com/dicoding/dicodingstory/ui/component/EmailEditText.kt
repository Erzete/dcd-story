package com.dicoding.dicodingstory.ui.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern


class EmailEditText : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)



    init {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && !validateEmail(s.toString().trim())) {
                    (parent.parent as? TextInputLayout)?.apply {
                        error = "Pastikan format email benar"
                        isErrorEnabled = true
                    }
                } else {
                    (parent.parent as? TextInputLayout)?.apply {
                        isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    private fun validateEmail(data: String): Boolean {
        val emailPattern: Pattern = Pattern.compile(".+@.+\\.[a-z]+")
        val emailMatcher: Matcher = emailPattern.matcher(data)
        return emailMatcher.matches()
    }
}