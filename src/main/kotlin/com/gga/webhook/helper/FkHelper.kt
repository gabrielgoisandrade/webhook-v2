package com.gga.webhook.helper

interface FkHelper<T> {

    fun collectAlterations(newResult: T, actualResult: T): T?

}