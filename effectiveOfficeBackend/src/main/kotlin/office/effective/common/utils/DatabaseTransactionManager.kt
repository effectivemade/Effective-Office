package office.effective.common.utils;

import org.ktorm.database.TransactionIsolation;

/**
 * Interface for classes used for creation database transaction on the facade layer
 */
interface DatabaseTransactionManager {
    /**
     * Executes code in a database transaction.
     *
     * Rollbacks the transaction if an exception was thrown.
     *
     * @param serviceCall lambda function to be executed
     * @param isolation transaction isolation
     */
    fun <T> useTransaction(serviceCall: () -> T, isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): T
}
