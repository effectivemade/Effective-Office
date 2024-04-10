package band.effective.office.workTogether

interface WorkTogether {
    fun getAll(): List<Teammate>
    fun getActive(): List<Teammate>
    fun getProperty(name: String): Map<String, String?>
}