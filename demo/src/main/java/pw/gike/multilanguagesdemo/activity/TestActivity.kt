package pw.gike.multilanguagesdemo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pw.gike.multilanguagesdemo.App
import pw.gike.multilanguagesdemo.R
import pw.gike.multilanguagesdemo.fragment.TestFragment

class TestActivity : BaseActivity() {

    private lateinit var tvApplicationContext: TextView
    private lateinit var tvActivityContext: TextView
    private lateinit var btSelectLanguage : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
        setValue()
        replaceFragment(TestFragment())
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentManager =  supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initView() {
        tvApplicationContext = findViewById(R.id.tv_application_context)
        tvActivityContext = findViewById(R.id.tv_activity_context)
        btSelectLanguage = findViewById(R.id.bt_select_language)

        btSelectLanguage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setValue() {
        tvApplicationContext.text = App.instance.getString(R.string.application_context, App.instance.getString(R.string.test_success))
        tvActivityContext.text = getString(R.string.activity_context, getString(R.string.test_success))
    }
}
