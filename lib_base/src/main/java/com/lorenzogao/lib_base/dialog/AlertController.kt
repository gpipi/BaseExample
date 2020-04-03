package com.lorenzogao.lib_base.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window

/**
 *  create by LorenzoGao
 *  email: 2508719070@qq.com
 *  TIME: 2020-04-02 20:08
 *  description:
 */

class AlertController(
    val dialog: BaseDialog,
    val window: Window?
) {


    private var viewHelper: DialogViewHelper? = null


    private fun setViewHelper(viewHelper: DialogViewHelper) {
        this.viewHelper = viewHelper
    }

     fun setOnClickListener(viewId: Int, listener: View.OnClickListener) {
        viewHelper?.setOnClickListener(viewId,listener)

    }

     fun setText(viewId: Int, text: CharSequence) {
        viewHelper?.setText(viewId,text)
    }


    fun <T : View> getView(viewId: Int): T?{
        return viewHelper?.getView(viewId)
    }

    fun setImageRes(viewId: Int, resId: Int) {
        viewHelper?.setImageRes(viewId,resId)
    }


    class AlertParams(val context: Context, val themeResId: Int) {

        var mView: View? = null

        var mLayoutResId: Int = 0

        // dialog 消失监听
        var mOnDismissListener: DialogInterface.OnDismissListener? = null

        // 按键监听
        var mOnKeyListener: DialogInterface.OnKeyListener? = null

        // dialog 取消监听
        var mOnCancelListener: DialogInterface.OnCancelListener? = null

        // 点击空白是否取消
        var mCancelable: Boolean = false


        val mTextArray = SparseArray<CharSequence>()

        val mClickArray = SparseArray<View.OnClickListener>()
        val mImageRes = SparseArray<Int>()

        //宽度
        var mWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        //高度
        var mHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        //动画
        var mAnimation = 0
        //弹出位置
        var mGravity = Gravity.CENTER


        fun apply(alert: AlertController) {

            var viewHelper: DialogViewHelper? = null
            if (mLayoutResId != 0) {
                viewHelper = DialogViewHelper(context, mLayoutResId)
            }
            mView?.let {
                viewHelper = DialogViewHelper().apply {
                    setContentView(it)
                }
            }


            if (viewHelper == null) throw IllegalAccessException("请设置setContentView")


            //给dialog设置布局
            alert.window?.setContentView(viewHelper?.mContentView)
            //设置AlertController辅助类
            alert.setViewHelper(viewHelper!!)
            //设置文本
            for (i in 0 until mTextArray.size()) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i))
            }


            //设置点击
            for (i in 0 until mClickArray.size()) {
                alert.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i))
            }


            for (i in 0 until mImageRes.size()) {
                alert.setImageRes(mImageRes.keyAt(i), mImageRes.valueAt(i))
            }


            //配置自定义效果 全屏 从底部弹出 默认动画
            val window = alert.window
//            设置位置
            window?.setGravity(mGravity)
            //设置动画
            if (mAnimation != 0) {
                window?.setWindowAnimations(mAnimation)
            }
            val params = window?.attributes
            params?.width = mWidth
            params?.height = mHeight
//设置宽高
            window?.attributes = params


        }

    }


}