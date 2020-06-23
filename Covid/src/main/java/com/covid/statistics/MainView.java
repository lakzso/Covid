package com.covid.statistics;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import antlr.collections.List;

@Route
public class MainView extends VerticalLayout {

	private final CountryRepository   crep;
    private final InfectionRepository irep;
	 
	public MainView(CountryRepository crepo,InfectionRepository irepo) {

		this.crep = crepo;
		this.irep=irepo;

		VerticalLayout todosList = new VerticalLayout(); 
		TextField taskField = new TextField(); 
		Button addButton = new Button("Add"); 
		addButton.addClickShortcut(Key.ENTER);

		Select<String> labelSelect = new Select<>();

		Collection<String> countries = new ArrayList<String>();

		
		for (Country i : crep.findAll()) {
			
			countries.add(i.getName());
		}

		
		labelSelect.setItems(countries);
		labelSelect.setLabel("Országok");

		Div country = new Div();
		country.setText("Kiválasztott ország");
		
		Div d=new Div();
		Div popCount=new Div();
		Div infCount=new Div();
		
		
		
		
	
		Grid<Infection> grid = new Grid<>(Infection.class);

	    grid.removeColumnByKey("country");
	
		
		
		labelSelect.addValueChangeListener(
				  event ->  {
					  country.setText("Kiválasztott: " + event.getValue() );
				  			    
					  grid.setItems(irepo.findByCountry(crepo.findByName(event.getValue())) );
				
				  })
				;
		
		add(
				 
		   new H1("Covid"), 
		   new VerticalLayout(labelSelect, country,grid)
		    
		   
		  );
		
		
		
	}
}