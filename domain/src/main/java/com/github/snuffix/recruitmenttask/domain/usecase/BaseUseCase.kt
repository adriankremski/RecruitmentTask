package com.github.snuffix.recruitmenttask.domain.usecase

import com.github.snuffix.recruitmenttask.domain.model.NoConnectivityException
import com.github.snuffix.recruitmenttask.domain.model.Result
import kotlinx.coroutines.CancellationException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
        } catch (exception: NoConnectivityException) {
            Result.NetworkError(exception = exception)
        } catch (exception: UnknownHostException) {
            Result.NetworkError(exception = exception)
        } catch (exception: SocketTimeoutException) {
            Result.NetworkError(exception = exception)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.Error(exception = exception)
        }
    }
}
