package com.gga.webhook.helper

import com.gga.webhook.errors.exceptions.InvalidSortException
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

class PageableHelper<T>(val repository: JpaRepository<T, Long>) {

    inline fun <reified O> getPageableContent(page: Int = 0, limit: Int = 10, sort: String = "asc"): Page<O> =
        PageRequest.of(page, limit, this.sort(sort)).run { repository.findAll(this) convertTo O::class.java }

    fun sort(sort: String): Sort = when (sort) {
        "asc" -> Sort.by("id").ascending()
        "desc" -> Sort.by("id").descending()
        else -> throw InvalidSortException("Sort type '$sort' isn't valid.")
    }

}