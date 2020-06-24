package com.covid.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;

import antlr.collections.List;

@Route
public class MainView extends VerticalLayout {

	private final CountryRepository crep;
	private final InfectionRepository irep;

	private HorizontalLayout createHeader() {
		H1 logo = new H1("Vaadin CRM");
	    logo.addClassName("logo");

	    Anchor logout = new Anchor("logout", "Log out"); 



	    HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout); 


	    header.expand(logo); 
 
		//header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
	    header.setWidth("100%");
	    header.addClassName("header");

	    return header;
	}
	
	public MainView(CountryRepository crepo, InfectionRepository irepo) {

		this.crep = crepo;
		this.irep = irepo;

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

		Grid<Infection> grid = new Grid<>(Infection.class);
		grid.removeColumnByKey("country");
		grid.removeColumnByKey("piece");
		grid.removeColumnByKey("date");

		Grid.Column<Infection> dateColumn = grid.addColumn(Infection::getDate).setHeader("Dátum");
		Grid.Column<Infection> infectionCountColumn = grid.addColumn(Infection::getPiece)
				.setHeader("Fertőzöttek száma");

		Binder<Infection> binder = new Binder<>(Infection.class);
		Editor<Infection> editor = grid.getEditor();
		editor.setBinder(binder);
		editor.setBuffered(true);

		Div validationStatus = new Div();
		validationStatus.setId("validation");

		// fertőzöttek száma validáció

		TextField infectionCountField = new TextField();
		binder.forField(infectionCountField)
				.withConverter(new StringToIntegerConverter("A fertőzöttek száma csak szám lehet"))
				.withStatusLabel(validationStatus).bind("piece");

		infectionCountColumn.setEditorComponent(infectionCountField);
		
		
		//Módosítás gombok
		Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

		Grid.Column<Infection> editorColumn = grid.addComponentColumn(infection -> {
			Button edit = new Button("Edit");
			edit.addClassName("edit");
			edit.addClickListener(e -> {
				editor.editItem(infection);
				infectionCountField.focus();
			});
			edit.setEnabled(!editor.isOpen());
			editButtons.add(edit);
			return edit;
		});

		editor.addOpenListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor.isOpen())));
		editor.addCloseListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor.isOpen())));

		Button save = new Button("Save", e -> editor.save());
		save.addClassName("save");

		Button cancel = new Button("Cancel", e -> editor.cancel());
		cancel.addClassName("cancel");

		grid.getElement().addEventListener("keyup", event -> editor.cancel())
				.setFilter("event.key === 'Escape' || event.key === 'Esc'");

		Div buttons = new Div(save, cancel);
		editorColumn.setEditorComponent(buttons);

		editor.addSaveListener(event -> {
			// event.getItem().setPiece(event.getItem().getPiece());

		});

		
		
		labelSelect.addValueChangeListener(event -> {
			country.setText("Kiválasztott: " + event.getValue());

			grid.setItems(irepo.findByCountry(crepo.findByName(event.getValue())));

		});

		add(
				createHeader(),
				new H1("Covid"), new VerticalLayout(labelSelect, country, grid)

		);

	}
}