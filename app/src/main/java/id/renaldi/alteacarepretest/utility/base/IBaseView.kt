package id.renaldi.alteacarepretest.utility.base

import androidx.annotation.LayoutRes

interface IBaseView {
    @LayoutRes
    fun getLayoutResource(): Int
    fun initViews()
    fun initObservers()
    fun initData()
}