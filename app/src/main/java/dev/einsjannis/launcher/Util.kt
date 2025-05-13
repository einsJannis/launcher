package dev.einsjannis.launcher



fun <T: U, U> List<T>.insertBetween(f: (prev: T?, next: T?) -> U?): List<U> {
    return buildList(this.size) {
        for (element in this@insertBetween) {
            // SAFETY: at the end of each iteration we set the last element to one of type T and
            //         in the beginning the list is empty
            f(lastOrNull() as T?, element)?.also { addLast(it) }
            addLast(element)
        }
        // SAFETY: at the end of each iteration we set the last element to one of type T and
        //         in the beginning the list is empty
        f(lastOrNull() as T?, null)?.also { addLast(it) }
    }
}