package pw.gike.multilanguagesdemo.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mallotec.reb.languageplugin.ui.base.BaseAppCompactActivity
import pw.gike.multilanguagesdemo.R
import pw.gike.multilanguagesdemo.fragment.TestFragment

class TestActivity : BaseAppCompactActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        replaceFragment(TestFragment())
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentManager =  supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
