package com.ddd.training.application;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EcheanceRequest extends AbstractBaseEntity {

	private Date paymentDate;

	private BigDecimal crd;

	protected EcheanceRequest() {
		super();
	}

	public EcheanceRequest(final Date paymentDate, final BigDecimal crd) {
		this.paymentDate = paymentDate;
		setCrd(crd);
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public BigDecimal getCrd() {
		return crd;
	}

	protected void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	protected void setCrd(final BigDecimal crd) {
		this.crd = crd;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final EcheanceRequest other = (EcheanceRequest) obj;

		return new EqualsBuilder() // Equality on the business reference only
				.append(id, other.id) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37) //
				.append(id) //
				.toHashCode();
	}
}
