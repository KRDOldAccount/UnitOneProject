package ata.unit.one.project.service;

import ata.unit.one.project.backend.Backend;
import ata.unit.one.project.models.Event;
import ata.unit.one.project.models.Person;
import ata.unit.one.project.models.VideoConferenceLink;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VideoConferenceServiceTest {
    @Test
    void create_event_conference_link() {
        // GIVEN - create event service
        Backend backend = new Backend();
        EventService eventService = new EventService(backend);
        PersonService personService = new PersonService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // WHEN - call get create event link
        Person person = personService.getPersons().get(0);
        Event event = eventService.getMeetingEvents(person.getPersonId()).get(0);
        VideoConferenceLink conferenceLink;
        if (videoConferenceService.getEventConferenceLink(event.getEventId()) == null) {
            conferenceLink = videoConferenceService.createEventConferenceLink(event.getEventId());
        } else {
            conferenceLink = videoConferenceService.getEventConferenceLink(event.getEventId());
        }

        // THEN
        assertNotNull(conferenceLink.getMeetingCode(), "Expected conference link to not be null");
    }

    @Test
    void create_event_conference_link_multiple_throws_illegal_argument_exception() {
        // GIVEN - create event service
        Backend backend = new Backend();
        EventService eventService = new EventService(backend);
        PersonService personService = new PersonService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // WHEN - call get create event link
        Person person = personService.getPersons().get(0);
        Event event = eventService.getMeetingEvents(person.getPersonId()).get(0);
        VideoConferenceLink conferenceLink = videoConferenceService.createEventConferenceLink(event.getEventId());

        // THEN
        assertNotNull(conferenceLink.getMeetingCode(), "Expected conference link to not be null");
        assertThrows(IllegalArgumentException.class, () -> videoConferenceService.createEventConferenceLink(event.getEventId()), "Expected  to throw IllegalArgumentException");
    }

    //TODO: Finish the implementation of this test case
    @Test
    void return_conference_link_already_created() {
        // GIVEN - create event service
        Backend backend = new Backend();
        EventService eventService = new EventService(backend);
        PersonService personService = new PersonService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // WHEN - call get create event link
        Person person = personService.getPersons().get(0);
        Event event = eventService.getMeetingEvents(person.getPersonId()).get(0);
        VideoConferenceLink conferenceLink = videoConferenceService.createEventConferenceLink(event.getEventId());
        // THEN
        //assertEquals(conferenceLink.getMeetingCode(), ???, "Expected conference link to be the same");
    }

    //TODO: Finish the implementation of this test case
    @Test
    void delete_conference_link_already_created() {
        // GIVEN - create event service
        Backend backend = new Backend();
        EventService eventService = new EventService(backend);
        PersonService personService = new PersonService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // WHEN - call get create event link
        Person person = personService.getPersons().get(0);
        Event event = eventService.getMeetingEvents(person.getPersonId()).get(0);
        VideoConferenceLink conferenceLink = videoConferenceService.createEventConferenceLink(event.getEventId());

        // THEN
        assertNotNull(conferenceLink.getMeetingCode(), "Expected conference link to not be null");
        // ???
        assertThrows(IllegalArgumentException.class, () -> videoConferenceService.getEventConferenceLink(event.getEventId()), "Expected to throw IllegalArgumentException");
    }

    @Test
    void create_conference_link_non_existing_event() {
        // GIVEN - create event service
        Backend backend = new Backend();
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // THEN
        assertThrows(IllegalArgumentException.class, () -> videoConferenceService.createEventConferenceLink(UUID.randomUUID().toString()), "Expected  to throw IllegalArgumentException");
    }

    //TODO: Finish the implementation of this test case
    @Test
    void get_conference_link_non_existing_event() {
        // GIVEN - create event service
        // ???

        // THEN
        // ???
        fail("Remove this line and implement the test case");
    }

    @Test
    void join_conference_link_non_existing_event() {
        // GIVEN - create event service
        Backend backend = new Backend();
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // THEN
        assertThrows(IllegalArgumentException.class, () -> videoConferenceService.joinVideoConference(UUID.randomUUID().toString()), "Expected  to throw IllegalArgumentException");
    }


    @Test
    void get_conference_link_non_existing_conference_link() {
        // GIVEN - create event service
        Backend backend = new Backend();
        EventService eventService = new EventService(backend);
        PersonService personService = new PersonService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // WHEN
        Person person = personService.getPersons().get(0);


        // THEN
        assertThrows(IllegalArgumentException.class, () -> videoConferenceService.getEventConferenceLink(UUID.randomUUID().toString()), "Expected  to throw IllegalArgumentException");
    }

    //TODO: Finish the implementation of this test case
    @Test
    void join_conference_link_non_existing_conference_link() {
        // GIVEN - create event service
        Backend backend = new Backend();
        EventService eventService = new EventService(backend);
        PersonService personService = new PersonService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        // WHEN
        Person person = personService.getPersons().get(0);
        Event event = eventService.getMeetingEvents(person.getPersonId()).get(0);

        // THEN
        // ???
    }
}
