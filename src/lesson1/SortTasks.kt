@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.math.absoluteValue

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {

    fun myTemp(s: String): Int {
        val separated = s.split(".")
        if (separated.size != 2) {
            throw Exception("Incorrect data format")
        }
        return if (separated[0][0] == '-') 10 * separated[0].toInt() - separated[1].toInt()
        else 10 * separated[0].toInt() + separated[1].toInt()
    }

    val list = LinkedList<Int>() // Трудоемкость добавления в конец LinkedList равна O(1)
    for (line in File(inputName).readLines()) {
        list.add(myTemp(line))
        // Трудоемкость преобразования строки в число O(1), т.к. в диапазоне от -273.0 до +500.0. все строки ограниченной длинны
    }
    // Трудоемкость данного цикла O(n)

    val array = list.toIntArray()
    // Трудоемкость O(n)
    quickSort(array)
    // Трудоемкость O(n * log n)
    // Ресурсоемкость O(n * log n)

    File(outputName).bufferedWriter().use {
        for (el in array) {
            if (el < 0) it.write("-")
            it.write(((el / 10).absoluteValue).toString())
            it.write(".")
            it.write(((el % 10).absoluteValue).toString())
            it.newLine()
        }
    }
    //Трудоемкость O(n)
}
// В итоге трудоемкость составляет O(n * log n)
// Ресурсоемкость O(n * log n)

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    TODO()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */

fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    var firstPointer = 0
    var secondPointer = first.size
    var commonPointer = 0
    /**
     * first  = [4 9 15 20 28]
     *           ^ fP
     * second = [null null null null null 1 3 9 13 18 23]
     *           ^ cP                     ^ sP
     */
    while (commonPointer < second.size && second[commonPointer] == null) {
        /**
         *  убедились, что:
         *  1. common pointer в порядке (мб не в порядке, если раньше было second = [ ... ... ... null])
         *  2. есть еще элементы null <==> first не закончился
         */
        if (secondPointer < second.size && first[firstPointer] >= second[secondPointer]!!) {
            // выполнить если еще есть second и элемент из second меньше
            val t = second[secondPointer]
            second[secondPointer] = null
            second[commonPointer] = t
            secondPointer++
        } else {
            second[commonPointer] = first[firstPointer]
            firstPointer++

        }
        commonPointer++
    }
    // В худшем случае second.size итераций => O(second.size) (линейная сложность)
    // Ресурсоемкость O(1)
}

