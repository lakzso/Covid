package com.covid.statistics;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InfectionRepository extends JpaRepository<Infection, Long> {
   
	
	
	public List<Infection> findByCountry(final Country countryName);
	 
	
}
