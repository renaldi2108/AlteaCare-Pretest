package id.renaldi.alteacarepretest.data.repository

import id.renaldi.alteacarepretest.data.network.service.DoctorService
import id.renaldi.alteacarepretest.utility.base.BaseRepository
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    private val service: DoctorService,
): BaseRepository() {

    fun doctorList() = flowNetworkCall { service.getDoctorList() }

}