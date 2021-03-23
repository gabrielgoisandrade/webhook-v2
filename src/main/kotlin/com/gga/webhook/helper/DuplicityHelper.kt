package com.gga.webhook.helper

interface DuplicityHelper<T> {

    fun findDuplicatedValues(newValues: List<T>): HashMap<String, List<T>>

}