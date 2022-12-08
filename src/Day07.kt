fun main() {
    data class File(val name: String, val size: Int)
    data class Folder(val name: String, val files: MutableList<File>, val folders: MutableList<Folder>, val parentFolder: Folder?)

    fun calculateFolderSize(folder: Folder): Int{
        val size = folder.files.sumOf { it.size } + folder.folders.sumOf { calculateFolderSize(it) }



        return size
    }

    fun flatnFolder(folder: Folder): MutableList<Folder>{
        val result = folder.folders.toMutableList()

        folder.folders.forEach { result.addAll(flatnFolder(it)) }

        return result
    }

    fun getFoldersFromInput(input: List<String>): List<Pair<Folder, Int>> {
        var currentFolder = Folder("/", mutableListOf(), mutableListOf(), null)
        val rootFolder = currentFolder

        input.forEach { line ->
            val splitted = line.split(" ")

            if (splitted[0] == "$") {
                if (splitted[1] == "cd") {
                    if (splitted[2].startsWith("/")) {
                        currentFolder = rootFolder
                    }

                    val dirs = splitted[2].trim().split("/")

                    dirs.filter { it.isNotEmpty() && it.isNotBlank() }.forEach { dir ->
                        currentFolder = if (dir == "..") {
                            val parentFolder = currentFolder.parentFolder
                            parentFolder ?: currentFolder
                        } else {
                            if (!currentFolder.folders.any { it.name == dir }) {
                                currentFolder.folders.add(Folder(dir, mutableListOf(), mutableListOf(), currentFolder))
                            }

                            currentFolder.folders.first { it.name == dir }
                        }
                    }
                }
            } else if (splitted[0].toIntOrNull() != null) {
                currentFolder.files.add(File(splitted[1], splitted[0].toInt()))
            }
        }

        val allFolders = flatnFolder(rootFolder)
        allFolders.add(rootFolder)

        return allFolders.map { Pair(it, calculateFolderSize(it)) }
    }

    fun part1(input: List<String>): Int {
        return getFoldersFromInput(input).filter { it.second <= 100000 }.sumOf { it.second }
    }

    fun part2(input: List<String>): Int {
        val folders = getFoldersFromInput(input)
        val totalSize = folders.first { it.first.name == "/" }.second
        val spaceToFreeUp = 30000000 - 70000000 + totalSize
        return folders.filter { it.second >= spaceToFreeUp }.minOf { it.second }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

     val input = readInput("Day07")
     println(part1(input))
     println(part2(input))
}
