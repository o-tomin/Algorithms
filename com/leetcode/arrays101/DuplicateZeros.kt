package com.leetcode.arrays101

fun main() {
    val arr = arrayListOf(
        1,  //0
        0,  //1 <-
        2,  //2
        3,  //3
        0,  //4 <-
        4,  //5
        5,  //6
        0,  //7 <-
    ).toIntArray()

    duplicateZeros(arr)

    println(arr.toList())
}


fun duplicateZeros(arr: IntArray) = arr
    // 1. Find indexes of 0 values.
    .mapIndexed(::Pair)
    .filter { it.second == 0 }
    .map { it.first }
    // 2. Calculate shifts for zeros
    .mapIndexed { index, value -> value to (value + index) }
    .filter { it.second < arr.size }
    .toMutableList()
    .also { it += arr.size.asIndex to arr.size.asIndex }
    // 3. Combine old values indexes ranges with new ones based on shifted indexes of zeros
    .zipWithNext()
    .map { (previous, next) -> (previous.first.previousIndex to next.first) to (previous.second to next.second) }
    .asReversed()
    // 4. Duplicate zeros and shift values to new positions
    .forEach { (oldRange, newRange) ->
        (oldRange.first..oldRange.second).zip(newRange.first..newRange.second).apply {
            asReversed().forEachIndexed { index, (old, new) ->
                arr.duplicateZeros(index, size, old, new)
            }
        }
    }

fun IntArray.duplicateZeros(
    rangeIndex: Int,
    rangeSize: Int,
    oldArrayIndex: Int,
    newArrayIndex: Int
) {
    if (rangeIndex in rangeSize.asIndex.previousIndex .. rangeSize.asIndex) {
        this[newArrayIndex] = 0
    } else {
        this[newArrayIndex] = this[oldArrayIndex]
    }
}

val Int.asIndex
    get() = this - 1

val Int.previousIndex
    get() = this - 1
