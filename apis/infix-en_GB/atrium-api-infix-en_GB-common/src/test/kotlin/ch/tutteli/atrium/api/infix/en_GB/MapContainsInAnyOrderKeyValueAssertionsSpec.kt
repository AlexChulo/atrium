package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.api.infix.en_GB.creating.map.KeyWithValueCreator
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.logic.utils.mapArguments
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.specs.integration.mfun2
import org.spekframework.spek2.Spek
import kotlin.jvm.JvmName
import ch.tutteli.atrium.api.infix.en_GB.MapContainsInAnyOrderKeyValueAssertionsSpec.Companion as C

class MapContainsInAnyOrderKeyValueAssertionsSpec : Spek({
    include(BuilderSpec)
    include(ShortcutSpec)
}) {
    object BuilderSpec : ch.tutteli.atrium.specs.integration.MapContainsInAnyOrderKeyValueAssertionsSpec(
        containsKeyValue_s to C::containsKeyValues,
        (containsKeyValue_s to C::containsKeyValuesNullable).withNullableSuffix(),
        "[Atrium][Shortcut] "
    )

    object ShortcutSpec : ch.tutteli.atrium.specs.integration.MapContainsInAnyOrderKeyValueAssertionsSpec(
        mfun2<String, Int, Expect<Int>.() -> Unit>(C::contains),
        mfun2<String?, Int?, (Expect<Int>.() -> Unit)?>(C::contains).withNullableSuffix(),
        "[Atrium][Shortcut] "
    )

    companion object : MapContainsSpecBase() {
        val containsKeyValue_s = "$contains $filler $inAnyOrder $keyValue/$keyValues"

        private fun containsKeyValues(
            expect: Expect<Map<out String, Int>>,
            a: Pair<String, Expect<Int>.() -> Unit>,
            aX: Array<out Pair<String, Expect<Int>.() -> Unit>>
        ): Expect<Map<out String, Int>> =
            mapArguments(a, aX).to { KeyWithValueCreator(it.first, it.second) }.let { (first, others) ->
                if (others.isEmpty()) expect contains o inAny order entry first
                else expect contains o inAny order the keyValues(first, *others)
            }

        private fun containsKeyValuesNullable(
            expect: Expect<Map<out String?, Int?>>,
            a: Pair<String?, (Expect<Int>.() -> Unit)?>,
            aX: Array<out Pair<String?, (Expect<Int>.() -> Unit)?>>
        ): Expect<Map<out String?, Int?>> =
            mapArguments(a, aX).to { KeyWithValueCreator(it.first, it.second) }.let { (first, others) ->
                if (others.isEmpty()) expect contains o inAny order entry first
                else expect contains o inAny order the keyValues(first, *others)
            }

        private fun contains(
            expect: Expect<Map<out String, Int>>,
            a: Pair<String, Expect<Int>.() -> Unit>,
            aX: Array<out Pair<String, Expect<Int>.() -> Unit>>
        ): Expect<Map<out String, Int>> =
            if (aX.isEmpty()) expect contains keyValue(a.first, a.second)
            else mapArguments(a, aX)
                .to { keyValue(it.first, it.second) }
                .let { (first, others) -> expect contains keyValues(first, *others) }

        @JvmName("containsNullable")
        private fun contains(
            expect: Expect<Map<out String?, Int?>>,
            a: Pair<String?, (Expect<Int>.() -> Unit)?>,
            aX: Array<out Pair<String?, (Expect<Int>.() -> Unit)?>>
        ): Expect<Map<out String?, Int?>> =
            if (aX.isEmpty()) expect contains keyValue(a.first, a.second)
            else mapArguments(a, aX)
                .to { keyValue(it.first, it.second) }
                .let { (first, others) -> expect contains keyValues(first, *others) }
    }


    @Suppress("unused", "UNUSED_VALUE")
    private fun ambiguityTest() {
        var map: Expect<Map<Number, CharSequence>> = notImplemented()
        var subMap: Expect<LinkedHashMap<out Number, String>> = notImplemented()
        var nKeyMap: Expect<Map<Number?, CharSequence>> = notImplemented()
        var nValueMap: Expect<Map<Number, CharSequence?>> = notImplemented()
        var nKeyValueMap: Expect<Map<Number?, CharSequence?>> = notImplemented()
        var ronKeyValueMap: Expect<Map<out Number?, CharSequence?>> = notImplemented()
        var starMap: Expect<Map<*, *>> = notImplemented()

        map = map contains o inAny order entry keyValue(1) { toBe("a") }
        subMap = subMap contains o inAny order entry keyValue(1) { toBe("a") }
        nKeyMap = nKeyMap contains o inAny order entry keyValue(1) { toBe("a") }
        nValueMap = nValueMap contains o inAny order entry keyValue(1) { toBe("a") }
        nKeyValueMap = nKeyValueMap contains o inAny order entry keyValue(1) { toBe("a") }
        ronKeyValueMap = ronKeyValueMap contains o inAny order entry keyValue(1) { toBe("a") }
        starMap = starMap contains o inAny order entry keyValue(1) { toBe("a") }

        map = map contains o inAny order the keyValues(keyValue(1) { toBe("a") })
        subMap = subMap contains o inAny order the keyValues(keyValue(1) { toBe("a") })
        nKeyMap = nKeyMap contains o inAny order the keyValues(keyValue(1) { toBe("a") })
        nValueMap = nValueMap contains o inAny order the keyValues(keyValue(1) { toBe("a") })
        nKeyValueMap = nKeyValueMap contains o inAny order the keyValues(keyValue(1) { toBe("a") })
        ronKeyValueMap = ronKeyValueMap contains o inAny order the keyValues(keyValue(1) { toBe("a") })
        starMap = starMap contains o inAny order the keyValues(keyValue(1) { toBe("a") })

        map = map contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        subMap = subMap contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        nKeyMap = nKeyMap contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        nValueMap = nValueMap contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        nKeyValueMap = nKeyValueMap contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        ronKeyValueMap = ronKeyValueMap contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        starMap = starMap contains o inAny order the keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )

        nKeyMap = nKeyMap contains o inAny order entry keyValue(null) { toBe("a") }
        nKeyMap = nKeyMap contains o inAny order entry keyValue(null) { toBe("a") }
        nValueMap = nValueMap contains o inAny order entry keyValue(1.2, null)
        nKeyValueMap = nKeyValueMap contains o inAny order entry keyValue(null) { toBe("a") }
        nKeyValueMap = nKeyValueMap contains o inAny order entry keyValue(null, null)
        ronKeyValueMap = ronKeyValueMap contains o inAny order entry keyValue(null) { toBe("a") }
        ronKeyValueMap = ronKeyValueMap contains o inAny order entry keyValue(null, null)
        starMap = starMap contains o inAny order entry keyValue(null) { toBe("a") }
        starMap = starMap contains o inAny order entry keyValue(null, null)

        nKeyMap = nKeyMap contains o inAny order the keyValues(keyValue(null) { toBe("a") })
        nKeyValueMap = nKeyValueMap contains o inAny order the keyValues(keyValue(null) { toBe("a") })
        ronKeyValueMap = ronKeyValueMap contains o inAny order the keyValues(keyValue(null) { toBe("a") })
        starMap = starMap contains o inAny order the keyValues(keyValue(null) { toBe("a") })

        nKeyMap = nKeyMap contains o inAny order the keyValues(keyValue(null) { toBe("a") })
        nValueMap = nValueMap contains o inAny order the keyValues(keyValue(1, null), keyValue(1) { toBe("a") })
        nKeyValueMap = nKeyValueMap contains o inAny order the keyValues(
            keyValue(null) { toBe("a") },
            keyValue(null, null),
            keyValue(1, null)
        )
        ronKeyValueMap = ronKeyValueMap contains o inAny order the keyValues(
            keyValue(null) { toBe("a") },
            keyValue(null, null),
            keyValue(1, null)
        )
        starMap = starMap contains o inAny order the keyValues(
            keyValue(null) { toBe("a") },
            keyValue(null, null),
            keyValue(1, null)
        )

        /// ------------- shortcuts -----------------------------------------------------------------

        map = map contains keyValue(1) { toBe("a") }
        subMap = subMap contains keyValue(1) { toBe("a") }
        nKeyMap = nKeyMap contains keyValue(1) { toBe("a") }
        nValueMap = nValueMap contains keyValue(1) { toBe("a") }
        nKeyValueMap = nKeyValueMap contains keyValue(1) { toBe("a") }
        ronKeyValueMap = ronKeyValueMap contains keyValue(1) { toBe("a") }
        starMap = starMap contains keyValue(1) { toBe("a") }

        map = map contains keyValues(keyValue(1) { toBe("a") })
        subMap = subMap contains keyValues(keyValue(1) { toBe("a") })
        nKeyMap = nKeyMap contains keyValues(keyValue(1) { toBe("a") })
        nValueMap = nValueMap contains keyValues(keyValue(1) { toBe("a") })
        nKeyValueMap = nKeyValueMap contains keyValues(keyValue(1) { toBe("a") })
        ronKeyValueMap = ronKeyValueMap contains keyValues(keyValue(1) { toBe("a") })
        starMap = starMap contains keyValues(keyValue(1) { toBe("a") })

        map = map contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        subMap = subMap contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        nKeyMap = nKeyMap contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        nValueMap = nValueMap contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        nKeyValueMap = nKeyValueMap contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        ronKeyValueMap = ronKeyValueMap contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )
        starMap = starMap contains keyValues(
            keyValue(1 as Number) { toBe("a") },
            keyValue(1.2) { toBe("b") }
        )

        nKeyMap = nKeyMap contains keyValue(null) { toBe("a") }
        nValueMap = nValueMap contains keyValue(1.2, null)
        nKeyValueMap = nKeyValueMap contains keyValue(null) { toBe("a") }
        nKeyValueMap = nKeyValueMap contains keyValue(null, null)
        ronKeyValueMap = ronKeyValueMap contains keyValue(null) { toBe("a") }
        ronKeyValueMap = ronKeyValueMap contains keyValue(null, null)
        starMap = starMap contains keyValue(null) { toBe("a") }
        starMap = starMap contains keyValue(null, null)

        nKeyMap = nKeyMap contains keyValues(keyValue(null) { toBe("a") })
        nKeyValueMap = nKeyValueMap contains keyValues(keyValue(null) { toBe("a") })
        ronKeyValueMap = ronKeyValueMap contains keyValues(keyValue(null) { toBe("a") })
        starMap = starMap contains keyValues(keyValue(null) { toBe("a") })

        nKeyMap = nKeyMap contains keyValues(keyValue(null) { toBe("a") })
        nValueMap = nValueMap contains keyValues(keyValue(1, null), keyValue(1) { toBe("a") })
        nKeyValueMap = nKeyValueMap contains keyValues(
            keyValue(null) { toBe("a") },
            keyValue(null, null),
            keyValue(1, null)
        )
        ronKeyValueMap = ronKeyValueMap contains keyValues(
            keyValue(null) { toBe("a") },
            keyValue(null, null),
            keyValue(1, null)
        )
        starMap = starMap contains keyValues(
            keyValue(null) { toBe("a") },
            keyValue(null, null),
            keyValue(1, null)
        )
    }
}
