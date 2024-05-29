package office.effective.workspace

import junit.framework.TestCase.assertEquals
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.impl.DatabaseTransactionManagerImpl
import office.effective.common.utils.UuidValidator
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.dto.WorkspaceDTO
import office.effective.features.workspace.facade.WorkspaceFacade
import office.effective.features.workspace.service.WorkspaceService
import office.effective.model.Workspace
import org.junit.Before
import org.junit.Test
import org.ktorm.database.TransactionIsolation
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.assertFailsWith


class WorkspaceFacadeTest {
    @Mock
    private lateinit var mockService: WorkspaceService
    @Mock
    private lateinit var mockConverter: WorkspaceFacadeConverter
    @Mock
    private lateinit var mockTransactionManager: DatabaseTransactionManagerImpl
    @Mock
    private lateinit var mockUuidValidator: UuidValidator

    private lateinit var workspaceFacade: WorkspaceFacade

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        workspaceFacade = WorkspaceFacade(mockService, mockConverter, mockTransactionManager, mockUuidValidator)
    }

    private fun setUpMockService(workspace: Workspace?) {
        whenever(mockService.findById(anyOrNull())).thenReturn(workspace)
    }

    private fun setUpMockConverter(workspace: Workspace, workspaceDTO: WorkspaceDTO) {
        whenever(mockConverter.modelToDto(workspace)).thenReturn(workspaceDTO)
    }

    private fun setUpMockTransactionManager() {
        whenever(mockTransactionManager.useTransaction<Workspace?>(
            anyOrNull(),
            eq(TransactionIsolation.READ_COMMITTED))
        ).thenAnswer { invocation: InvocationOnMock ->
            @Suppress("UNCHECKED_CAST")
            val lambda = invocation.arguments[0] as () -> Workspace?
            lambda()
        }
    }

    @Test
    fun testFindById() {
        val workspaceId = UUID.randomUUID()
        val existingWorkspace = Workspace(workspaceId, "Workspace 1", "Tag", emptyList())
        val expectedWorkspaceDTO = WorkspaceDTO(workspaceId.toString(), "Workspace 1", emptyList(), null, "Tag")

        setUpMockTransactionManager()
        setUpMockService(existingWorkspace)
        setUpMockConverter(existingWorkspace, expectedWorkspaceDTO)

        val result = workspaceFacade.findById(workspaceId.toString())

        assertEquals(expectedWorkspaceDTO, result)
    }

    @Test
    fun testFindByIdWithNonExistingWorkspace() {
        val workspaceId = UUID.randomUUID()

        setUpMockTransactionManager()
        setUpMockService(null)

        assertFailsWith<InstanceNotFoundException> {
            workspaceFacade.findById(workspaceId.toString())
        }
    }

    @Test
    fun testFindAllByTag() {
        val workspace1Id = UUID.randomUUID()
        val workspace2Id = UUID.randomUUID()
        val existingList = listOf(
            Workspace(workspace1Id, "Workspace 1", "Tag", emptyList()),
            Workspace(workspace2Id, "Workspace 2", "Tag", emptyList())
        )
        val expectedList = listOf(
            WorkspaceDTO(workspace1Id.toString(), "Workspace 1", emptyList(), null, "Tag"),
            WorkspaceDTO(workspace2Id.toString(), "Workspace 2", emptyList(), null, "Tag")
        )

        setUpMockTransactionManager()
        whenever(mockService.findAllByTag(anyOrNull())).thenReturn(existingList)
        whenever(mockConverter.modelToDto(anyOrNull())).thenReturn(expectedList[0], expectedList[1])

        val result = workspaceFacade.findAllByTag("tag")

        assertEquals(expectedList, result)
    }
}
