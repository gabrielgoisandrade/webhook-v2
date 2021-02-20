package com.gga.webhook.errors

import java.io.Serializable
import java.util.*

data class ApiError(val message: String, val details: String, val timestamp: Date) : Serializable
