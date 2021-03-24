package com.gga.webhook.helper

interface DuplicityHelper<T> {

    fun findDuplicatedValues(newValues: HashSet<T>): HashMap<String, HashSet<T>>

}