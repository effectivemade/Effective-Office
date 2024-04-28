//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.utils.impl](../index.md)/[DatabaseTransactionManagerImpl](index.md)

# DatabaseTransactionManagerImpl

[jvm]\
class [DatabaseTransactionManagerImpl](index.md)(database: Database) : [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md)

Class used for creation database transaction on the facade layer

## Constructors

| | |
|---|---|
| [DatabaseTransactionManagerImpl](-database-transaction-manager-impl.md) | [jvm]<br>constructor(database: Database) |

## Functions

| Name | Summary |
|---|---|
| [useTransaction](use-transaction.md) | [jvm]<br>open override fun &lt;[T](use-transaction.md)&gt; [useTransaction](use-transaction.md)(serviceCall: () -&gt; [T](use-transaction.md), isolation: TransactionIsolation): [T](use-transaction.md)<br>Executes code in a database transaction. |
