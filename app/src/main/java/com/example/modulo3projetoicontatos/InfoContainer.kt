package com.example.modulo3projetoicontatos

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/* Classe responsável por controlar o info_container.xml */
class InfoContainer(
    context: Context,
    attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    init{
        inflate(context, R.layout.info_container, this@InfoContainer)
        context.theme.obtainStyledAttributes(
            attributeSet,
            // Cria uma estilizavel pro Action Button para passar as  propriedades estilizaveis dele, que são: buttonIcon e buttonTitle
            R.styleable.InfoContainer,
            0,0
        ).apply {
            // aplicar o valor da propriedade no componente/view
            try {
                findViewById<TextView>(R.id.infoTitle).text = getString(R.styleable.InfoContainer_infoTitle)
                findViewById<TextView>(R.id.infoValue).text = getString(R.styleable.InfoContainer_infoValue)
                findViewById<ImageView>(R.id.infoIcon).setImageResource(getResourceId(R.styleable.InfoContainer_infoIcon, R.drawable.ic_close))
            }finally {
                // ao final ele usa o recycle para atualizar o componente
                recycle()
            }
        }
    }

    // setando o Value dinamicamente, ou seja, o que o programador passar
    fun setInfoValue(value: String?){
        findViewById<TextView>(R.id.infoValue).text = value
    }

}