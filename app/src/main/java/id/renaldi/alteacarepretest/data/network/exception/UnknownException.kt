package id.renaldi.alteacarepretest.data.network.exception

class UnknownException : Exception() {

    override val message: String?
        get() = "Some Unknown Error Occurred"
}