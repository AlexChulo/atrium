package ch.tutteli.atrium.logic.creating.iterable.contains.searchbehaviours.impl

import ch.tutteli.atrium.logic.creating.iterable.contains.searchbehaviours.InOrderSearchBehaviour
import ch.tutteli.atrium.translations.DescriptionIterableLikeExpectation

class InOrderSearchBehaviourImpl : InOrderSearchBehaviour {
    //TODO 1.3.0 replace with Representable and remove suppression
    @Suppress("DEPRECATION")
    override fun decorateDescription(description: ch.tutteli.atrium.reporting.translating.Translatable): ch.tutteli.atrium.reporting.translating.Translatable =
        ch.tutteli.atrium.reporting.translating.TranslatableWithArgs(DescriptionIterableLikeExpectation.IN_ORDER, description)
}
