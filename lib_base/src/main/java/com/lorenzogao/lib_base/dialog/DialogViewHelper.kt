package com.lorenzogao.lib_base.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.forEach
import java.lang.ref.WeakReference

/**
 *  create by LorenzoGao
 *  email: 2508719070@qq.com
 *  TIME: 2020-04-02 20:11
 *  description:dialog 辅助处理类
 */


internal class DialogViewHelper constructor() {

    private var mContext:Context?=null

     var mContentView:View?=null

     var mView: SparseArray<WeakReference<View>> = SparseArray()


    fun setContentView(view: View) {
        mContentView=view

    }

    constructor(context: Context,layoutResId: Int) : this(){
        mContext=context
        mContentView=LayoutInflater.from(mContext).inflate(layoutResId,null)
    }


    /*
    * 设置文本
    * */
    fun setText(viewId: Int, charSequence: CharSequence) {
        // 每次都findViewById  减少findViewById的次数
        val tv = getView<TextView>(viewId)
        tv?.let {
            tv.text = charSequence
        }


    }



    fun setImageRes(viewId: Int, resId: Int) {
        val iv = getView<ImageView>(viewId)
        iv?.let {
            iv.setImageResource(resId)
        }

    }



    /*
    * 设置点击事件
    * */

    fun setOnClickListener(viewId: Int, onClickListener: View.OnClickListener) {
        val view = getView<View>(viewId)
        view?.setOnClickListener(onClickListener)
    }


    fun <T : View> getView(viewId: Int): T? {
        val WeakReference = mView.get(viewId)

        var view: View? = null
        if (WeakReference != null) {
            view = WeakReference!!.get()
        }
        if (view == null) {
            view = mContentView?.findViewById(viewId)
            if (view != null) {
                mView.put(viewId, WeakReference(view))
            }
        }
        return view as T?
    }




}