package com.gga.webhook.factories

import com.gga.webhook.builder.DtoBuilder
import com.gga.webhook.builder.ModelBuilder
import com.gga.webhook.utils.RequestUtil
import kotlin.random.Random

abstract class BaseTest {

    protected val id: Long = RequestUtil().randomId

    protected val randomInt: Int = Random.nextInt()

    protected val dto: DtoBuilder = DtoBuilder()

    protected val model: ModelBuilder = ModelBuilder()

}