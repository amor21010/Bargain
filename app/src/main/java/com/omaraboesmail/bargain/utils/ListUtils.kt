package com.omaraboesmail.bargain.utils

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>.checkItemsAre() =
    if (all { it is T })
        this as List<T>
    else ArrayList()

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> ArrayList<*>.checkItemsAre() =
    if (all { it is T })
        this as ArrayList<T>
    else ArrayList()
