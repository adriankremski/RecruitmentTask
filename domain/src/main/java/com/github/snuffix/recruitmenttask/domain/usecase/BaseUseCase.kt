package com.github.snuffix.recruitmenttask.domain.usecase

import com.github.snuffix.recruitmenttask.domain.model.Result
import kotlinx.coroutines.CancellationException

abstract class BaseUseCase<DATA : Any, in Params> {
    abstract suspend fun execute(params: Params): DATA

    /**
     * Repository calls may throw exceptions (Cancellation, NetworkError in later version, etc)
     * This method is responsible for wrapping the exceptions before passing them to the presentation
     */
    protected suspend fun <T : Any> getResult(sourceCall: suspend () -> T): Result<T> {
        return try {
            Result.Ok(sourceCall())
        } catch (exception: CancellationException) {
            Result.CancelledError()
        } catch (exception: Exception) {
            Result.Error(exception = exception)
        }
    }
}
