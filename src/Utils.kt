import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

// Memoize 1 argument function
interface Memo1<A, R> {
    fun recurse(a: A): R
}

abstract class Memoized1<A, R> {
    private data class Input<A>(
        val a: A,
    )

    private val cache = mutableMapOf<Input<A>, R>()
    private val memo = object : Memo1<A, R> {
        override fun recurse(a: A): R =
            cache.getOrPut(Input(a)) { function(a) }
    }

    protected abstract fun Memo1<A, R>.function(a: A): R

    fun execute(a: A): R = memo.recurse(a)
}

fun <A, R> (Memo1<A, R>.(A) -> R).memoize(): (A) -> R {
    val memoized = object : Memoized1<A, R>() {
        override fun Memo1<A, R>.function(a: A): R = this@memoize(a)
    }
    return { a ->
        memoized.execute(a)
    }
}

// Memoize 2 argument function
interface Memo2<A, B, R> {
    fun recurse(a: A, b: B): R
}

abstract class Memoized2<A, B, R> {
    private data class Input<A, B>(
        val a: A,
        val b: B
    )

    private val cache = mutableMapOf<Input<A, B>, R>()
    private val memo = object : Memo2<A, B, R> {
        override fun recurse(a: A, b: B): R =
            cache.getOrPut(Input(a, b)) { function(a, b) }
    }

    protected abstract fun Memo2<A, B, R>.function(a: A, b: B): R

    fun execute(a: A, b: B): R = memo.recurse(a, b)
}

fun <A, B, R> (Memo2<A, B, R>.(A, B) -> R).memoize(): (A, B) -> R {
    val memoized = object : Memoized2<A, B, R>() {
        override fun Memo2<A, B, R>.function(a: A, b: B): R = this@memoize(a, b)
    }
    return { a, b ->
        memoized.execute(a, b)
    }
}

// example usage memoization
fun Memo1<Int, Int>.recurse(a: Int): Int = TODO()
val recurse = Memo1<Int, Int>::recurse.memoize()