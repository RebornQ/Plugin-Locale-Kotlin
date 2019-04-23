package pw.gike.multilanguagesdemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import pw.gike.multilanguagesdemo.App

import pw.gike.multilanguagesdemo.R


class TestFragment : Fragment() {

    private lateinit var tvTestTips : TextView
    private lateinit var tvTestAppTips : TextView

    private lateinit var btStartTest : Button
    private lateinit var btStartAppTest : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_test, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(view: View) {
        tvTestTips = view.findViewById(R.id.tv_test_tips)
        tvTestAppTips = view.findViewById(R.id.tv_test_tips_application)
        btStartTest = view.findViewById(R.id.bt_test_start)
        btStartAppTest = view.findViewById(R.id.bt_test_start_application)

        tvTestTips.text = getString(R.string.fragment_string)
        tvTestAppTips.text = App.instance.getString(R.string.fragment_application_string)
        val text = getString(R.string.click) + " " + getString(R.string.fragment_string)
        val applicationText = App.instance.getString(R.string.click) + " " +  App.instance.getString(R.string.fragment_application_string)
        btStartTest.setOnClickListener {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        btStartAppTest.setOnClickListener {
            Toast.makeText(App.instance, applicationText, Toast.LENGTH_SHORT).show()
        }
    }
}
