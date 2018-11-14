package com.wsst.lxns

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.wsst.lxns.utils.ProxyMethod
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {
    private var imageIdArray: IntArray? = null
    private var viewList: MutableList<View> = arrayListOf()
    private var ivPointArray: Array<ImageView>? = null
    var name: String  by ProxyMethod("张三", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        tvName.setOnClickListener {
            name = "王五郎"
        }
        Log.e("key ", "GuideActivity$name")
    }
}