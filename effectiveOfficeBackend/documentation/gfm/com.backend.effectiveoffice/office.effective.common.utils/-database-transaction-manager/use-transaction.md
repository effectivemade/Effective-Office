//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.utils](../index.md)/[DatabaseTransactionManager](index.md)/[useTransaction](use-transaction.md)

# useTransaction

[jvm]\
abstract fun &lt;[T](use-transaction.md)&gt; [useTransaction](use-transaction.md)(serviceCall: () -&gt; [T](use-transaction.md), isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): [T](use-transaction.md)

Executes code in a database transaction.

Rollbacks the transaction if an exception was thrown.

#### Parameters

jvm

| | |
|---|---|
| serviceCall | lambda function to be executed |
| isolation | transaction isolation |
