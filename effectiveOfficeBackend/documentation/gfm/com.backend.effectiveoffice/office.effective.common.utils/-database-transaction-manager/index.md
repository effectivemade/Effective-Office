//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.utils](../index.md)/[DatabaseTransactionManager](index.md)

# DatabaseTransactionManager

[jvm]\
class [DatabaseTransactionManager](index.md)(database: Database)

Class used for creation database transaction on the facade layer

## Constructors

| | |
|---|---|
| [DatabaseTransactionManager](-database-transaction-manager.md) | [jvm]<br>constructor(database: Database) |

## Functions

| Name | Summary |
|---|---|
| [useTransaction](use-transaction.md) | [jvm]<br>fun &lt;[T](use-transaction.md)&gt; [useTransaction](use-transaction.md)(serviceCall: () -&gt; [T](use-transaction.md), isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): [T](use-transaction.md)<br>Executes code in a database transaction. |
