package ata.unit.one.project.comparator;

import ata.unit.one.project.backend.Backend;
import ata.unit.one.project.models.Person;
import ata.unit.one.project.service.PersonService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortByPersonNameTest {

    @Test
    void return_first_item_lower_case() {
        // GIVEN - create person service
        Backend backend = new Backend();
        PersonService personService = new PersonService(backend);

        // WHEN -  call get persons
        Person newPerson = personService.addPerson("aaaaa");
        List<Person> persons = personService.getPersons();

        // THEN
        assertEquals(newPerson, persons.get(0), "Expected persons to be equal");
    }

    @Test
    void return_first_item_upper_case() {
        // GIVEN - create person service
        Backend backend = new Backend();
        PersonService personService = new PersonService(backend);

        // WHEN -  call get persons
        Person newPerson = personService.addPerson("AAAAA");
        List<Person> persons = personService.getPersons();

        // THEN
        assertEquals(newPerson, persons.get(0), "Expected persons to be equal");
    }

    @Test
    void return_last_item_lower_case() {
        // GIVEN - create person service
        Backend backend = new Backend();
        PersonService personService = new PersonService(backend);

        // WHEN -  call get persons
        Person newPerson = personService.addPerson("zzzzz");
        personService.addPerson("aaaaa");
        List<Person> persons = personService.getPersons();

        // THEN
        assertEquals(newPerson, persons.get(persons.size() - 1), "Expected persons to be equal");
    }

    @Test
    void return_last_item_upper_case() {
        // GIVEN - create person service
        Backend backend = new Backend();
        PersonService personService = new PersonService(backend);

        // WHEN -  call get persons
        Person newPerson = personService.addPerson("ZZZZ");
        personService.addPerson("aaaaa");
        List<Person> persons = personService.getPersons();

        // THEN
        assertEquals(newPerson, persons.get(persons.size() - 1), "Expected persons to be equal");
    }
}
