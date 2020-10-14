package com.ddd.training.application;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Currency extends AbstractBaseEntity {

	public static final String EUR_ISO = "EUR";

	public static final String USD_ISO = "USD";

	private String name;

	private String isoCode;

	protected Currency() {
		super();
	}

	public Currency(final String name, final String isoCode) {
		this.name = name;
		this.isoCode = isoCode;
	}

	public String getName() {
		return name;
	}

	public String getIsoCode() {
		return isoCode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Currency other = (Currency) obj;

		return new EqualsBuilder() // Equality on the business reference only
				.append(isoCode, other.isoCode) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return isoCode.hashCode();
	}

	public boolean isFundingCurrency() {
		return EUR_ISO.equals(isoCode) || USD_ISO.equals(isoCode);
	}
}
