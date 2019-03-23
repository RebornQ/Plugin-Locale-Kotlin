package pw.gike.multilanguagesdemo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pw.gike.multilanguagesdemo.R
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil

class MainActivity : BaseActivity() {

    private lateinit var btStartSettingActivity: Button

    private lateinit var tvSystemLanguage: TextView
    private lateinit var tvUserSelectLanguage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setValue()
    }

    private fun initView() {
        btStartSettingActivity = findViewById(R.id.bt_settings)

        tvSystemLanguage = findViewById(R.id.tv_system_language)
        tvUserSelectLanguage = findViewById(R.id.tv_select_language)

        btStartSettingActivity.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setValue() {
        val string = getString(
            R.string.system_language,
            LocaleManageUtil.getSystemLocale(this).displayLanguage
        )
        tvSystemLanguage.text = string
        tvUserSelectLanguage.text = getString(
            R.string.user_select_language,
            LocaleManageUtil.getSelectLanguage(this)
        )
    }
}
