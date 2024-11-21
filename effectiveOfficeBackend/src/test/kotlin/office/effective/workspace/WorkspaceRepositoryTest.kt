package office.effective.workspace

import junit.framework.TestCase.assertEquals
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import office.effective.config
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.repository.*
import office.effective.model.Utility
import office.effective.model.Workspace
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.add
import org.ktorm.entity.find
import java.sql.DriverManager
import java.util.UUID

class WorkspaceRepositoryTest {
    private val url: String = config.propertyOrNull("test.database.url")?.getString()
        ?: "jdbc:h2:mem:effectiveOfficeBackendD;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH"

    private val changelogFile: String = config.propertyOrNull("liquibase.changelogFile")
        ?.getString() ?: "changelog/changelog-master.yaml"

    private val db = Database.connect(url = url, driver = "org.h2.Driver")
    private lateinit var liquibase: Liquibase
    private val repository = WorkspaceRepository(db, WorkspaceRepositoryConverter(db))

    @Before
    fun setUp() {
        val connection = DriverManager.getConnection(url)
        val databaseConnection = JdbcConnection(connection)
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection)
        liquibase = Liquibase(changelogFile, ClassLoaderResourceAccessor(), database)
        liquibase.update("")
        val workspaceEntity = WorkspaceEntity {
            id = UUID.fromString("8896abc1-457b-41e4-b80b-2fe7cfb3dbaf")
            name = "Some name"
            tag = db.workspaceTags.find { it.name eq "meeting" }!!
        }
        val utilityEntity = UtilityEntity {
            id = UUID.fromString("9896abc1-457b-41e4-b80b-2fe7cfb3dbaf")
            name = "Utility"
            iconUrl = "url"
        }
        db.workspaces.add(workspaceEntity)
        db.utilities.add(utilityEntity)
        db.insert(WorkspaceUtilities) {
            set(it.workspaceId, workspaceEntity.id)
            set(it.utilityId, utilityEntity.id)
            set(it.count, 2)
        }
    }

    @Test
    fun testFindById() {
        val workspace = repository.findById(UUID.fromString("8896abc1-457b-41e4-b80b-2fe7cfb3dbaf"))
        val expectedWorkspace = Workspace(
            UUID.fromString("8896abc1-457b-41e4-b80b-2fe7cfb3dbaf"),
            "Some name",
            "meeting",
            listOf(Utility(
                UUID.fromString("9896abc1-457b-41e4-b80b-2fe7cfb3dbaf"),
                "Utility",
                "url",
                2
            ))
        )
        assertEquals(expectedWorkspace, workspace)
    }

    @After
    fun after() {
        liquibase.database.close()
    }
}