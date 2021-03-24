@file:Suppress("unused")

package com.gga.webhook.utils

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies.STRICT
import org.springframework.data.domain.Page
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
        infix fun <O, D> Collection<O>.convertTo(destination: Class<D>): Collection<D> =
            this.stream().map { mapper.map(it, destination) }.collect(Collectors.toList())

        @JvmStatic
        infix fun <O, D> O.convertTo(destination: Class<D>): D = mapper.map(this, destination)

        @JvmStatic
        infix fun <O, D> Page<O>.convertTo(destination: Class<D>): Page<D> = this.map { mapper.map(it, destination) }

    }
}