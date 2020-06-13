package cn.netdiscovery.command.function

/**
 *
 * @FileName:
 *          cn.netdiscovery.command.function.Result
 * @author: Tony Shen
 * @date: 2020-05-20 15:36
 * @version: V1.0 <描述当前版本功能>
 */
sealed class Result<out T, out E>

data class Success<out T>(val value: T) : Result<T, Nothing>()
data class Failure<out E>(val reason: E) : Result<Nothing, E>()

inline fun <T> resultFrom(block: () -> T): Result<T, Exception> =
    try {
        Success(block())
    } catch (x: Exception) {
        Failure(x)
    }

inline fun <T, Tʹ, E> Result<T, E>.map(f: (T) -> Tʹ): Result<Tʹ, E> = flatMap { value -> Success(f(value)) }

inline fun <T, Tʹ, E> Result<T, E>.flatMap(f: (T) -> Result<Tʹ, E>): Result<Tʹ, E> =
    when (this) {
        is Success<T> -> f(value)
        is Failure<E> -> this
    }

fun <T> Result<T, T>.get() = when (this) {
    is Success<T> -> value
    is Failure<T> -> reason
}

infix fun <T, E> Result<T, E>.or(block: T) = when (this) {
    is Success -> this
    else -> Success(block)
}

inline infix fun <T, E> Result<T, E>.getOrElse(block: (E) -> T): T {
    return when (this) {
        is Success -> value
        is Failure -> block(reason)
    }
}

inline fun <T, E> Result<T, E>.peek(f: (T) -> Unit) = apply { if (this is Success<T>) f(value) }
