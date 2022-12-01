package id.renaldi.alteacarepretest.ui.activity.main

import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import dagger.hilt.android.AndroidEntryPoint
import id.renaldi.alteacarepretest.R
import id.renaldi.alteacarepretest.databinding.ActivityMainBinding
import id.renaldi.alteacarepretest.utility.base.BaseActivity
import java.util.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import id.renaldi.alteacarepretest.ui.activity.adapter.MainAdapter

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {
    override fun getLayoutResource(): Int = R.layout.activity_main

    lateinit var selectedHospital: BooleanArray
    lateinit var selectedSpecialization: BooleanArray
    lateinit var adapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun initViews(): Unit = with(binding) {
        viewModel.getDoctorList()

        selectedHospital = BooleanArray(viewModel.hospitalArray.size)
        selectedSpecialization = BooleanArray(viewModel.specializationArray.size)

        tvHospital.setOnClickListener {
            setupDialog("Select Hospitals").show()
        }
        tvSpecialization.setOnClickListener {
            setupDialog("Select Specialization").show()
        }
    }

    override fun initObservers(): Unit = with(viewModel) {
        data.observe(this@MainActivity) {
            adapter = MainAdapter(this@MainActivity, 0, it)
            binding.listView.adapter = adapter
        }

        isComplete.observe(this@MainActivity) {
            binding.progressBar.isVisible = it
        }

        dataFilter.observe(this@MainActivity) {
            adapter.setupList(it)
            Log.e("dataFilter", "${it.size}")
        }

        dataSearch.observe(this@MainActivity) {
            adapter.setupList(it)
        }
    }

    private fun setupDialog(title: String) = AlertDialog.Builder(this@MainActivity).setTitle(title).setCancelable(false)
        .setMultiChoiceItems(if(title == "Select Hospitals") viewModel.hospitalArray else viewModel.specializationArray, if(title == "Select Hospitals") selectedHospital else selectedSpecialization) { _, i, b ->
            viewModel.selectedAdd(b, i, title)
        }.setPositiveButton("OK") { _, _ ->
            clearAll(title != "Select Hospitals")
            viewModel.filterBy(viewModel.currentData, if(title == "Select Hospitals") viewModel.hospitalList else viewModel.specializationList, title)
        }.setNegativeButton("Cancel") {
                dialogInterface, _ -> dialogInterface.dismiss()
        }.setNeutralButton("Clear All") { _, _ ->
            clearAll(title == "Select Hospitals")
        }

    private fun clearAll(type: Boolean) {
        val sizeList = if(type) selectedHospital.indices else selectedSpecialization.indices
        for (j in sizeList) {
            if(type) {
                selectedHospital[j] = false
                viewModel.hospitalList.clear()
            } else {
                selectedSpecialization[j] = false
                viewModel.specializationList.clear()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search = menu.findItem(R.id.searchBar)

        (search.actionView as SearchView).apply {
            queryHint = "Keyword"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(keyword: String?): Boolean {
                    viewModel.searchBy(viewModel.currentData, keyword!!)
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }
}