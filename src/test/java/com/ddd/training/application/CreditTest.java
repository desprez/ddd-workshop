package com.ddd.training.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.ddd.training.domain.credit.Credit;
import com.ddd.training.domain.echeance.EcheanceRequest;
import com.ddd.training.domain.echeance.EcheanceRequestBuilder;

public class CreditTest {

	@Test
	public void should_get_echeances() {
		//GIVEN
		final EcheanceRequest echeanceRequest1 = new EcheanceRequestBuilder().build();
		final EcheanceRequest echeanceRequest2 = new EcheanceRequestBuilder().build();

		final Credit credit = new Credit();
		credit.getEcheanceRequests().add(echeanceRequest1);
		credit.getEcheanceRequests().add(echeanceRequest2);

		//WHEN
		final List<EcheanceRequest> echeanceRequestActive = credit.getEcheanceRequests();

		//THEN
		assertThat(echeanceRequestActive).hasSize(2);

	}
}
