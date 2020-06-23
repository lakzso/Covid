package com.covid.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    
	public Country findByName(final  String name);
}
