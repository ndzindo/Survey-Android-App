package ba.etf.rma22.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.view.pitanje.OpcijeAdapter
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)
    //TODO napisati testove
    @Test
    fun upisiUGrupu4Istrazivanja3IProvjeriTekstPorukeTest() {

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        onView(withId(R.id.odabirGodina)).perform(click())
        onView(withText("3")).perform(click());

        onView(withId(R.id.odabirIstrazivanja)).perform(click())
        onView(withText("nazivistrazivanja3")).perform(click());

        onView(withId(R.id.odabirGrupa)).perform(click())
        onView(withText("grupa4")).perform(click());

        onView(withId(R.id.dodajIstrazivanjeDugme)).perform(click())

        onView(withId(R.id.tvPoruka)).check(matches(withText("Uspješno ste upisani u\n" +
                "grupu grupa4 istraživanja nazivistrazivanja3!")))
    }

    @Test
    fun otvoriAnketu3OdgovoriNaPitanje1ProvjeriProgresIZavrsiAnketu() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        val mojeAnkete = AnketaRepository.getMyAnkete()
        val anketa3Id = mojeAnkete.indexOfFirst { anketa -> anketa.naziv == "anketa3" }

        onView(withId(R.id.listaAnketa)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    ViewMatchers.hasDescendant(withText(mojeAnkete[anketa3Id].naziv)),
                    ViewMatchers.hasDescendant(withText(mojeAnkete[anketa3Id].nazivIstrazivanja))
                ), click()))

        onView(allOf(withId(R.id.odgovoriLista), isDisplayed()))
            .perform(actionOnItem<OpcijeAdapter.ViewHolder>(withChild(withText("c")), click()));

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.progresTekst)).check(matches(withText("40%")))

        onView(withId(R.id.dugmePredaj)).perform(click())

        onView(withId(R.id.tvPoruka)).check(matches(withText("Završili ste anketu ${mojeAnkete[anketa3Id].naziv} u okviru istraživanja ${mojeAnkete[anketa3Id].nazivIstrazivanja}")))
    }
}

