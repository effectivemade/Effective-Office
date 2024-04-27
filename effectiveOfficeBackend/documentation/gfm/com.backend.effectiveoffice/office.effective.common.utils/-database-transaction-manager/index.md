//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.utils](../index.md)/[DatabaseTransactionManager](index.md)

# DatabaseTransactionManager

interface [DatabaseTransactionManager](index.md)

Interface for classes used for creation database transaction on the facade layer

#### Inheritors

| |
|---|
| [DatabaseTransactionManagerImpl](../../office.effective.common.utils.impl/-database-transaction-manager-impl/index.md) |

## Functions

| Name | Summary |
|---|---|
| [useTransaction](use-transaction.md) | [jvm]<br>abstract fun &lt;[T](use-transaction.md)&gt; [useTransaction](use-transaction.md)(serviceCall: () -&gt; [T](use-transaction.md), isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): [T](use-transaction.md)<br>Executes code in a database transaction. |
