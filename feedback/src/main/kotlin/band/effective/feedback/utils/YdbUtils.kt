package band.effective.feedback.utils

import tech.ydb.auth.iam.CloudAuthHelper
import tech.ydb.core.grpc.GrpcTransport
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.TableClient
import tech.ydb.table.query.DataQueryResult
import tech.ydb.table.result.ResultSetReader
import tech.ydb.table.transaction.TxControl

fun createSessionRetryContext(authorizedKeyJson: String, connectionString: String): SessionRetryContext {
    val transport: GrpcTransport =
        GrpcTransport.forConnectionString(connectionString)
            .withAuthProvider(CloudAuthHelper.getServiceAccountJsonAuthProvider(authorizedKeyJson))
            .build()
    val tabletClient = TableClient.newClient(transport).build()
    return SessionRetryContext.create(tabletClient).build()
}

fun SessionRetryContext.executeQuery(query: String): DataQueryResult {
    val txControl = TxControl.serializableRw().setCommitTx(true)
    return supplyResult { session ->
        session.executeDataQuery(query, txControl)
    }.join().value
}

fun <T> DataQueryResult.getAll(builder: ResultSetReader.() -> T): List<T> {
    val result = mutableListOf<T>()
    getResultSet(0).apply {
        while (next()) {
            result.add(builder())
        }
    }
    return result
}

fun <T> SessionRetryContext.getResultQuery(query: String, builder: ResultSetReader.() -> T) =
    executeQuery(query).getAll(builder)

fun SessionRetryContext.newId(tableName: String) =
    (getResultQuery("select * from $tableName") { getColumn("id").uint64 }.maxOrNull() ?: -1) + 1