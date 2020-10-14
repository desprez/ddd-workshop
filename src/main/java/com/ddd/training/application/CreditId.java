package com.ddd.training.application;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

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
	public boolean equals(final Object object) {
		boolean equalObjects = false;

		if (object != null && this.getClass() == object.getClass()) {
			final CreditId typedObject = (CreditId) object;
			equalObjects = referenceCode.equals(typedObject.referenceCode);
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(referenceCode);
	}

}
