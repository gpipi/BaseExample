package com.lorenzogao.lib_base.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import com.lorenzogao.lib_base.R

/**
 *  create by LorenzoGao
 *  email: 2508719070@qq.com
 *  TIME: 2020-04-01 21:01
 *  description:
 */

class BaseDialog(context: Context, themeResId: Int) :
    Dialog(context, themeResId) {


    private var mAlert: AlertController = AlertController(this, window)


    /*
  * 设置文本
  * */
    fun setText(viewId: Int, text: CharSequence) {
        mAlert.setText(viewId, text)
    }
    /*
    * 设置点击事件
    * */

    fun setOnClickListener(viewId: Int, onClickListener: View.OnClickListener) {
        mAlert.setOnClickListener(viewId, onClickListener)
    }


    fun <T : View> getView(viewId: Int): T? {
        return mAlert.getView(viewId)
    }





    fun setImageRes(viewId: Int, resId:Int){
        mAlert.setImageRes(viewId,resId)

    }






    class Builder(context: Context, themeResId: Int = R.style.dialog) {


        private val P: AlertController.AlertParams =
            AlertController.AlertParams(context, themeResId)


        fun setContentView(view: View): Builder {
            P.mView = view
            P.mLayoutResId = 0
            return this
        }

        fun setContentView(layoutResId: Int): Builder {
            P.mView = null
            P.mLayoutResId = layoutResId
            return this
        }


        fun setText(viewId: Int, text: CharSequence): Builder {
            P.mTextArray.put(viewId, text)

            return this
        }


        fun setOnClickListener(viewId: Int, listener: View.OnClickListener): Builder {
            P.mClickArray.put(viewId, listener)
            return this
        }

        fun setImageRes(viewId: Int, resId: Int): Builder {
            P.mImageRes.put(viewId, resId)
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.P.mCancelable = cancelable
            return this
        }


        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            P.mOnCancelListener = onCancelListener
            return this
        }


        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            P.mOnDismissListener = onDismissListener
            return this
        }


        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener): Builder {
            P.mOnKeyListener = onKeyListener
            return this
        }

        //配置万能参数
        fun fullWidth(): Builder {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }

        //从底部弹出是否有动画
        fun formBottom(isAnimation: Boolean): Builder {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_form_bottom_amin

            }
            P.mGravity = Gravity.BOTTOM
            return this
        }

        //设置宽高
        fun setWidthAndHeight(widht: Int, height: Int): Builder {
            P.mWidth = widht
            P.mHeight = height
            return this
        }


        fun setWidth(widht: Int): Builder {
            P.mWidth = widht
            return this
        }



        fun setHeight(height: Int): Builder {
            P.mHeight = height
            return this
        }


        //添加默认动画
        fun addDefaultAnimation(): Builder {
            P.mAnimation = R.style.dialog_form_bottom_amin
            return this
        }

        /*
        **设置动画
         */
        fun addAnimation(styleAnimation: Int): Builder {
            P.mAnimation = styleAnimation
            return this
        }


        fun setGravity(gravity: Int): Builder {
            P.mGravity=gravity
            return this
        }

        /**
         * Creates an [AlertDialog] with the arguments supplied to this
         * builder.
         *
         *
         * Calling this method does not display the dialog. If no additional
         * processing is needed, [.show] may be called instead to both
         * create and display the dialog.
         */
        fun create(): BaseDialog {
            // Context has already been wrapped with the appropriate theme.
            val dialog = BaseDialog(P.context, P.themeResId)
            P.apply(dialog.mAlert)
            dialog.setCancelable(P.mCancelable)
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true)
            }
            dialog.setOnCancelListener(P.mOnCancelListener)
            dialog.setOnDismissListener(P.mOnDismissListener)
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener)
            }
            return dialog
        }

        /**
         * Creates an [AlertDialog] with the arguments supplied to this
         * builder and immediately displays the dialog.
         *
         *
         * Calling this method is functionally identical to:
         * <pre>
         * AlertDialog dialog = builder.create();
         * dialog.show();
        </pre> *
         */
        fun show(): BaseDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }

    }


}