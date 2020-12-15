package com.qwert2603.eten.util


fun <T> List<T>.mapIf(condition: (T) -> Boolean, mapper: (T) -> T): List<T> = this
    .map {
        if (condition(it)) {
            mapper(it)
        } else {
            it
        }
    }

fun <T> List<T>.replaceIf(condition: (T) -> Boolean, newItem: T): List<T> = this
    .map {
        if (condition(it)) {
            newItem
        } else {
            it
        }
    }

inline fun <T, reified R : T> List<T>.mapIfTyped(
    condition: (T) -> Boolean,
    mapper: (R) -> T,
): List<T> = this
    .map {
        if (it is R && condition(it)) {
            mapper(it)
        } else {
            it
        }
    }