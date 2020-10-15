package com.ddd.training.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Credit extends AbstractBaseEntity {

	private String name;

	private String technicalCode;

	private Date marketDate;

	private Date placeDate;

	private List<EcheanceRequest> echeanceRequests =  new ArrayList<>();

	private List<Currency> currencies = new ArrayList<>();

	public Credit(final String name, final String technicalCode) {
		setName(name);
		setTechnicalCode(technicalCode);
	}

	public Credit() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getTechnicalCode() {
		return technicalCode;
	}

	public void setTechnicalCode(final String technicalCode) {
		this.technicalCode = technicalCode;
	}

	public Date getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(final Date marketDate) {
		this.marketDate = marketDate;
	}

	public Date getPlaceDate() {
		return placeDate;
	}

	public void setPlaceDate(final Date placeDate) {
		this.placeDate = placeDate;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(final List<Currency> currencies) {
		this.currencies = currencies;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Credit other = (Credit) obj;

		return new EqualsBuilder() // Equality on the business reference only
				.append(technicalCode, other.technicalCode) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37) //
				.append(name) //
				.append(technicalCode)
				.toHashCode();
	}

	public List<EcheanceRequest> getEcheanceRequests() {
		return echeanceRequests;
	}

	public void setEcheanceRequests(final List<EcheanceRequest> echeanceRequests) {
		this.echeanceRequests = echeanceRequests;
	}
}
