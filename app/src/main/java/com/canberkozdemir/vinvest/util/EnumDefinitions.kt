package com.canberkozdemir.vinvest.util

//10 * 60 * 1000 * 1000 * 1000L
enum class Minutes {
    ONE_MINUTE(1 * 60 * 1000 * 1000 * 1000L),
    FIVE_MINUTES(5 * 60 * 1000 * 1000 * 1000L),
    TEN_MINUTES(10 * 60 * 1000 * 1000 * 1000L),
    THIRTY_MINUTES(30 * 60 * 1000 * 1000 * 1000L),
    ONE_HOUR(60L * 60 * 1000 * 1000 * 1000L);

    var millisecondValue: Long? = 0

    constructor()

    constructor(millisecondValue: Long) {
        this.millisecondValue = millisecondValue
    }

    fun intToMillisecond(): Long? {
        return millisecondValue
    }
}