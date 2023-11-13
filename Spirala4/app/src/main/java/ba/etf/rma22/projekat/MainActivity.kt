package ba.etf.rma22.projekat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.util.NetworkUtil
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.anketa.FragmentAnkete
import ba.etf.rma22.projekat.view.istrazivanje.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.main.CustomFragmentStateAdapter
import ba.etf.rma22.projekat.view.main.MainActivityViewModel
import ba.etf.rma22.projekat.view.pitanje.FragmentPitanje
import ba.etf.rma22.projekat.view.pitanje.FragmentPredaj


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var pageAdapter: CustomFragmentStateAdapter
    private lateinit var pitanjaList: List<Pitanje>
    private lateinit var fragments: MutableList<Fragment>

    private val mainActivityViewModel = MainActivityViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //provjeri je li upaljen uredjaj
        NetworkUtil.isDeviceConnected = isConnected(applicationContext)

        //postavi context za bazu
        AppDatabase.createInstance(applicationContext)

        val payload: String? = intent?.extras?.getString("payload")

        if (payload != null) {
            mainActivityViewModel.saveKorisnikHash(payload)
        } else {
            // TODO handle kad se normalno otvori aplikacija
        }

//        TODO odkomentarisati
//        fragments = mutableListOf()
//
//        viewPager2 = findViewById(R.id.pager)
//
//        setDefaultFragments()
//
//        viewPager2.offscreenPageLimit = 2
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                if (position == 0 && pageAdapter.getItem(viewPager2.currentItem) is FragmentPoruka) {
//                    pageAdapter.refreshFragment(1, FragmentIstrazivanje())
//                    pageAdapter.refreshFragment(0, FragmentAnkete())
//                }
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//        })
//        pageAdapter = CustomFragmentStateAdapter(supportFragmentManager, fragments, lifecycle)
//        viewPager2.adapter = pageAdapter

    }

    private fun isConnected(context: Context?): Boolean {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    }
            }
            return false
    }

    private fun setDefaultFragments() {
        fragments.clear()
        fragments.add(FragmentAnkete())
        fragments.add(FragmentIstrazivanje())

    }

    private fun resetToDefaultFragments() {
        setDefaultFragments()
        pageAdapter.notifyDataSetChanged()
    }


    override fun onBackPressed() {

        if (pageAdapter.getItem(fragments.size-1) is FragmentPredaj) {
            //Nalazimo se u anketi
            zaustaviAnketu()
        } else if (viewPager2.currentItem == 0) {
            super.onBackPressed()
        } else if (pageAdapter.getItem(viewPager2.currentItem) is FragmentPoruka){
            pageAdapter.refreshFragment(1, FragmentIstrazivanje())
            pageAdapter.refreshFragment(0, FragmentAnkete())
            viewPager2.currentItem = 0
        } else {
            viewPager2.currentItem = 0
        }
    }

    fun prikaziPorukuFragment() {

        // todo obrisati
        val grupa = Grupa(0,Korisnik.upisaneGrupe[Korisnik.upisaneGrupe.size - 1], Korisnik.upisanaIstrazivanja[Korisnik.upisanaIstrazivanja.size - 1],)
        pageAdapter.refreshFragment(1,
            FragmentPoruka.newInstance(grupa))
    }

    fun prikaziPitanjaZaAnketu(anketa: Anketa) {

        if (mainActivityViewModel.korisnikImaPristup(anketa)){
            pitanjaList = mainActivityViewModel.getPitanjaForNazivAnketaAndNazivIstrazivanje(anketa.naziv, anketa.nazivIstrazivanja!!)

            fragments.clear()
            pitanjaList.forEach { pitanje -> fragments.add(FragmentPitanje.newInstance(pitanje, anketa)) }
            fragments.add(FragmentPredaj.newInstance(anketa))

            pageAdapter.notifyDataSetChanged()

        } else {
            Toast.makeText(this, "Korisnik nije upisan u grupu", Toast.LENGTH_SHORT).show()
        }
    }

    fun predajAnketu(anketa: Anketa) {
        fragments.clear()

        fragments.add(FragmentAnkete())
        fragments.add(FragmentPoruka.newInstance(anketa))

        pageAdapter.notifyDataSetChanged()

        viewPager2.setCurrentItem(1, false)

    }

    fun zaustaviAnketu() {
        resetToDefaultFragments()
        viewPager2.setCurrentItem(0, false)
    }
}