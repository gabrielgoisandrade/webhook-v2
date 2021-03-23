package com.gga.webhook.factories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
abstract class BaseRepositoryTestFactory: BaseTest() {

    @Autowired
    protected lateinit var entityManager: TestEntityManager

}