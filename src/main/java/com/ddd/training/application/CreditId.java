package com.ddd.training.application;

import java.util.UUID;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class CreditId {

	private String referenceCode;

	public CreditId(final String id, final String referenceCode) {
		setReferenceCode(referenceCode);
	}

	protected CreditId() {
		super();
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	private void setReferenceCode(final String referenceCode) {
		Validate.notBlank(referenceCode, "Reference code cannot be null or empty");
		this.referenceCode = referenceCode;
	}

	protected void validateId(final String id) {
		try {
			UUID.fromString(id);
		} catch (final Exception e) {
			throw new IllegalArgumentException("The id has an invalid format.");
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final CreditId other = (CreditId) obj;

		return new EqualsBuilder() // Equality on the business reference only
				.append(referenceCode, other.referenceCode) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37) //
				.append(referenceCode) //
				.toHashCode();
	}

}
