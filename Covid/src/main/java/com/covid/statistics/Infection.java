package com.covid.statistics;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Infection {
	@Id
	@GeneratedValue
	private int Id;
	private Date date;
    private int piece;
    @ManyToOne
    @JoinColumn(name="IsoCode", nullable=false)
    private Country country;
    
    public Infection() {
		super();
	}

	public Infection( Date date, int piece) {
		super();
		this.setDate(date);
	    this.setPiece(piece);
		
	}
     
	public int  getId() {
		return Id;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPiece() {
		return piece;
	}

	public void setPiece(int piece) {
		this.piece = piece;
	}

	
	
	
	
}
