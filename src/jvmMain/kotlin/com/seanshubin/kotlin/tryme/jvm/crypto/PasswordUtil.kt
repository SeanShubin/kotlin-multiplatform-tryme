package com.seanshubin.kotlin.tryme.jvm.crypto

class PasswordUtil(private val uniqueIdGenerator: UniqueIdGenerator, private val oneWayHash: OneWayHash) {
    fun createSaltAndHash(password: String): SaltAndHash =
        SaltAndHash.fromPassword(password, uniqueIdGenerator, oneWayHash)

    fun validatePassword(password: String, saltAndHash: SaltAndHash): Boolean =
        SaltAndHash.validate(password, saltAndHash, oneWayHash)
}
