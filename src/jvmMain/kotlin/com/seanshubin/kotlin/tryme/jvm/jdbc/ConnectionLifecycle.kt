package com.seanshubin.kotlin.tryme.jvm.jdbc

import java.sql.ResultSet

interface ConnectionLifecycle {
    fun <T> withResultSet(sqlQuery: String, doSomethingWithResultSet: (ResultSet) -> T): T
}
