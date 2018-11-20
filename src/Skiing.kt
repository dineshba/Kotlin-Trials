import java.util.Arrays.asList

fun main(args: Array<String>) {
    val mountain: List<List<Int>> = asList(
            asList(4, 8, 7, 3),
            asList(2, 5, 9, 3),
            asList(6, 3, 2, 5),
            asList(4, 4, 1, 6)
    )
    val nodes: List<Node> = createMap(mountain).flatten().toList()
    calculateMaxLengthAndMaxSteep(nodes)
    val maxLength = nodes.maxBy { it.length }?.length ?: 0
    val topNode = nodes.asSequence().filter { it.length == maxLength }.maxBy { it.value }
    println("answer: length: ${topNode?.length} path: ${topNode?.toAnswerString()}, steep: ${topNode?.getSteep()}")
}

private fun calculateMaxLengthAndMaxSteep(nodes: List<Node>) {
    nodes.forEach {
        if (!it.reached) {
            val nearByNodes = it.getAllNearByNodesWithLessValue()
            it.reached = true
            if (nearByNodes.isEmpty()) {
                it.length = 1
                it.path = null
            } else {
                val notReachedNodes = nearByNodes.filter { !it.reached }
                calculateMaxLengthAndMaxSteep(notReachedNodes)
                val maxBy = nearByNodes.maxBy { it.length }!!
                it.path = maxBy
                it.length = maxBy.length + 1
            }
        }
    }
}

fun createMap(mountain: List<List<Int>>): List<List<Node>> {
    val map: List<List<Node>> = mountain.map { a ->
        val row = a.map { Node(value = it) }
        a.withIndex().forEach { it ->
            row.getOrNull(it.index)?.right = row.getOrNull(it.index + 1)
        }
        a.withIndex().forEach { it ->
            row.getOrNull(it.index)?.left = row.getOrNull(it.index - 1)
        }
        row
    }
    map.withIndex().forEach { row ->
        row.value.withIndex().forEach { it.value.top = map.getOrNull(row.index - 1)?.getOrNull(it.index) }
    }
    map.withIndex().forEach { row ->
        row.value.withIndex().forEach { it.value.bottom = map.getOrNull(row.index + 1)?.getOrNull(it.index) }
    }
    return map
}


data class Node(val value: Int) {
    var top: Node? = null
    var bottom: Node? = null
    var left: Node? = null
    var right: Node? = null
    var reached = false
    var length: Int = 0
    var path: Node? = null

    override fun toString(): String {
        return "Node(value=$value, top=${top?.value}, bottom=${bottom?.value}, left=${left?.value}, right=${right?.value}, length=$length, path=$path)"
    }

    fun getAllNearByNodesWithLessValue(): List<Node> {
        return asList(top, bottom, left, right).asSequence().filterNotNull().filter { it.value < value }.toList()
    }

    fun toAnswerString(): String {
        return "$value -> ${path?.toAnswerString()}"
    }

    fun getSteep(): Int {
        var bottomNode: Node = this
        while (bottomNode.path != null) {
            bottomNode = bottomNode.path as Node
        }
        return this.value - (bottomNode.value)
    }
}