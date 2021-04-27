package rationals

import java.math.BigInteger

class Rational(var numerator: BigInteger, var denominator: BigInteger) : Comparable<Rational> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Rational

        val firstRational: Rational = this.numerator.times(other.denominator) divBy this.denominator.times(other.denominator)
        val secondRational: Rational = other.numerator.times(this.denominator) divBy other.denominator.times(this.denominator)

        if (firstRational.numerator.equals(secondRational.numerator)
                && firstRational.denominator.equals(secondRational.denominator)) {
            return true
        }
        return false
    }

    override fun toString(): String {
        if (denominator == 1.toBigInteger()) {
            return "$numerator"
        }
        if (denominator < 0.toBigInteger()) {
            denominator = denominator.times((-1).toBigInteger())
            numerator = numerator.times((-1).toBigInteger())
        }
        return "$numerator/$denominator"
    }

    override fun compareTo(other: Rational): Int {
        return this.numerator.times(other.denominator).compareTo(other.numerator.times(this.denominator))
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }
}

infix fun <T : Number> Number.divBy(denominator: T): Rational {
    var numerator = BigInteger.valueOf(this.toLong())
    var denominator = BigInteger.valueOf(denominator.toLong())
    return normialized(Rational(numerator, denominator))
}

fun normialized(rational: Rational): Rational {
    var gcd = rational.numerator.gcd(rational.denominator)
    return Rational(rational.numerator.div(gcd), rational.denominator.div(gcd))
}

operator fun Rational.plus(rational: Rational): Rational {
    var resultNumerator = rational.numerator.times(this.denominator).plus(this.numerator.times(rational.denominator))
    var resultDenominator = this.denominator.times(rational.denominator)
    return normialized(Rational(resultNumerator, resultDenominator))

}

operator fun Rational.minus(rational: Rational): Rational {
    var resultDenominator = this.denominator.times(rational.denominator)
    var resultNumerator = this.numerator.times(rational.denominator).minus(rational.numerator.times(this.denominator))
    return normialized(Rational(resultNumerator, resultDenominator))
}

operator fun Rational.times(rational: Rational): Rational {
    var resultDenominator = this.denominator.times(rational.denominator)
    var resultNumerator = this.numerator.times(rational.numerator)
    return normialized(Rational(resultNumerator, resultDenominator))
}

operator fun Rational.div(rational: Rational): Rational {
    var secondRational = rational.denominator divBy rational.numerator
    return this * secondRational;
}

operator fun Rational.unaryMinus(): Rational {
    return normialized(Rational(-this.numerator, denominator))
}

fun String.toRational(): Rational {
    if (split("/").size > 1) {
        var numeratorBigInteger = split("/")[0].toBigInteger()
        var denominatorBigInteger = split("/")[1].toBigInteger()

        if (denominatorBigInteger != 0.toBigInteger()) {
            return normialized(Rational(numeratorBigInteger, denominatorBigInteger))
        }

    } else {
        var numeratorBigInteger = split("/")[0].toBigIntegerOrNull()
        if (numeratorBigInteger != null) {
            return numeratorBigInteger divBy 1.toBigInteger()
        }
    }
    return 0.toBigInteger() divBy 1.toBigInteger()
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")

    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

    println("20395802948019459839003802001190283020/32493205934869548609023910932454365628".toRational())

    println("20395802948019459839003802001190283020/32493205934869548609023910932454365628".toRational() in "1/2".toRational().."2/3".toRational())

}



