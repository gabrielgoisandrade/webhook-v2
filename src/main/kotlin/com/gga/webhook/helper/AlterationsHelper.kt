package com.gga.webhook.helper

interface AlterationsHelper<T> {

    fun collectAlterations(newResult: T, actualResult: T): T?

}