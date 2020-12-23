package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.logic.*
import ch.tutteli.kbox.identity

/**
 * Expects that the subject of the assertion (a [Collection]) is an empty [Collection].
 *
 * @param empty Use the pseudo-keyword `empty`.
 *
 * @return An [Expect] for the current subject of the assertion.
 */
infix fun <T : Collection<*>> Expect<T>.toBe(@Suppress("UNUSED_PARAMETER") empty: empty): Expect<T> =
    _logicAppend { isEmpty(::identity) }

/**
 * Expects that the subject of the assertion (a [Collection]) is not an empty [Collection].
 *
 * @param empty Use the pseudo-keyword `empty`.
 *
 * @return An [Expect] for the current subject of the assertion.
 */
infix fun <T : Collection<*>> Expect<T>.notToBe(@Suppress("UNUSED_PARAMETER") empty: empty): Expect<T> =
    _logicAppend { isNotEmpty(::identity) }

/**
 * Expects that the subject of the assertion (a [Collection]) has the given [expected] size.
 *
 * Shortcut for `size.toBe(expected)`.
 *
 * @return An [Expect] for the current subject of the assertion.
 */
infix fun <T : Collection<*>> Expect<T>.hasSize(expected: Int): Expect<T> =
    size { toBe(expected) }


/**
 * Creates an [Expect] for the property [Collection.size] of the subject of the assertion,
 * so that further fluent calls are assertions about it.
 *
 * @return The newly created [Expect] for the extracted feature.
 */
val <T : Collection<*>> Expect<T>.size: Expect<Int>
    get() = _logic.size(::identity).transform()

/**
 * Expects that the property [Collection.size] of the subject of the assertion
 * holds all assertions the given [assertionCreator] creates for it and
 * returns an [Expect] for the current subject of the assertion.
 *
 * @return An [Expect] for the current subject of the assertion.
 */
infix fun <E, T : Collection<E>> Expect<T>.size(assertionCreator: Expect<Int>.() -> Unit): Expect<T> =
    _logic.size(::identity).collectAndAppend(assertionCreator)
