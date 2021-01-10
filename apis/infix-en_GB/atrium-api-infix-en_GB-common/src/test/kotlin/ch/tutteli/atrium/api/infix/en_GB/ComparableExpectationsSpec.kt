package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.specs.fun1
import ch.tutteli.atrium.specs.integration.DiffEqualsCompareTo
import ch.tutteli.atrium.specs.notImplemented
import ch.tutteli.atrium.specs.testutils.WithAsciiReporter

class ComparableExpectationsSpec : ch.tutteli.atrium.specs.integration.ComparableExpectationsSpec(
    fun1(Expect<Int>::isLessThan),
    fun1(Expect<Int>::isLessThanOrEqual),
    fun1(Expect<Int>::isGreaterThan),
    fun1(Expect<Int>::isGreaterThanOrEqual),
    fun1(Expect<Int>::isEqualComparingTo),
    fun1(Expect<DiffEqualsCompareTo>::isEqualComparingTo)
) {

    companion object : WithAsciiReporter()

    @Suppress("unused")
    fun ambiguityTest() {
        val a1: Expect<Int> = notImplemented()
        a1 isLessThan 1
        a1 isLessThanOrEqual 1
        a1 isGreaterThan 1
        a1 isGreaterThanOrEqual 1
        a1 isEqualComparingTo 1
    }
}
