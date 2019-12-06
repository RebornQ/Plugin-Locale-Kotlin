package pw.gike.multilanguagesdemo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pw.gike.multilanguagesdemo.R
import androidx.appcompat.app.AlertDialog
import org.greenrobot.eventbus.EventBus
import pw.gike.multilanguagesdemo.Constant
import pw.gike.multilanguagesdemo.utils.LocaleManageUtil

// 此处不能继承AppCompatActivity，否则无法通过attachBaseContext()刷新语言
class SettingActivity : BaseActivity() {

    private lateinit var tvSelectLanguage : TextView

    private lateinit var btSelectLanguage : Button

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initView()
        tvSelectLanguage.text = getString(
            R.string.user_select_language,
            LocaleManageUtil.getSelectLanguage(this)
        )
    }

    private fun initView() {
        tvSelectLanguage = findViewById(R.id.tv_select_language)

        btSelectLanguage = findViewById(R.id.bt_select_language)

        btSelectLanguage.setOnClickListener {
            listLanguageDialog()
        }

    }

    private fun listLanguageDialog() {
        val languages = arrayOf(getString(R.string.text_language_auto), getString(R.string.text_language_zh), getString(R.string.text_language_en))
        val listDialog = AlertDialog.Builder(this)
        listDialog.setTitle(getString(R.string.please_select_language))
        listDialog.setItems(languages) { dialog, which ->
            selectLanguage(which)
            dialog.dismiss()
        }
        listDialog.show()
    }

    private fun selectLanguage(select: Int) {
        LocaleManageUtil.saveSelectLanguage(this, select)
        EventBus.getDefault().post(Constant.EVENT_REFRESH_LANGUAGE)
//        LocaleManageUtil.toRestartLauncherActivity(this)
    }
}
