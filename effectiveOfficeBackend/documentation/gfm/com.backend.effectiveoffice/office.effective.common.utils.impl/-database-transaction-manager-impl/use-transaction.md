//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.utils.impl](../index.md)/[DatabaseTransactionManagerImpl](index.md)/[useTransaction](use-transaction.md)

# useTransaction

[jvm]\
open override fun &lt;[T](use-transaction.md)&gt; [useTransaction](use-transaction.md)(serviceCall: () -&gt; [T](use-transaction.md), isolation: TransactionIsolation): [T](use-transaction.md)

Executes code in a database transaction.

Rollbacks the transaction if an exception was thrown.

#### Parameters

jvm

| | |
|---|---|
| serviceCall | lambda function to be executed |
| isolation | transaction isolation |
