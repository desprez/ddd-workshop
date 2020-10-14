package com.ddd.training.application;

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
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Currency)) {
			return false;
		}

		final Currency currency = (Currency) o;

		if (!isoCode.equals(currency.isoCode)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return isoCode.hashCode();
	}

	public boolean isFundingCurrency() {
		return EUR_ISO.equals(isoCode) || USD_ISO.equals(isoCode);
	}
}
