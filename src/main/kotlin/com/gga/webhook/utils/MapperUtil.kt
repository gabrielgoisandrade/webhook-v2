@file:Suppress("unused")

package com.gga.webhook.utils

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import java.util.stream.Collectors

class MapperUtil private constructor() {
    companion object {

        @JvmStatic
        private val mapper: ModelMapper = ModelMapper().also {
            it.configuration.setAmbiguityIgnored(true).matchingStrategy = MatchingStrategies.STRICT
        }

        @JvmStatic
        infix fun <O, D> O.convertTo(to: Class<D>): D = mapper.map(this, to)

        @JvmStatic
        infix fun <O, D> Collection<O>.convertTo(to: Class<D>): Set<D> =
            this.stream().map { mapper.map(it, to) }.collect(Collectors.toSet())

    }
}