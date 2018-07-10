package com.framgia.bitcoinwallet.util.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.framgia.bitcoinwallet.R

class EditTextCustom @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var img: ImageView? = null
    private var edt: EditText? = null


    init {
        inflate(context, R.layout.text_view_box, this)
        initView()
        setupAttributes(attrs)
    }

    private fun initView() {
        img = findViewById(R.id.img)
        edt = findViewById(R.id.edt)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typeArray = context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextCustom, 0, 0)
        var res = typeArray.getDrawable(R.styleable.EditTextCustom_setImage)
        var resText = typeArray.getString(R.styleable.EditTextCustom_textHint)
        var inputType = typeArray.getInt(R.styleable.EditTextCustom_android_inputType, EditorInfo.TYPE_NULL)
        img?.setImageDrawable(res)
        edt?.setHint(resText)
        edt?.setRawInputType(inputType)
        typeArray.recycle()
    }

    fun setTextHint(text: String?) {
        edt?.setHint(text)
    }

    fun setText(text: String?) {
        edt?.setText(text)
    }

    fun getText() = edt?.text

    fun setImage(res: Int) {
        img?.setImageResource(res)
    }
}
