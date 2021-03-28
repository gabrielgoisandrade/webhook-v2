package com.gga.webhook.helper

import org.springframework.http.ResponseEntity

interface LinkHelper<T> {

    fun T.setLinks(): ResponseEntity<T>

}