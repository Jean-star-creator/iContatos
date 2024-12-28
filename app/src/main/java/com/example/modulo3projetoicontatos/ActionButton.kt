package com.example.modulo3projetoicontatos

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/* Essa será uma classe controladora */
class ActionButton(
    context: Context,
    attributSet: AttributeSet,
): LinearLayout(context, attributSet) {

    init{
        inflate(context, R.layout.action_button, this@ActionButton)
        context.theme.obtainStyledAttributes(
            attributSet,
            // Cria uma estilizavel pro Action Button para passar as  propriedades estilizaveis dele, que são: buttonIcon e buttonTitle
            R.styleable.ActionButton,
            0,0
        ).apply {
            // aplicar o valor da propriedade no componente/view
            try {
                findViewById<TextView>(R.id.buttonTitle).text = getString(R.styleable.ActionButton_buttonTitle)
                findViewById<ImageView>(R.id.buttonIcon).setImageResource(getResourceId(R.styleable.ActionButton_buttonIcon, R.drawable.ic_close))
            }finally {
                // ao final ele usa o recycle para atualizar o componente
                recycle()
            }
        }
    }

    // Esta function só será usada para buttons customizados que estão, em sua maioria, no containerViewFragment
    fun onclickListener(listener: OnClickListener){
        findViewById<LinearLayout>(R.id.container).setOnClickListener(listener)
    }

}