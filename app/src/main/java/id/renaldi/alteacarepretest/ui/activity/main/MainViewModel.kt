package id.renaldi.alteacarepretest.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.renaldi.alteacarepretest.data.repository.DoctorRepository
import id.renaldi.alteacarepretest.data.repository.pojo.Doctor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DoctorRepository
): ViewModel() {
    var hospitalList: ArrayList<String> = ArrayList()
    var hospitalArray = arrayOf("Mitra Keluarga Bintaro", "Mitra Keluarga Gading Serpong")

    var specializationList: ArrayList<String> = ArrayList()
    var specializationArray = arrayOf("Dokter Umum", "Kebidanan & Kandungan", "Kulit & Kelamin", "Jantung & Pem.Darah", "Bedah", "Penyakit Dalam", "Anak")

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete: LiveData<Boolean> = _isComplete

    private val _data = MutableLiveData<MutableList<Doctor>>()
    val data: LiveData<MutableList<Doctor>> = _data
    val currentData: MutableList<Doctor> = mutableListOf()

    private val _dataFilter = MutableLiveData<MutableList<Doctor>>()
    val dataFilter: LiveData<MutableList<Doctor>> = _dataFilter

    private val _dataSearch = MutableLiveData<MutableList<Doctor>>()
    val dataSearch: LiveData<MutableList<Doctor>> = _dataSearch

    fun filterBy(from: MutableList<Doctor>, to: MutableList<String>, type: String) {
        _dataFilter.value = if(to.size>0) from.filter {
            to.any { keyword -> keyword == if(type == "Select Hospitals") it.hospital[0].name else it.specialization.name }
        }.toMutableList() else from
    }

    fun searchBy(from: MutableList<Doctor>, keyword: String){
        _dataSearch.value = if(keyword.isNotEmpty())from.filter { it.name.toLowerCase().contains(keyword.toLowerCase()) }.toMutableList() else from
    }

    fun selectedAdd(checked: Boolean, index: Int, type: String) {
        if(type == "Select Hospitals") {
            if (checked) hospitalList.add(hospitalArray[index])
            else hospitalList.remove(hospitalArray[index])
        } else {
            if (checked) specializationList.add(specializationArray[index])
            else specializationList.remove(specializationArray[index])
        }
    }

    fun getDoctorList() = repository.doctorList()
        .onStart {
            _isComplete.value = true
        }
        .onCompletion {
            _isComplete.value = false
        }
        .onEach {
            _data.value = it.data
            currentData.addAll(it.data)
        }.launchIn(viewModelScope)
}