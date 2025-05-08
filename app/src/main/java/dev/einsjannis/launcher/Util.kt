package dev.einsjannis.launcher



fun <T: U, U> List<T>.insertBetween(f: (prev: T?, next: T?) -> U?): List<U> {
    return buildList(this.size) {
        var prev: T? = null
        for (element in this@insertBetween) {
            f(prev, element)?.also { addLast(it) }
            addLast(element)
            prev = element
        }
        f(prev, null)?.also { addLast(it) }
    }
}