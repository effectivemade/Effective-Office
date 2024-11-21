package office.effective.workspace

import junit.framework.TestCase.assertEquals
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.features.workspace.service.WorkspaceService
import office.effective.model.Workspace
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.*

class WorkspaceServiceTest {
    @Mock
    private lateinit var mockRepository: WorkspaceRepository

    private lateinit var workspaceService: WorkspaceService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        workspaceService = WorkspaceService(mockRepository)
    }

    @Test
    fun testFindById() {
        val workspaceId = UUID.randomUUID()
        val workspace = Workspace(workspaceId, "Workspace Name", "Tag", emptyList())

        whenever(mockRepository.findById(workspaceId)).thenReturn(workspace)

        val result = workspaceService.findById(workspaceId)

        assertEquals(workspace, result)
    }

    @Test
    fun testFindAllByTag() {
        val tag = "Tag"
        val workspaces = listOf(
            Workspace(UUID.randomUUID(), "Workspace 1", tag, emptyList()),
            Workspace(UUID.randomUUID(), "Workspace 2", tag, emptyList())
        )

        whenever(mockRepository.findAllByTag(tag)).thenReturn(workspaces)

        val result = workspaceService.findAllByTag(tag)

        assertEquals(workspaces, result)
    }
}
