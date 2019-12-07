package pw.gike.multilanguagesdemo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.mallotec.reb.languageplugin.utils.ActivityUtil
import com.mallotec.reb.languageplugin.ui.base.BaseAppCompactActivity
import com.mallotec.reb.languageplugin.utils.LocaleManageUtil
import pw.gike.multilanguagesdemo.R


// 此处不能继承AppCompatActivity，否则无法通过attachBaseContext()刷新语言
class SettingActivity : BaseAppCompactActivity() {

    private lateinit var tvSelectLanguage : TextView

    private lateinit var btSelectLanguage : Button

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initView()
        tvSelectLanguage.text = getString(
            R.string.user_select_language,
            LocaleManageUtil.getSelectLanguageString(this)
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
        LocaleManageUtil.saveSelectLanguage(select.toString())
        // 使用 EventBus 可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
//        EventBus.getDefault().post(Constant.EVENT_RECREATE_ACTIVITY)

        // 使用广播也可以实现不重启到 LauncherActivity 只需 recreate() 即可刷新 Resources
        ActivityUtil.recreateActivity(this)

//        LocaleManageUtil.toRestartLauncherActivity(this)
    }
}
