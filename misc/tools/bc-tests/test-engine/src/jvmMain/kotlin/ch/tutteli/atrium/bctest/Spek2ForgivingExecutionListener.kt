package ch.tutteli.atrium.bctest

import org.spekframework.spek2.junit.JUnitEngineExecutionListenerAdapter
import org.spekframework.spek2.runtime.execution.ExecutionListener
import org.spekframework.spek2.runtime.execution.ExecutionResult
import org.spekframework.spek2.runtime.scope.TestScopeImpl

class Spek2ForgivingExecutionListener(
    private val listener: JUnitEngineExecutionListenerAdapter,
    private val forgiveRegex: Regex
) : ExecutionListener by listener {

    override fun testExecutionFinish(test: TestScopeImpl, result: ExecutionResult) {
        when (result) {
            ExecutionResult.Success -> listener.testExecutionFinish(test, ExecutionResult.Success)
            is ExecutionResult.Failure -> handleFailure(result, test)
        }
    }

    private fun handleFailure(result: ExecutionResult.Failure, test: TestScopeImpl) {
        if (forgiveRegex.matches(test.path.toString())) {
            println("forgiving ${test.path}")
            listener.testExecutionFinish(test, ExecutionResult.Success)
        } else {
            println("!!!!! path of test in case you want to forgive it failing:\n${test.path}")
            listener.testExecutionFinish(test, result)
        }
    }
}
