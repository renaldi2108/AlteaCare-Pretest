package id.renaldi.alteacarepretest.data.network.service

import id.renaldi.alteacarepretest.data.network.config.DOCTOR_LIST
import id.renaldi.alteacarepretest.data.repository.pojo.Doctor
import id.renaldi.alteacarepretest.utility.base.BaseResponseList
import retrofit2.Response
import retrofit2.http.GET

interface DoctorService {
    @GET(DOCTOR_LIST)
    suspend fun getDoctorList(): Response<BaseResponseList<Doctor>>
}