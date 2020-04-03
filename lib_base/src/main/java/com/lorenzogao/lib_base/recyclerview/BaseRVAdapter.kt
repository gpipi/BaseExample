package com.lorenzogao.lib_base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType

/**
 *  create by LorenzoGao
 *  email: 2508719070@qq.com
 *  TIME: 2020-04-03 10:03
 *  description:
 */

abstract class BaseRVAdapter<T, K : BaseViewHolder> : RecyclerView.Adapter<K> {


    var mData = arrayListOf<T>()


    lateinit var mContext: Context

    private var mLayoutId: Int = 0


    constructor(layoutId: Int) {
        this.mLayoutId = layoutId
        mData = ArrayList<T>()
    }

    constructor(layoutId: Int, data: List<T>) {
        this.mLayoutId = layoutId
        this.mData.addAll(data)
    }


    fun addData(data: T) {
        mData.add(data)
        notifyItemChanged(mData.size-1)

    }

    fun addData(data: List<T>) {
        mData.addAll(data)
        notifyItemRangeChanged(mData.size-data.size,data.size)
    }

    fun  setData(position:Int,data: T){
        mData[position] = data
        notifyItemChanged(mData.size-1)
    }








    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false)
        return createBaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size

    }

    override fun onBindViewHolder(holder: K, position: Int) {
        convert(holder, mData[position])


    }


    abstract fun convert(holder: K, t: T)

    private fun createBaseViewHolder(view: View): K {
        var temp = javaClass
        var z: Class<*>? = null
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp!!)
            temp = temp!!.superclass as Class<BaseRVAdapter<T, K>>
        }
        val k: K?
        // 泛型擦除会导致z为null
        k = if (z == null) {
            BaseViewHolder(view) as K
        } else {
            createGenericKInstance(z, view)
        }
        return k ?: BaseViewHolder(view) as K
    }

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    private fun createGenericKInstance(z: Class<*>, view: View): K? {
        try {
            val constructor: Constructor<*>
            // inner and unstatic class
            if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
                constructor = z.getDeclaredConstructor(javaClass, View::class.java)
                constructor.isAccessible = true
                return constructor.newInstance(this, view) as K
            } else {
                constructor = z.getDeclaredConstructor(View::class.java)
                constructor.isAccessible = true
                return constructor.newInstance(view) as K
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private fun getInstancedGenericKClass(z: Class<*>): Class<*>? {
        val type = z.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            for (temp in types) {
                if (temp is Class<*>) {
                    if (BaseViewHolder::class.java.isAssignableFrom(temp)) {
                        return temp
                    }
                }
            }
        }
        return null
    }
}