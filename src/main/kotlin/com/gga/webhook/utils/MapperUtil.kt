package com.gga.webhook.utils

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper
import java.util.stream.Collectors

class MapperUtil private constructor() {
    companion object {

        @JvmStatic
        private val mapper: Mapper = DozerBeanMapperBuilder.buildDefault()

        @JvmStatic
        infix fun <O, D> O.convertTo(to: Class<D>): D = mapper.map(this, to)

        @JvmStatic
        infix fun <O, D> Collection<O>.convertTo(to: Class<D>): Set<D> =
            this.stream().map { mapper.map(it, to) }.collect(Collectors.toSet())

    }
}