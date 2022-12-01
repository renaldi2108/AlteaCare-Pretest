package id.renaldi.alteacarepretest.utility.base

open class APIResponse(
    open val status: String = "",
    open val message: String = "",
)

open class BaseResponseList<T>(
    var data: MutableList<T>,
    status: String = "",
    message: String = "",
): APIResponse(status, message)