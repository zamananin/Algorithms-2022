@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6

import lesson6.impl.GraphBuilder

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */

/**
 * Для построения минимального остовного дерева с v вершинами
 * необходимо и достаточно v - 1 ребер. В противном случае в графе имеются циклы
 * Если удалить ребро в цикле, то граф останется связным
 */
fun Graph.minimumSpanningTree(): Graph {
    if (this.vertices.size < 2) return this
    // Требуется создать копию существующего графа, из которой будут удаляться ребра без ущерба для исходного
    var graph = buildGraph(vertices, edges)
    while (graph.edges.size > graph.vertices.size - 1) {
        // В качестве результата поиска достаточно найти всего ребро, включенное в цикл
        val e = findCycle(graph)
        graph = buildGraph(vertices, graph.edges.minus(e))
    }
    return graph
}

private fun buildGraph(vertices: Set<Graph.Vertex>, edges: Set<Graph.Edge>) =
    GraphBuilder().apply {
        for (v in vertices)
            addVertex(v.name)
        for (e in edges) {
            addConnection(e.begin, e.end)
        }
    }.build()

// В качестве решения используется поиск вглубину до первого обратного ребра
// Обратное ребро - ребро, содиняющее текущую вершину с одной из посещенных ранее
// Заметим, что наличие такого ребра гарантированно при поиске из любой вершины
private fun findCycle(graph: Graph): Graph.Edge {
    val visited = mutableSetOf<Graph.Vertex>()
    val start = graph.vertices.iterator().next()
    visited.add(start)
    return dfs(graph, visited, start, start)!!
}

private fun dfs(
    graph: Graph,
    visited: MutableSet<Graph.Vertex>,
    actual: Graph.Vertex,
    previous: Graph.Vertex
): Graph.Edge? {
    val neighbors = graph.getNeighbors(actual)
    // Чтобы не идти глубже, чем надо, на каждом шаге проверяем, нет ли соседей в visited
    if (actual != previous) {
        for (v in neighbors) {
            if (v != previous && v in visited)
                return graph.getConnection(actual, previous)
        }
    }
    for (v in neighbors) {
        if (v != previous) {
            visited.add(v)
            val result = dfs(graph, visited, v, actual)
            if (result != null) return result
        }
    }
    return null
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    TODO()
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */

/**
 * В общем случае задача решается только полным перебором,
 * следовательно трудоекость составляет O(V * (V + E))
 * Для решения этой задачи используется поиск в глубину
 */

fun Graph.longestSimplePath(): Path {
    var longestPath = Path()
    for (vertex in this.vertices) { // O(V)
        val path = recursiveSearchLongest(this, vertex)
        if (path.length > longestPath.length) longestPath = path
    }
    return longestPath
}

private fun recursiveSearchLongest(graph: Graph, vertex: Graph.Vertex): Path {
    return recursiveSearchLongest(graph, vertex, Path(vertex))
}

private fun recursiveSearchLongest(graph: Graph, vertex: Graph.Vertex, actualPath: Path): Path {
    var longestPath = actualPath
    val visited = actualPath.vertices.toSet()
    for (v in graph.getNeighbors(vertex)) {
        if (v !in visited) {
            val path = recursiveSearchLongest(graph, v, Path(actualPath, graph, v))
            if (path.length > longestPath.length) longestPath = path
        }
    }
    return longestPath
}

/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}
