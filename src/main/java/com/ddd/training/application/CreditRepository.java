package com.ddd.training.application;

import java.util.List;
import java.util.Optional;

public interface CreditRepository {

	public List<Credit> findAll();

	public Optional<Credit> findOne(final String id) ;

	public void save(Credit credit);

}
