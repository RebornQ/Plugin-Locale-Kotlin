package pw.gike.multilanguagesdemo.activity

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.mallotec.reb.localeplugin.utils.LocaleHelper
import pw.gike.multilanguagesdemo.App
import pw.gike.multilanguagesdemo.R

class MainActivity : BaseActivity() {

    private lateinit var btStartSettingActivity: Button
    private lateinit var btTestActivityString: Button
    private lateinit var btTestApplicationString: Button
    private lateinit var btTestFragment: Button

    private lateinit var tvAppName: TextView
    private lateinit var tvSystemLanguage: TextView
    private lateinit var tvUserSelectLanguage: TextView
    private lateinit var tvApplicationString: TextView
    private lateinit var tvActivityString: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setValue()
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        overrideConfiguration?.setLocale(LocaleHelper.getInstance().getSetLocale())
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    private fun initView() {
        btStartSettingActivity = findViewById(R.id.bt_settings)
        btTestApplicationString = findViewById(R.id.bt_test_application_string)
        btTestActivityString = findViewById(R.id.bt_test_activity_string)
        btTestFragment = findViewById(R.id.bt_test_fragment)

        tvAppName = findViewById(R.id.tv_app_name)
        tvSystemLanguage = findViewById(R.id.tv_system_language)
        tvUserSelectLanguage = findViewById(R.id.tv_select_language)
        tvApplicationString = findViewById(R.id.tv_application_string)
        tvActivityString = findViewById(R.id.tv_activity_string)

        btStartSettingActivity.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        btTestApplicationString.setOnClickListener {
            tvApplicationString.text = App.instance.getString(
                R.string.application_string,
                App.instance.getString(R.string.test_success)
            )
            Toast.makeText(
                App.instance,
                App.instance.getString(R.string.test_success),
                Toast.LENGTH_SHORT
            ).show()
        }
        btTestActivityString.setOnClickListener {
            tvActivityString.text =
                getString(R.string.activity_string, getString(R.string.test_success))
            Toast.makeText(this, getString(R.string.test_success), Toast.LENGTH_SHORT).show()
        }
        btTestFragment.setOnClickListener {
            val intent = Intent(this@MainActivity, TestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setValue() {
        tvAppName.text = App.instance.getString(R.string.app_name_label, getApplicationName())
        val string = getString(
            R.string.system_language,
            LocaleHelper.getInstance().getCurrentSystemLocale().displayLanguage
        )
        tvSystemLanguage.text = string
        tvUserSelectLanguage.text = getString(
            R.string.user_select_language,
            LocaleHelper.getInstance().getSelectLanguageString(this)
        )
        tvApplicationString.text = App.instance.getString(
            R.string.application_string,
            App.instance.getString(R.string.test_success)
        )
        tvActivityString.text =
            getString(R.string.activity_string, getString(R.string.test_success))
    }

    private fun getApplicationName(): String {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo? = null
        try {
            packageManager = applicationContext.packageManager
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        val applicationName = packageManager!!.getApplicationLabel(applicationInfo)
        return applicationName.toString()
    }
}
