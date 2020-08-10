package com.currenciesapp.error

abstract class MyException : Exception() {
    abstract val originalException: Throwable?
}
