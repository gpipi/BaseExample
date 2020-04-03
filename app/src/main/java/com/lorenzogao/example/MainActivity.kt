package com.lorenzogao.example

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lorenzogao.lib_base.dialog.BaseDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        tv_show.setOnClickListener {

//           val dialog= BaseDialog.Builder(this)
//                .setContentView(R.layout.dialog)
//                .setText(R.id.tv_text,"LorenzoGao")
//                .show()
//
//            dialog.setOnClickListener(R.id.btn_click, View.OnClickListener {
//                Toast.makeText(this,"1111",Toast.LENGTH_SHORT).show()
//                dialog.dismiss()
//            })
//        }




    }
}
