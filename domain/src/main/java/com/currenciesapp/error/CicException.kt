package com.fieldcode.customerinformationcenter.domain.error

abstract class CicException : Exception() {
    abstract val originalException: Throwable?
}
