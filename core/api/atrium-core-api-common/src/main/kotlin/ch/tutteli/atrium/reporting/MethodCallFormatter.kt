package ch.tutteli.atrium.reporting

/**
 * Responsible to format a method call in reporting.
 */
interface MethodCallFormatter {

    /**
     * Returns a lazy representation of the method call to a method named [methodName] with the given [arguments].
     *
     * @param methodName The name of the method for which a call with the given [arguments] should be formatted.
     * @param arguments The arguments of the method call.
     *
     * @return An lambda containing the logic to build the representation.
     */
    @Deprecated("Use the overload which returns a string right away, wrap it into a lambda on your own if you need this functionality; will be removed with 0.17.0")
    fun format(methodName: String, arguments: Array<out Any?>): () -> String

    /**
     * Returns a representation of a method call to a method named [methodName] with the given [arguments].
     *
     * @param methodName The name of the method for which a call with the given [arguments] should be formatted.
     * @param arguments The arguments of the method call.
     *
     * @return An lambda containing the logic to build the representation.
     */
    fun formatCall(methodName: String, arguments: Array<out Any?>): String


    /**
     * Formats the given [argument].
     */
    fun formatArgument(argument: Any?): String
}
