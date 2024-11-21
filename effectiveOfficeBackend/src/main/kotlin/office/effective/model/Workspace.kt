package office.effective.model

import java.util.*

data class Workspace(
    var id: UUID?,
    var name: String,
    var tag: String,
    var utilities: List<Utility>,
    var zone: WorkspaceZone? = null
)
