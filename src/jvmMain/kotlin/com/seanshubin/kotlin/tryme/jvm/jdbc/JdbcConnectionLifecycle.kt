package com.seanshubin.kotlin.tryme.jvm.jdbc

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class JdbcConnectionLifecycle(
    private val url: String,
    private val user: String,
    private val password: String
) : ConnectionLifecycle {
    override fun <T> withResultSet(sqlQuery: String, doSomethingWithResultSet: (ResultSet) -> T): T =
        withPreparedStatement(sqlQuery) { statement ->
            val resultSet = statement.executeQuery()
            resultSet.use(doSomethingWithResultSet)
        }

    private fun <T> withPreparedStatement(
        sqlQuery: String,
        doSomethingWithPreparedStatement: (PreparedStatement) -> T
    ): T =
        withConnection { connection ->
            val statement = connection.prepareStatement(sqlQuery)
            statement.use(doSomethingWithPreparedStatement)
        }

    private fun <T> withConnection(doSomethingWithConnection: (Connection) -> T): T {
        val connection = DriverManager.getConnection(url, user, password)
        return connection.use(doSomethingWithConnection)
    }
}
