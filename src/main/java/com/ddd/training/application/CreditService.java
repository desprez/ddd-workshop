package com.ddd.training.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreditService {

	private final DataService dataService;

	private final CreditRepository creditRepository;

	public CreditService(final DataService dataService, final CreditRepository creditRepository) {
		this.dataService = dataService;
		this.creditRepository = creditRepository;
	}

	public List<EcheanceRequest> valuationCredit(final Credit credit, final Date valueDate) {

		final List<EcheanceRequest> echeanceRequestActive = credit.getEcheanceRequests();
		final List<EcheanceRequest> echeanceRequestValuations = new ArrayList<>();

		for (final EcheanceRequest echeanceRequest : echeanceRequestActive) {
			BigDecimal creditValuation = echeanceRequest.getCrd();

			if (!containsFundingCurrencies(credit.getCurrencies())) {
				creditValuation = applyCrossChange(creditValuation, valueDate);
			}

			final EcheanceRequest echeanceRequestValuation = new EcheanceRequestBuilder()
					.withPaymentDate(echeanceRequest.getPaymentDate()).withCrd(creditValuation).build();
			echeanceRequestValuations.add(echeanceRequestValuation);
		}

		return echeanceRequestValuations;
	}

	public void addEcheanceToCredit(final String idProduct, final EcheanceRequest echeanceRequest) {
		final Optional<Credit> credit = creditRepository.findOne(idProduct);
		if (credit.isPresent()) {
			credit.get().getEcheanceRequests().add(echeanceRequest);
		}
	}

	public BigDecimal applyCrossChange(final BigDecimal value, final Date date) {
		final BigDecimal crossChange = dataService.getCrossChange(date);
		if (value != null) {
			return value.divide(crossChange);
		} else {
			return BigDecimal.ZERO;
		}
	}

	public Boolean containsFundingCurrencies(final List<Currency> currencies) {
		final Predicate<Currency> isFundingCurrency = currency -> currency.isFundingCurrency();
		return currencies.stream().anyMatch(isFundingCurrency);
	}

	public long countRemainingEcheanceAfter(final Credit credit, final Date date) {
		final Predicate<EcheanceRequest> endTerm = er -> er.getPaymentDate().after(date);
		return credit.getEcheanceRequests().stream().filter(endTerm).count();
	}

}
