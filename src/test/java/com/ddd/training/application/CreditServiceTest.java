package com.ddd.training.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ddd.training.application.Credit;
import com.ddd.training.application.CreditBuilder;
import com.ddd.training.application.CreditRepository;
import com.ddd.training.application.CreditService;
import com.ddd.training.application.Currency;
import com.ddd.training.application.DataService;
import com.ddd.training.application.EcheanceRequest;
import com.ddd.training.application.EcheanceRequestBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CreditServiceTest {

	private CreditService creditService;

	@Mock
	private DataService dataService;

	@Mock
	private CreditRepository creditRepository;

	@Before
	public void init() throws Exception {
		creditService = new CreditService(dataService, creditRepository);
	}

	@Test
	public void should_be_true_when_contains_funding_currencies() {
		final Currency euro = new Currency("EURO", Currency.EUR_ISO);
		final Currency us = new Currency("US", Currency.USD_ISO);

		final Boolean result = creditService.containsFundingCurrencies(Arrays.asList(euro, us));

		assertThat(result).isTrue();
	}

	@Test
	public void should_be_false_when_no_contains_funding_currencies() {
		final Currency gpb = new Currency("GPB", "GPB");

		final Boolean result = creditService.containsFundingCurrencies(Arrays.asList(gpb));

		assertThat(result).isFalse();
	}

	@Test
	public void should_add_echeance_to_product() {
		// Given
		final Credit credit = new CreditBuilder().build();

		final EcheanceRequest echeance = new EcheanceRequestBuilder().build();

		final String productId = "11111";
		when(creditRepository.findOne(productId)).thenReturn(Optional.of(credit));

		// When
		creditService.addEcheanceToCredit(productId, echeance);
		// Then

		assertThat(credit.getEcheanceRequests()).hasSize(1).contains(echeance);
	}

	@Test
	public void should_return_remaining_echeance_after_date() {
		// Given
		final Credit credit = new CreditBuilder().build();

		credit.getEcheanceRequests().add(new EcheanceRequest(toDate(2014, 5, 1), new BigDecimal("1500")));
		credit.getEcheanceRequests().add(new EcheanceRequest(toDate(2014, 6, 1), new BigDecimal("1000")));
		credit.getEcheanceRequests().add(new EcheanceRequest(toDate(2014, 7, 1), new BigDecimal("500")));
		credit.getEcheanceRequests().add(new EcheanceRequest(toDate(2014, 8, 1), new BigDecimal("0")));

		// When
		final long result = creditService.countRemainingEcheanceAfter(credit, toDate(2014, 6, 2));

		// Then
		assertThat(result).isEqualTo(2);
	}

	@Test
	public void should_apply_cross_change() throws Exception {
		// Given
		final Date date = toDate(2014, 6, 2);
		when(dataService.getCrossChange(date)).thenReturn(BigDecimal.valueOf(2));

		// When
		final BigDecimal result = creditService.applyCrossChange(BigDecimal.TEN, date);

		// Then
		assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(5));

	}

	private Date toDate(final int year, final int month, final int day) {
		final Instant instant = LocalDateTime.of(year, month, day, 0, 0).toInstant(ZoneOffset.UTC);
		return Date.from(instant);
	}
}
