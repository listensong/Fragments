package com.example.fragment

class CompanionShow {
    companion object {
        private var variableLong: Long = 1110L
        internal var variableInt: Int = 220

        @JvmStatic
        val variableUInt: UInt = 3330U

        @JvmField
        val variableFloat: Float = 44440F
        var variableFloatNew: Float = 50505F

        var variableDouble: Double? = null
        const val variableString: String = "HelloWorld"

        fun companionFunction(): Boolean {
            return false
        }

        @JvmStatic
        fun companionFunc() {
            println("What's companion")
        }
    }
}