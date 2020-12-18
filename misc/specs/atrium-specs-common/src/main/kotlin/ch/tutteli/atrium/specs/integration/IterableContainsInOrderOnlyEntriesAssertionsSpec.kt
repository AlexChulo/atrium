package ch.tutteli.atrium.specs.integration

import ch.tutteli.atrium.api.cc.en_GB.returnValueOf
import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.internal.expect
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.migration.asAssert
import ch.tutteli.atrium.domain.builders.migration.asExpect
import ch.tutteli.atrium.logic.utils.expectLambda
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.creating.ErrorMessages

abstract class IterableContainsInOrderOnlyEntriesAssertionsSpec(
    containsInOrderOnlyEntries: Fun2<Iterable<Double>, Expect<Double>.() -> Unit, Array<out Expect<Double>.() -> Unit>>,
    containsInOrderOnlyNullableEntries: Fun2<Iterable<Double?>, (Expect<Double>.() -> Unit)?, Array<out (Expect<Double>.() -> Unit)?>>,
    rootBulletPoint: String,
    successfulBulletPoint: String,
    failingBulletPoint: String,
    warningBulletPoint: String,
    listBulletPoint: String,
    explanatoryBulletPoint: String,
    featureArrow: String,
    featureBulletPoint: String,
    describePrefix: String = "[Atrium] "
) : IterableContainsEntriesSpecBase({

    include(object : SubjectLessSpec<Iterable<Double>>(
        describePrefix,
        containsInOrderOnlyEntries.forSubjectLess({ toBe(2.5) }, arrayOf())
    ) {})
    include(object : SubjectLessSpec<Iterable<Double?>>(
        "$describePrefix[nullable] ",
        containsInOrderOnlyNullableEntries.forSubjectLess(null, arrayOf())
    ) {})

    include(object : AssertionCreatorSpec<Iterable<Double>>(
        describePrefix, listOf(1.2, 2.0),
        *containsInOrderOnlyEntries.forAssertionCreatorSpec(
            "$toBeDescr: 1.2", "$toBeDescr: 2.0",
            { toBe(1.2) }, arrayOf(expectLambda { toBe(2.0) })
        )
    ) {})
    include(object : AssertionCreatorSpec<Iterable<Double?>>(
        "$describePrefix[nullable] ", listOf(1.2, 2.0) as Iterable<Double?>,
        *containsInOrderOnlyNullableEntries.forAssertionCreatorSpec(
            "$toBeDescr: 1.2", "$toBeDescr: 2.0",
            { toBe(1.2) }, arrayOf(expectLambda { toBe(2.0) })
        )
    ) {})

    fun Expect<Iterable<Double?>>.containsInOrderOnlyNullableEntriesFun(
        t: (Expect<Double>.() -> Unit)?,
        vararg tX: (Expect<Double>.() -> Unit)?
    ) = containsInOrderOnlyNullableEntries(this, t, tX)

    val indentBulletPoint = " ".repeat(rootBulletPoint.length)
    val indentSuccessfulBulletPoint = " ".repeat(successfulBulletPoint.length)
    val indentFailingBulletPoint = " ".repeat(failingBulletPoint.length)
    val indentFeatureArrow = " ".repeat(featureArrow.length)
    val indentFeatureBulletPoint = " ".repeat(featureBulletPoint.length)

    fun Expect<String>.elementSuccess(index: Int, actual: Any, expected: String): Expect<String> {
        return this.contains.exactly(1).regex(
            "\\Q$successfulBulletPoint$featureArrow${elementWithIndex(index)}: $actual\\E.*$separator" +
                "$indentBulletPoint$indentSuccessfulBulletPoint$indentFeatureArrow$featureBulletPoint$expected"
        )
    }

    fun Expect<String>.elementFailing(
        index: Int,
        actual: Any,
        expected: String,
        explaining: Boolean = false
    ): Expect<String> {
        return this.contains.exactly(1).regex(
            "\\Q$failingBulletPoint$featureArrow${elementWithIndex(index)}: $actual\\E.*$separator" +
                "$indentBulletPoint$indentFailingBulletPoint$indentFeatureArrow${if (explaining) "$indentFeatureBulletPoint$explanatoryBulletPoint" else featureBulletPoint}$expected"
        )
    }

    fun Expect<String>.elementNonExisting(index: Int, expected: String): Expect<String> {
        return this.contains.exactly(1).regex(
            "\\Q$failingBulletPoint$featureArrow${elementWithIndex(index)}: $sizeExceeded\\E.*$separator" +
                "$indentBulletPoint$indentFailingBulletPoint$indentFeatureArrow$indentFeatureBulletPoint$explanatoryBulletPoint$expected"
        )
    }

    nonNullableCases(
        describePrefix,
        containsInOrderOnlyEntries,
        containsInOrderOnlyNullableEntries
    ) { containsEntriesFunArr ->

        fun Expect<Iterable<Double>>.containsEntriesFun(
            t: Expect<Double>.() -> Unit,
            vararg tX: Expect<Double>.() -> Unit
        ) = containsEntriesFunArr(t, tX)

        context("empty collection") {
            it("$isLessThanFun(1.0) throws AssertionError") {
                expect {
                    expect(fluentEmpty()).containsEntriesFun({ isLessThan(1.0) })
                }.toThrow<AssertionError> {
                    message {
                        contains("$rootBulletPoint$containsInOrderOnly:")
                        elementNonExisting(0, "$isLessThanDescr: 1.0")
                        containsNot(additionalElements)
                        containsSize(0, 1)
                    }
                }
            }
            it("$isLessThanFun(1.0) and $isGreaterThanFun(4.0) throws AssertionError") {
                expect {
                    expect(fluentEmpty()).containsEntriesFun({ isLessThan(1.0) }, { isGreaterThan(4.0) })
                }.toThrow<AssertionError> {
                    message {
                        contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                        elementNonExisting(0, "$isLessThanDescr: 1.0")
                        elementNonExisting(1, "$isGreaterThanDescr: 4.0")
                        containsNot(additionalElements)
                        containsSize(0, 2)
                    }
                }
            }
            //TODO remove with 1.0.0
            it("$returnValueOfFun(...) states warning that subject is not set") {
                expect {
                    expect(fluentEmpty()).containsEntriesFun({
                        @Suppress("DEPRECATION")
                        asAssert().returnValueOf(subject::dec).asExpect().toBe(1.0)
                    })
                }.toThrow<AssertionError> {
                    @Suppress("DEPRECATION")
                    messageContains(ErrorMessages.SUBJECT_ACCESSED_TOO_EARLY.getDefault())
                }
            }
        }

        context("iterable ${oneToFour().toList()}") {

            context("happy case") {
                it("1.0, 2.0, 3.0, 4.0, 4.0") {
                    expect(oneToFour()).containsEntriesFun(
                        { toBe(1.0) },
                        { toBe(2.0) },
                        { toBe(3.0) },
                        { toBe(4.0) },
                        { toBe(4.0) })
                }
                it("$isLessThanFun(5.0), $isLessThanFun(5.0), $isLessThanFun(5.0), $isLessThanFun(5.0), $isLessThanFun(5.0)") {
                    expect(oneToFour()).containsEntriesFun(
                        { isLessThan(5.0) },
                        { isLessThan(5.0) },
                        { isLessThan(5.0) },
                        { isLessThan(5.0) },
                        { isLessThan(5.0) })
                }
            }

            context("error cases ... throws AssertionError") {

                it("$isLessThanFun(5.0), 1.0, 2.0, $isGreaterThanFun(2.0), 4.0 -- wrong order") {
                    expect {
                        expect(oneToFour()).containsEntriesFun(
                            { isLessThan(5.0) },
                            { toBe(1.0) },
                            { toBe(2.0) },
                            { isGreaterThan(2.0) },
                            { toBe(4.0) })
                    }.toThrow<AssertionError> {
                        message {
                            contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                            elementSuccess(0, 1.0, "$isLessThanDescr: 5.0")
                            elementFailing(1, 2.0, "$toBeDescr: 1.0")
                            elementFailing(2, 3.0, "$toBeDescr: 2.0")
                            elementSuccess(3, 4.0, "$isGreaterThanDescr: 2.0")
                            elementSuccess(4, 4.0, "$toBeDescr: 4.0")
                            containsSize(5, 5)
                        }
                    }
                }

                it("1.0, 2.0, 3.0, 4.0 -- 4.0 was missing") {
                    expect {
                        expect(oneToFour()).containsEntriesFun(
                            { toBe(1.0) },
                            { toBe(2.0) },
                            { toBe(3.0) },
                            { toBe(4.0) })
                    }.toThrow<AssertionError> {
                        message {
                            contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                            elementSuccess(0, 1.0, "$toBeDescr: 1.0")
                            elementSuccess(1, 2.0, "$toBeDescr: 2.0")
                            elementSuccess(2, 3.0, "$toBeDescr: 3.0")
                            elementSuccess(3, 4.0, "$toBeDescr: 4.0")
                            contains(
                                "$warningBulletPoint$additionalElements:",
                                "$listBulletPoint${elementWithIndex(4)}: 4.0"
                            )
                            containsSize(5, 4)
                        }
                    }
                }

                it("1.0, 4.0 -- 2.0, 3.0 and 4.0 was missing") {
                    expect {
                        expect(oneToFour()).containsEntriesFun({ toBe(1.0) }, { toBe(4.0) })
                    }.toThrow<AssertionError> {
                        message {
                            contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                            elementSuccess(0, 1.0, "$toBeDescr: 1.0")
                            elementFailing(1, 2.0, "$toBeDescr: 4.0")
                            contains(
                                "$warningBulletPoint$additionalElements:",
                                "$listBulletPoint${elementWithIndex(2)}: 3.0",
                                "$listBulletPoint${elementWithIndex(3)}: 4.0",
                                "$listBulletPoint${elementWithIndex(4)}: 4.0"
                            )
                            containsSize(5, 2)
                        }
                    }
                }
                it("1.0, 3.0, $isGreaterThanFun(4.0) -- $isGreaterThanFun(4.0) is wrong and 4.0 and 4.0 are missing") {
                    expect {
                        expect(oneToFour()).containsEntriesFun({ toBe(1.0) }, { toBe(3.0) }, { isGreaterThan(4.0) })
                    }.toThrow<AssertionError> {
                        message {
                            contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                            elementSuccess(0, 1.0, "$toBeDescr: 1.0")
                            elementFailing(1, 2.0, "$toBeDescr: 3.0")
                            elementFailing(2, 3.0, "$isGreaterThanDescr: 4.0")
                            contains(
                                "$warningBulletPoint$additionalElements:",
                                "$listBulletPoint${elementWithIndex(3)}: 4.0",
                                "$listBulletPoint${elementWithIndex(4)}: 4.0"
                            )
                            containsSize(5, 3)
                        }
                    }
                }
                it("1.0, 2.0, 3.0, 4.0, 4.0, 5.0 -- 5.0 too much") {
                    expect {
                        expect(oneToFour()).containsEntriesFun(
                            { toBe(1.0) },
                            { toBe(2.0) },
                            { toBe(3.0) },
                            { toBe(4.0) },
                            { toBe(4.0) },
                            { toBe(5.0) })
                    }.toThrow<AssertionError> {
                        message {
                            contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                            elementSuccess(0, 1.0, "$toBeDescr: 1.0")
                            elementSuccess(1, 2.0, "$toBeDescr: 2.0")
                            elementSuccess(2, 3.0, "$toBeDescr: 3.0")
                            elementSuccess(3, 4.0, "$toBeDescr: 4.0")
                            elementSuccess(4, 4.0, "$toBeDescr: 4.0")
                            elementNonExisting(5, "$toBeDescr: 5.0")
                            containsSize(5, 6)
                        }
                    }
                }
            }
        }
    }

    nullableCases(describePrefix) {

        describeFun(containsInOrderOnlyNullableEntries) {

            val null1null3 = { sequenceOf(null, 1.0, null, 3.0).constrainOnce().asIterable() }

            context("iterable ${null1null3().toList()}") {

                context("happy case") {
                    it("null, 1.0, null, 3.0") {
                        expect(null1null3()).containsInOrderOnlyNullableEntriesFun(
                            null,
                            { toBe(1.0) },
                            null,
                            { toBe(3.0) })
                    }
                    it("null, $isLessThanFun(5.0), null, $isLessThanFun(5.0)") {
                        expect(null1null3()).containsInOrderOnlyNullableEntriesFun(
                            null,
                            { isLessThan(5.0) },
                            null,
                            { isLessThan(5.0) }
                        )
                    }
                }

                context("error cases (throws AssertionError)") {

                    it("null, null, $isLessThanFun(5.0), $isGreaterThanFun(2.0) -- wrong order") {
                        expect {
                            expect(null1null3()).containsInOrderOnlyNullableEntriesFun(
                                null,
                                null,
                                { isLessThan(5.0) },
                                { isGreaterThan(2.0) }
                            )
                        }.toThrow<AssertionError> {
                            message {
                                contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                                elementSuccess(0, "null", "$toBeDescr: null")
                                elementFailing(1, 1.0, "$toBeDescr: null")
                                elementFailing(2, "null", "$isLessThanDescr: 5.0", explaining = true)
                                elementSuccess(3, 3.0, "$isGreaterThanDescr: 2.0")
                                containsSize(4, 4)
                            }
                        }
                    }

                    it("null, 1.0, null, 3.0, null -- null too much") {
                        expect {
                            expect(null1null3()).containsInOrderOnlyNullableEntriesFun(
                                null,
                                { toBe(1.0) },
                                null,
                                { toBe(3.0) },
                                null
                            )
                        }.toThrow<AssertionError> {
                            message {
                                contains.exactly(1).value("$rootBulletPoint$containsInOrderOnly:")
                                elementSuccess(0, "null", "$toBeDescr: null")
                                elementSuccess(1, 1.0, "$toBeDescr: 1.0")
                                elementSuccess(2, "null", "$toBeDescr: null")
                                elementSuccess(3, 3.0, "$toBeDescr: 3.0")
                                elementNonExisting(4, "$toBeDescr: null")
                                containsSize(4, 5)
                            }
                        }
                    }
                }
            }
        }
    }
})

