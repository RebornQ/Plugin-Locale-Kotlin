package pw.gike.multilanguagesdemo.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.mallotec.reb.languageplugin.ui.base.BaseAppCompatActivity
import com.mallotec.reb.languageplugin.utils.LocaleManageUtil
import pw.gike.multilanguagesdemo.R


// 此处不能继承AppCompatActivity，否则无法通过attachBaseContext()刷新语言
class SettingActivity : BaseAppCompatActivity() {

    private lateinit var tvSelectLanguage : TextView

    private lateinit var btSelectLanguage : Button

    private lateinit var btGotoTest : Button

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
        btGotoTest = findViewById(R.id.bt_goto_test)

        btSelectLanguage.setOnClickListener {
            listLanguageDialog()
        }

        btGotoTest.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun listLanguageDialog() {
        val languages = resources.getStringArray(R.array.language_titles)
        val listDialog = AlertDialog.Builder(this)
        listDialog.setTitle(getString(R.string.please_select_language))
        listDialog.setItems(languages) { dialog, which ->
            // 应用切换的语言
//            val intent = Intent(this, MainActivity::class.java)
            LocaleManageUtil
                .language(resources.getStringArray(R.array.language_values)[which])
                .apply(this)
            dialog.dismiss()
        }
        listDialog.show()
    }
}
