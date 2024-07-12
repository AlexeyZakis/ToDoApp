package com.example.todoapp.data.repository

fun <T> Set<T>.intersectWithComparator(other: Set<T>, comparator: (T, T) -> Boolean): Set<T> {
    return this.filter { item1 -> other.any { item2 -> comparator(item1, item2) } }.toSet()
}

fun <T> Set<T>.minusWithComparator(other: Set<T>, comparator: (T, T) -> Boolean): Set<T> {
    return this.filterNot { item1 -> other.any { item2 -> comparator(item1, item2) } }.toSet()
}
