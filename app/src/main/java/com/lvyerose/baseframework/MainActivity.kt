package com.lvyerose.baseframework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lvyerose.baseframework.recycler.RecyclerMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_test_recycler.setOnClickListener { startActivity(Intent(this, RecyclerMainActivity::class.java)) }
    }


}
