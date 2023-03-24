import java.io.File

fun parseInputFile(file: File): Pair<ArrayList<Process>, Int> {
    var resultNumberOfTicks = 0
    val list = arrayListOf<Process>().apply {
        file.forEachLine {
            it.split(" ").let {
                add(
                    Process(
                        name = it[0],
                        numberOfTicks = it[1].toInt()
                    ).let {
                        resultNumberOfTicks += it.numberOfTicks
                        it
                    }
                )
            }
        }
    }
    return Pair(list, resultNumberOfTicks)
}

fun main() {
    val numberOfActualTicks: Int
    var numberOfWaitTicks = 0
    var numberOfAbstractTicks = 0
    val processList = parseInputFile(
//        File("test1")
//        File("test2")
        File("test3")
    ).let {
        numberOfActualTicks = it.second
        it.first
    }

    val ticks = arrayListOf<String>()
    repeat(numberOfActualTicks) {
        processList.forEach { process ->
            if (process.ticksLeft > 0) {
                process.ticksLeft--
                ticks.add(process.name)
            }
        }
    }

    processList.forEach { process ->
        print(
            "${process.name} ${process.numberOfTicks} "
        )
        ticks.subList(0, ticks.indexOfLast { it.contains(process.name) } + 1).forEach {
            print(it.contains(process.name).let {
                if (!it) numberOfWaitTicks++
                numberOfAbstractTicks++
                if (it) "И" else "Г"
            })
        }
        println()
    }

//    println(ticks)
    println("Efficiency: ${"%.${2}f".format((numberOfWaitTicks.toFloat() / numberOfAbstractTicks * 100))}%")
}

data class Process(
    val name: String,
    val numberOfTicks: Int,
    var ticksLeft: Int = numberOfTicks
)