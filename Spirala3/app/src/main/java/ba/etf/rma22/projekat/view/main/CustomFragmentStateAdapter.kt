package ba.etf.rma22.projekat.view.main

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.anketa.FragmentAnkete
import ba.etf.rma22.projekat.view.istrazivanje.FragmentIstrazivanje

class CustomFragmentStateAdapter(
    fragmentManager: FragmentManager,
    var fragments: MutableList<Fragment>,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun refreshFragment(position: Int, fragment: Fragment) {
        fragments[position] = fragment
        notifyItemChanged(position)
    }

    override fun getItemId(position: Int): Long {
//        if (position == 0 && getItem(1) is FragmentPoruka) refreshFragment(1, FragmentIstrazivanje())
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find { it.hashCode().toLong() == itemId } != null
    }

    fun getItem(currentItem: Int): Fragment {
        return fragments[currentItem]
    }


}