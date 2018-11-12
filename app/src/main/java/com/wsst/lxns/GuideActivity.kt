package com.wsst.lxns

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView

class GuideActivity : AppCompatActivity() {
    private var imageIdArray: IntArray? = null
    private var viewList: MutableList<View> = arrayListOf()

    private var ivPointArray : Array<ImageView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
    }
}