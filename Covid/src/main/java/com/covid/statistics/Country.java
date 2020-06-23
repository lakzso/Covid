package com.covid.statistics;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import antlr.collections.List;

@Entity
@Table(name = "Country")
public class Country {
	@Id
	@GeneratedValue
	private long  Id;
	private String IsoCode;
	private String name;
	private String region;
	private int population;
	 @OneToMany(mappedBy="country")
	   private Set<Infection> Infections;

	public Country() {
		super();
	}
     
	 public void setInfectios(Set<Infection> Infections) {
	        this.Infections = Infections;
	    }
	 
	 
	public Country(String IsoCode, String name, String region, int population) {
		super();
		this.setIsoCode(IsoCode);
		this.setName(name);
		this.setRegion(region);
		this.setPopulation(population);
	}

	public Country(String IsoCode, String name) {
		super();
		this.setIsoCode(IsoCode);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getIsoCode() {
		return IsoCode;
	}

	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	

}
