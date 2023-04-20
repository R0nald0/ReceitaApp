package com.example.receitas.presentation.view

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.receitas.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test

class SalvarEditarActivityTest{

    @get:Rule
     val activityScenarioRule = ActivityScenarioRule(SalvarEditarActivity::class.java)

    @Test
    fun `salvar_devePreencharOsCamposESalvarUmaReceita`() {
      /*  // onView(withId(R.id.img_button_add_receita)).perform(click())

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
            )*/

        val textInputEditText2 = onView(
            Matchers.allOf(
                withId(R.id.edt_nome_receita),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.txinp_nome_receita),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText("torta"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(3000)

        val maskedEditText = onView(
            Matchers.allOf(
                withId(R.id.edt_tempo_preparo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.txinp_tempo_preparo),
                        0
                    ),
                    0
                )
            )
        )
        maskedEditText.perform(scrollTo(), replaceText("58"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(3000)

        val textInputEditText5 = onView(
            Matchers.allOf(
                withId(R.id.edt_ingredientes_receita),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.txinp_ingrediente_receita),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("ovo,leite,acucar"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(3000)


        val textInputEditText10 = onView(
            Matchers.allOf(
                withId(R.id.edt_intreucoes_receita),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.txinp_intreucoes_receita),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText10.perform(scrollTo(), replaceText("esperar"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(3000)

        val textInputEditText11 = onView(
            Matchers.allOf(
                withId(R.id.edt_intreucoes_receita), ViewMatchers.withText("esperar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.txinp_intreucoes_receita),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText11.perform(scrollTo(),
            replaceText(
                """
                Coloque no liquidificador o creme de leite (com soro mesmo) e o leite condensado.
                Bata um pouco e depois vá acrescentando o suco do limão, aos poucos.
                Ele vai ficar bem consistente, leve à geladeira.
                 """
            )
        )

        val textInputEditText12 = onView(
            Matchers.allOf(
                withId(R.id.edt_intreucoes_receita), ViewMatchers.withText(
                    """
                Coloque no liquidificador o creme de leite (com soro mesmo) e o leite condensado.
                Bata um pouco e depois vá acrescentando o suco do limão, aos poucos.
                Ele vai ficar bem consistente, leve à geladeira.
                 """
                ),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.txinp_intreucoes_receita),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textInputEditText12.perform(closeSoftKeyboard())
        Thread.sleep(3000)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }

    }
}