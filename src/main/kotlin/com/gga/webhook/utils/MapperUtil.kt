package com.gga.webhook.utils

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper
import java.util.stream.Collectors

class MapperUtil private constructor() {
    companion object {

        @JvmStatic
        private val mapper: Mapper = DozerBeanMapperBuilder.buildDefault()

        @JvmStatic
        fun <O, D> parseObjet(from: O, to: Class<D>): D = mapper.map(from, to)

        @JvmStatic
        fun <O, D> parseListObjets(from: Set<O>, to: Class<D>): Set<D> =
            from.stream().map { this.mapper.map(it, to) }.collect(Collectors.toSet())

    }
}