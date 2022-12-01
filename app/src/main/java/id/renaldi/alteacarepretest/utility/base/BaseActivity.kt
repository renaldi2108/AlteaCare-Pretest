package id.renaldi.alteacarepretest.utility.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), IBaseView {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutResource())
        initViews()
        initObservers()

        if (savedInstanceState == null) {
            onNotSavedInstanceState()
        } else {
            onSavedInstanceState(savedInstanceState)
        }
    }

    protected open fun onNotSavedInstanceState() {
        initData()
    }

    protected open fun onSavedInstanceState(savedInstanceState: Bundle) {}

    override fun initViews() = Unit

    override fun initObservers() = Unit

    override fun initData() = Unit
}