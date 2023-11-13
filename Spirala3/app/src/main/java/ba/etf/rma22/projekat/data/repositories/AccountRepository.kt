package ba.etf.rma22.projekat.data.repositories

object AccountRepository {
    var acHash: String = "7835fd06-d070-4bc8-aeca-1812ca8e279e"

    fun postaviHash(acHash: String): Boolean {
        AccountRepository.acHash = acHash
        return true

        //trebat ce vratiti false u nekom slucaju za s4
    }

    fun getHash(): String = acHash
}