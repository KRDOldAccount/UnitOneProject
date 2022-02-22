package ata.unit.one.project.comparator;

import ata.unit.one.project.models.Person;

import java.util.Comparator;

public class SortByPersonName implements Comparator<Person> {

    @Override
    public int compare(Person firstPerson, Person secondPerson) {
        //TODO: Implement this compare method to properly sort the list of persons
            return firstPerson.getPersonName().compareToIgnoreCase(secondPerson.getPersonName());

    }
}
