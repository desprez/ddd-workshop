package com.ddd.training.domain.credit;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class CreditRepositoryImpl implements CreditRepository {

	private final CreditJpaRepository jpaRepository;

	public CreditRepositoryImpl(final CreditJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public List<Credit> findAll() {
		return jpaRepository.findAll();
	}

	@Override
	public Optional<Credit> findOne(final String id) {
		return jpaRepository.findById(id);
	}

	@Override
	public void save(final Credit credit) {
		jpaRepository.save(credit);
	}

}
