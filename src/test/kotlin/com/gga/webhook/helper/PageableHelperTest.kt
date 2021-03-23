package com.gga.webhook.helper

import com.gga.webhook.errors.exceptions.InvalidSortException
import com.gga.webhook.models.EventModel
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.repositories.EventRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PageableHelperTest {

    @Mock
    private lateinit var eventRepository: EventRepository

    @InjectMocks
    private lateinit var helper: PageableHelper<EventModel>

    @BeforeAll
    private fun init() {
        openMocks(this)
    }

    @Test
    @DisplayName("Must not throw an exception by invalid direction value")
    fun direction() {
        val direction = "asc"

        val expectedSort: Sort = Sort.by("id").ascending()

        assertDoesNotThrow { this.helper.sort(direction) }.also { assertThat(it).isEqualTo(expectedSort) }
    }

    @Test
    @DisplayName("Must throw an exception by invalid direction value")
    fun throwErrorByInvalidDirection() {
        val direction = "abc"

        assertThrows<InvalidSortException> { this.helper.sort(direction) }.also {
            assertThat(it).isInstanceOf(InvalidSortException::class.java)
                .hasMessage("Sort type '$direction' isn't valid.")
        }
    }

    @Test
    @DisplayName("Must return a pageable content")
    fun getRepository() {
        val pagedEvent: PageImpl<EventModel> = PageImpl(listOf(EventModel(action = "some issue")))

        val expectedPaged: PageImpl<EventDto> = PageImpl(listOf(EventDto(action = "some issue")))

        `when`(this.eventRepository.findAll(any(Pageable::class.java))).thenReturn(pagedEvent)

        this.helper.getPageableContent<EventDto>(0, 1, "asc").also {
            assertThat(it.content).isEqualTo(expectedPaged.content)
        }
    }

}