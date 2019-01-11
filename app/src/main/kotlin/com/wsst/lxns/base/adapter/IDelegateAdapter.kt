package com.wsst.lxns.base.adapter

/**
 * 通用的adapter必须实现的接口，作为方法名统一的一个规范
 * Created by 谢岳峰 on 2018/10/31.
 */
interface IDelegateAdapter<T> {
     var data: MutableList<T>

}