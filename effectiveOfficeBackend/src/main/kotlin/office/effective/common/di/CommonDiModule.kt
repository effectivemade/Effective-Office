package office.effective.common.di

import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.impl.DatabaseTransactionManagerImpl
import office.effective.common.utils.UuidValidator
import org.koin.dsl.module
import org.ktorm.database.Database

val commonDiModule = module(createdAtStart = true) {
    val host: String? = System.getenv("DATABASE_HOST")
    val port: String? = System.getenv("DATABASE_PORT")
    val databaseName: String? = System.getenv("DATABASE_NAME")

    val url: String = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName)
    val username: String? = System.getenv("DATABASE_USERNAME")
    val password: String? = System.getenv("DATABASE_PASSWORD")

    single<Database> {
        Database.connect(
            url = url,
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
    }
    single<DatabaseTransactionManager> { DatabaseTransactionManagerImpl(get()) }
    single { UuidValidator() }
}
