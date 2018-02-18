package src.binance

class PasswordGenerator {
    lateinit var seed: String
    var hashRepetition: Int = 0
    fun generatePassword(): BHPassword = BHPassword((seed + hashRepetition))
}

class BHPassword(var basePass: String) {
    init {
        basePass = basePass.toUpperCase()
    }
}

fun main(args: Array<String>) {
    val withPassword = with(PasswordGenerator()) {
        seed = "Joao"
        hashRepetition = 25
        generatePassword()
        this
    }

    val runPassword = PasswordGenerator().run {
        seed = "Joao"
        hashRepetition = 25
        generatePassword()
    }
            .basePass
            .run {
                toUpperCase()
                trim()
            }

    val applyPassword = PasswordGenerator().apply {
        seed = "Joao"
        hashRepetition = 25
        generatePassword()
    }

    val letPassword = PasswordGenerator().let {
        it.seed = "Joao"
        it.hashRepetition = 25
        it.generatePassword()
    }.basePass

    val alsoPassword = PasswordGenerator().also {
        it.seed = "Joao"
        it.hashRepetition = 25
        it.generatePassword()
    }


}