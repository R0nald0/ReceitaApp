package com.example.receitas.presentation.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.receitas.R
import org.junit.Rule
import org.junit.Test

class SalvarEditarActivityTest{

    @get:Rule
     val activityScenarioRule = ActivityScenarioRule(SalvarEditarActivity::class.java)

    @Test
    fun `salvar_devePreencharOsCamposESalvarUmaReceita`() {
        // onView(withId(R.id.img_button_add_receita)).perform(click())

         onView(withId(R.id.edt_nome_receita)).perform(typeText("MOUSSE DE LIMAO"))

       // onView(withId(R.id.scroll_View_add_receita)).perform(swipeUp())
         onView(withId(R.id.edt_tempo_preparo)).perform(typeText("10"))
         onView(withId(R.id.edt_ingredientes_receita)).perform(
             typeText("""
                 uma lata de leite condensado uma lata de creme de leite
                 uma lata e meia xícara de suco de limao esse suco e puro mesmo sem agua e so espremer o limao"""
             )
         )
         onView(withId(R.id.edt_intreucoes_receita)).perform(
             typeText("""
                Coloque no liquidificador o creme de leite (com soro mesmo) e o leite condensado.
                Bata um pouco e depois vá acrescentando o suco do limão, aos poucos.
                Ele vai ficar bem consistente, leve à geladeira.
                 """
                 )
            )
    }
}