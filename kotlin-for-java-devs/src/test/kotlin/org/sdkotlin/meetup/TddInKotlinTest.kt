package org.sdkotlin.meetup

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.sdkotlin.intro.kotlin._XX_tdd.TddInKotlin

class TddInKotlinTest {

	@Test
	fun `test TDD in Kotlin for smoke`() {

		val tddInKotlin = TddInKotlin()

		assertThat(tddInKotlin.weCanDoIt()).isTrue()
	}
}
