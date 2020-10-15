package com.ddd.training.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ddd.training.domain.credit.Credit;
import com.ddd.training.domain.credit.CreditBuilder;
import com.ddd.training.domain.credit.CreditRepositoryImpl;
import com.ddd.training.domain.echeance.EcheanceRequest;
import com.ddd.training.domain.echeance.EcheanceRequestBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CreditRepositoryImplTest {

	@Autowired
	private CreditRepositoryImpl creditRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void find_saved_credit_should_success() {
		// Given
		final EcheanceRequest echeanceRequest = new EcheanceRequestBuilder().withPaymentDate(new Date()).build();
		final Credit credit = new CreditBuilder().withName("find credit test").withMarketDate(new Date()).build();
		credit.getEcheanceRequests().add(echeanceRequest);
		entityManager.persistAndFlush(credit);
		// When
		final Optional<Credit> found = creditRepository.findOne(credit.getId());

		// Then
		assertThat(found.isPresent()).isTrue();
	}

	@Test
	public void save_credit_should_successfully_retreived() {
		// Given
		final EcheanceRequest echeanceRequest = new EcheanceRequestBuilder().withPaymentDate(new Date()).build();
		final Credit credit = new CreditBuilder().withName("Save credit test").withMarketDate(new Date()).build();
		credit.getEcheanceRequests().add(echeanceRequest);

		// When
		creditRepository.save(credit);

		// Then
		assertThat(creditRepository.findAll()).isNotEmpty();
	}


}
