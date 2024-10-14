package band.effective.office.tv.repository.workTogether

interface WorkTogether {
    fun getAll(): List<Teammate>

    fun getProperty(name: String): Map<String, String?>
}