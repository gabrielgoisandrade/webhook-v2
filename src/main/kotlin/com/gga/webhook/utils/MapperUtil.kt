@file:Suppress("unused")

package com.gga.webhook.utils

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies.STRICT
import java.util.stream.Collectors

class MapperUtil private constructor() {
    companion object {

        private val mapper: ModelMapper = ModelMapper().apply {
            with(this.configuration) {
                this.isAmbiguityIgnored = true
                this.isFieldMatchingEnabled = true
                this.matchingStrategy = STRICT
            }
        }

        @JvmStatic
        infix fun <O, D> Collection<O>.convertTo(to: Class<D>): Set<D> =
            this.stream().map { mapper.map(it, to) }.collect(Collectors.toSet())

        @JvmStatic
        infix fun <O, D> O.convertTo(to: Class<D>): D = mapper.map(this, to)

    }
}