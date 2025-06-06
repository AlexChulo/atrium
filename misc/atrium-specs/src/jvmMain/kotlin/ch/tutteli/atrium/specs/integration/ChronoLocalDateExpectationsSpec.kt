package ch.tutteli.atrium.specs.integration

import ch.tutteli.atrium.api.fluent.en_GB.messageToContain
import ch.tutteli.atrium.api.fluent.en_GB.toThrow
import ch.tutteli.atrium.api.verbs.internal.expect
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.translations.DescriptionDateTimeLikeExpectation
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.chrono.ChronoLocalDate
import java.time.chrono.JapaneseDate

abstract class ChronoLocalDateExpectationsSpec(
    toBeBefore: Fun1<ChronoLocalDate, ChronoLocalDate>,
    toBeBeforeOrTheSamePointInTimeAs: Fun1<ChronoLocalDate, ChronoLocalDate>,
    toBeAfter: Fun1<ChronoLocalDate, ChronoLocalDate>,
    toBeAfterOrTheSamePointInTimeAs: Fun1<ChronoLocalDate, ChronoLocalDate>,
    toBeTheSamePointInTimeAs: Fun1<ChronoLocalDate, ChronoLocalDate>,
    describePrefix: String = "[Atrium] "
) : Spek({

    val december22 = LocalDate.of(2019, 12, 22)
    val december23 = LocalDate.of(2019, 12, 23)
    val december24 = LocalDate.of(2019, 12, 24)

    include(object : SubjectLessSpec<ChronoLocalDate>(
        describePrefix,
        toBeBefore.forSubjectLessTest(december23),
        toBeBeforeOrTheSamePointInTimeAs.forSubjectLessTest(december23),
        toBeAfter.forSubjectLessTest(december23),
        toBeAfterOrTheSamePointInTimeAs.forSubjectLessTest(december23),
        toBeTheSamePointInTimeAs.forSubjectLessTest(december23)
    ) {})

    val toBeBeforeDescr = DescriptionDateTimeLikeExpectation.TO_BE_BEFORE.getDefault()
    val toBeBeforeOrTheSamePointInTimeAsDescr = DescriptionDateTimeLikeExpectation.TO_BE_BEFORE_OR_THE_SAME_POINT_IN_TIME_AS.getDefault()
    val toBeAfterDescr = DescriptionDateTimeLikeExpectation.TO_BE_AFTER.getDefault()
    val toBeAfterOrTheSamePointInTimeAsDescr = DescriptionDateTimeLikeExpectation.TO_BE_AFTER_OR_THE_SAME_POINT_IN_TIME_AS.getDefault()
    val toBeTheSamePointInTimeAsDescr = DescriptionDateTimeLikeExpectation.TO_BE_THE_SAME_DAY_AS.getDefault()


    listOf<ChronoLocalDate>(
        december23,
        JapaneseDate.of(2019, 12, 23)
    ).forEach { subject ->

        describe("$describePrefix subject is $subject") {
            describe("${toBeBefore.name} ...") {
                val toBeBeforeFun = toBeBefore.lambda

                it("... $december22 throws an AssertionError") {
                    expect {
                        expect(subject).toBeBeforeFun(december22)
                    }.toThrow<AssertionError> { messageToContain("$toBeBeforeDescr: $december22") }
                }
                it("... $december23 throws an AssertionError") {
                    expect {
                        expect(subject).toBeBeforeFun(december23)
                    }.toThrow<AssertionError> { messageToContain("$toBeBeforeDescr: $december23") }
                }
                it("... $december24 does not throw") {
                    expect(subject).toBeBeforeFun(december24)
                }
            }
            describe("${toBeBeforeOrTheSamePointInTimeAs.name} ...") {
                val toBeBeforeOrTheSamePointInTimeAsFun = toBeBeforeOrTheSamePointInTimeAs.lambda

                it("... $december22 throws an AssertionError") {
                    expect {
                        expect(subject).toBeBeforeOrTheSamePointInTimeAsFun(december22)
                    }.toThrow<AssertionError> { messageToContain("$toBeBeforeOrTheSamePointInTimeAsDescr: $december22") }
                }
                it("... $december23 does not throw") {
                    expect(subject).toBeBeforeOrTheSamePointInTimeAsFun(december23)
                }
                it("... $december24 does not throw") {
                    expect(subject).toBeBeforeOrTheSamePointInTimeAsFun(december24)
                }
            }
            describe("${toBeAfter.name} ...") {
                val toBeAfterFun = toBeAfter.lambda

                it("... $december22 does not throw") {
                    expect(subject).toBeAfterFun(december22)
                }
                it("... $december23 throws an AssertionError") {
                    expect {
                        expect(subject).toBeAfterFun(december23)
                    }.toThrow<AssertionError> { messageToContain("$toBeAfterDescr: $december23") }
                }
                it("... $december24 throws an AssertionError") {
                    expect {
                        expect(subject).toBeAfterFun(december24)
                    }.toThrow<AssertionError> { messageToContain("$toBeAfterDescr: $december24") }
                }
            }
            describe("${toBeAfterOrTheSamePointInTimeAs.name} ...") {
                val toBeAfterOrTheSamePointInTimeAsFun = toBeAfterOrTheSamePointInTimeAs.lambda

                it("... $december22 does not throw") {
                    expect(subject).toBeAfterOrTheSamePointInTimeAsFun(december22)
                }
                it("... $december23 does not throw") {
                    expect(subject).toBeAfterOrTheSamePointInTimeAsFun(december23)
                }
                it("... $december24 throws an AssertionError") {
                    expect {
                        expect(subject).toBeAfterOrTheSamePointInTimeAsFun(december24)
                    }.toThrow<AssertionError> { messageToContain("$toBeAfterOrTheSamePointInTimeAsDescr: $december24") }
                }
            }
            describe("${toBeTheSamePointInTimeAs.name} ...") {
                val toBeTheSamePointInTimeAsFun = toBeTheSamePointInTimeAs.lambda

                it("... $december22 throws an AssertionError") {
                    expect {
                        expect(subject).toBeTheSamePointInTimeAsFun(december22)
                    }.toThrow<AssertionError> { messageToContain("$toBeTheSamePointInTimeAsDescr: $december22") }
                }
                it("... $december23 does not throw") {
                    expect(subject).toBeTheSamePointInTimeAsFun(december23)
                }
                it("... $december24 throws an AssertionError") {
                    expect {
                        expect(subject).toBeTheSamePointInTimeAsFun(december24)
                    }.toThrow<AssertionError> { messageToContain("$toBeTheSamePointInTimeAsDescr: $december24") }
                }
            }
        }
    }
})
