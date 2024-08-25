package ch.tutteli.atrium.api.verbs.internal

import ch.tutteli.atrium.api.verbs.internal.factory.InternalExpectationVerbs
import ch.tutteli.atrium.core.ExperimentalNewExpectTypes
import ch.tutteli.atrium.creating.*
import ch.tutteli.atrium.logic._logic
import ch.tutteli.atrium.logic.creating.RootExpectBuilder
import ch.tutteli.atrium.logic.manualFeature
import ch.tutteli.atrium.logic.toAssertionCreator
import ch.tutteli.atrium.logic.toExpectGrouping
import ch.tutteli.atrium.reporting.AtriumErrorAdjuster
import ch.tutteli.atrium.reporting.Text
import ch.tutteli.atrium.reporting.erroradjusters.MultiAtriumErrorAdjuster
import ch.tutteli.atrium.reporting.erroradjusters.RemoveAtriumFromAtriumError
import ch.tutteli.atrium.reporting.erroradjusters.RemoveRunnerFromAtriumError
import ch.tutteli.atrium.testfactories.TestFactoryBuilder
import ch.tutteli.atrium.testfactories.testFactoryTemplate

import ch.tutteli.atrium.api.verbs.internal.expect as atriumVerb

@OptIn(ExperimentalNewExpectTypes::class, ExperimentalComponentFactoryContainer::class)
fun <T> expect(subject: T): RootExpect<T> =
    RootExpectBuilder.forSubject(subject)
        .withVerb("I expected subject")
        .withOptions {
            withComponent(AtriumErrorAdjuster::class) { c ->
                MultiAtriumErrorAdjuster(
                    c.build<RemoveRunnerFromAtriumError>(),
                    RemoveAtriumButNotAtriumSpecsFromAtriumErrorImpl(),
                    otherAdjusters = emptyList()
                )
            }
        }
        .build()

fun <T> expect(subject: T, assertionCreator: Expect<T>.() -> Unit): Expect<T> =
    atriumVerb(subject)._logic.appendAsGroup(assertionCreator)


@OptIn(ExperimentalNewExpectTypes::class)
fun expectGrouped(
    description: String = "my expectations",
    configuration: RootExpectBuilder.OptionsChooser<*>.() -> Unit = {},
    groupingActions: ExpectGrouping.() -> Unit,
): ExpectGrouping = RootExpectBuilder.forSubject(Text.EMPTY)
    .withVerb(description)
    .withOptions {
        configuration()
    }
    .build()
    ._logic.appendAsGroup(groupingActions.toAssertionCreator())
    .toExpectGrouping()


fun <R> ExpectGrouping.expect(subject: R): Expect<R> =
    expectWithinExpectGroup(subject).transform()

fun <R> ExpectGrouping.expect(subject: R, assertionCreator: Expect<R>.() -> Unit): Expect<R> =
    expectWithinExpectGroup(subject).transformAndAppend(assertionCreator)


private fun <R> ExpectGrouping.expectWithinExpectGroup(subject: R) =
    _logic.manualFeature("I expected subject") { subject }


expect class RemoveAtriumButNotAtriumSpecsFromAtriumErrorImpl() : RemoveAtriumFromAtriumError{
    override fun adjust(throwable: Throwable)
    override fun adjustOtherThanStacks(throwable: Throwable)
}

fun testFactory(setup: TestFactoryBuilder.() -> Unit) = testFactoryTemplate(setup, InternalExpectationVerbs)
fun testFactory(
    setup: TestFactoryBuilder.() -> Unit,
    vararg otherSetups: TestFactoryBuilder.() -> Unit
) = testFactoryTemplate(setup, otherSetups, InternalExpectationVerbs)
