package com.gga.webhook.utils

import org.springframework.hateoas.Links

class LinkUtil private constructor() {
    companion object {

        @JvmStatic
        fun configureLinks(links: Links, function: () -> Unit): Unit = if (links.isEmpty) function() else Unit

    }
}