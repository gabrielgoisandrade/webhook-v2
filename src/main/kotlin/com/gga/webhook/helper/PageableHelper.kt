package com.gga.webhook.helper

import com.gga.webhook.errors.exceptions.InvalidSortException
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

class PageableHelper<R> {

    var repository: JpaRepository<R, Long>? = null

    constructor()

    constructor(repository: JpaRepository<R, Long>) {
        this.repository = repository
    }

    inline fun <reified O> getPageableContent(page: Int, limit: Int, sort: String): Page<O> =
        PageRequest.of(page, limit, this.sort(sort)).run { repository!!.findAll(this) convertTo O::class.java }

    inline fun <T, reified O> getPageableContent(content: List<T>, page: Int, limit: Int, sort: String): Page<O> =
        PageRequest.of(page, limit, this.sort(sort)).run {
            PageImpl(content, this, content.size.toLong()) convertTo O::class.java
        }

    fun sort(sort: String): Sort = when (sort) {
        "asc" -> Sort.by("nodeId").ascending()
        "desc" -> Sort.by("nodeId").descending()
        else -> throw InvalidSortException("Sort type '$sort' isn't valid.")
    }

}