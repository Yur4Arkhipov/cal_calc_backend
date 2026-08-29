package com.jacqulin.feature.usage.data.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(
    block: suspend Transaction.() -> T
): T =
    newSuspendedTransaction(
        Dispatchers.IO
    ) {
        block()
    }