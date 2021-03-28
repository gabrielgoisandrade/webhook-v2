package com.gga.webhook.helper

interface AssociationEntityHelper<T> {

    fun findDuplicatedValues(newValues: List<T>): HashMap<String, List<T>>

    fun List<T>.filterValues(toFilter: List<T>)

    fun getExistingValues(newValues: List<T>): List<T>

}