//---------------------------------------------------
//  Generated content, modify:
//  logic/generateLogic.gradle
//  if necessary - enjoy the day 🙂
//---------------------------------------------------

package ch.tutteli.atrium.scala2.logic

import ch.tutteli.atrium.creating.AssertionContainer
import ch.tutteli.atrium.domain.creating.changers.ExtractedFeaturePostStep
import ch.tutteli.atrium.logic.PairKt
import ch.tutteli.atrium.scala2.logic._

class PairLogic[K, T <: Pair[K, *>>(container: AssertionContainer[K]) {

    def first[T : Pair[K, *>](): ExtractedFeaturePostStep[T, K] = PairKt.first(container)
    def second[T : Pair[*, V]](): ExtractedFeaturePostStep[T, V] = PairKt.second(container)
}
