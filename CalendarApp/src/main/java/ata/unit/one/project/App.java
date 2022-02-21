/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ata.unit.one.project;

import ata.unit.one.project.backend.Backend;
import ata.unit.one.project.cmd.UserHandler;
import ata.unit.one.project.models.Event;
import ata.unit.one.project.models.Person;
import ata.unit.one.project.models.VideoConferenceLink;
import ata.unit.one.project.service.EventService;
import ata.unit.one.project.service.PersonService;
import ata.unit.one.project.service.VideoConferenceService;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.document.TableRowStyle;

import java.util.List;

public class App {

    private final PersonService personService;
    private final EventService eventService;
    private final UserHandler userHandler;
    private final VideoConferenceService videoConferenceService;

    public static final String QUERY_PERSONS_COMMAND = "PERSONS";
    public static final String QUERY_EVENTS_COMMAND = "EVENTS";
    public static final String QUERY_CONFERENCE_LINKS_COMMAND = "CONFERENCELINKS";

    public static final String GET_COMMAND = "GET";
    public static final String CREATE_COMMAND = "CREATE";
    public static final String SHARE_COMMAND = "SHARE";
    public static final String DELETE_COMMAND = "DELETE";
    public static final String JOIN_COMMAND = "JOIN";
    public static final String EXIT_COMMAND = "EXIT";

    public static final String QUIT_COMMAND = "QUIT";

    public App(PersonService personService, EventService eventService, VideoConferenceService videoConferenceService, UserHandler userHandler) {
        this.personService = personService;
        this.eventService = eventService;
        this.userHandler = userHandler;
        this.videoConferenceService = videoConferenceService;
    }

    public static void main(String[] args) {
        Backend backend = new Backend();
        PersonService personService = new PersonService(backend);
        EventService eventService = new EventService(backend);
        VideoConferenceService videoConferenceService = new VideoConferenceService(backend);

        App app = new App(personService, eventService, videoConferenceService, new UserHandler());
        while (true) {
            try {
                if (!app.handleMenu()) break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public boolean handleMenu() {
        showMenu();
        String command = userHandler.getString("\nWhat would you like to do? Please type a command.");
        if (command.equalsIgnoreCase(QUIT_COMMAND)) {
            System.out.println("Thank you");
            return false;
        }
        handleSubMenuSelection(command);
        return true;
    }

    public void handleSubMenuSelection(String command) {
        if (command.equalsIgnoreCase(QUERY_PERSONS_COMMAND)) {
            handlePersonsSubMenu();
            return;
        }
        if (command.equalsIgnoreCase(QUERY_EVENTS_COMMAND)) {
            handleEventMenu();
            return;
        }
        if (command.equalsIgnoreCase(QUERY_CONFERENCE_LINKS_COMMAND)) {
            handleConferenceLinksSubMenu();
            return;
        }
        throw new IllegalArgumentException("Command not supported");
    }

    public void handleEventMenu() {
        showEventMenu();
        while (true) {
            String newCommand = userHandler.getString("\nWhat would you like to do? Please type a command.");
            if (newCommand.equalsIgnoreCase(GET_COMMAND)) {
                String personId = userHandler.getString("Enter the person's id");
                System.out.println(queryPersonMeetingEvent(personId));
            } else if (newCommand.equalsIgnoreCase(SHARE_COMMAND)) {
                String ownerPersonId = userHandler.getString("Enter the owner person id");
                String personId = userHandler.getString("Enter the  person id");
                System.out.println(shareCalendarEvents(ownerPersonId, personId));
            } else if (newCommand.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }
        }
    }

    public void handlePersonsSubMenu() {
        showPersonMenu();
        while (true) {
            String newCommand = userHandler.getString("\nWhat would you like to do? Please type a command.");
            if (newCommand.equalsIgnoreCase(GET_COMMAND)) {
                System.out.println(queryPersons());
            } else if (newCommand.equalsIgnoreCase(CREATE_COMMAND)) {
                String eventId = userHandler.getString("Enter the person's name");
                System.out.println(createPersons(eventId));
            } else if (newCommand.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }
        }
    }

    private void handleConferenceLinksSubMenu() {
        showConferenceLinkMenu();
        while (true) {
            String newCommand = userHandler.getString("\nWhat would you like to do? Please type a command.");
            if (newCommand.equalsIgnoreCase(GET_COMMAND)) {
                String eventId = userHandler.getString("Enter the event id");
                System.out.println(queryConferenceLink(eventId));
            } else if (newCommand.equalsIgnoreCase(CREATE_COMMAND)) {
                String eventId = userHandler.getString("Enter the event id");
                System.out.println(createConferenceLink(eventId));
            } else if (newCommand.equalsIgnoreCase(DELETE_COMMAND)) {
                String eventId = userHandler.getString("Enter the event id");
                System.out.println(deleteConferenceLink(eventId));
            } else if (newCommand.equalsIgnoreCase(JOIN_COMMAND)) {
                String eventId = userHandler.getString("Enter the event id");
                System.out.println(joinConferenceLink(eventId));
            } else if (newCommand.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }
        }
    }

    private String queryPersons() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Person Id", "Name");
        table.addRule(TableRowStyle.NORMAL);
        for (Person person : personService.getPersons()) {
            table.addRow(person.getPersonId(), person.getPersonName());
            table.addRule();
        }

        return table.render(150);
    }

    private String createPersons(String personName) {
        try {
            AsciiTable table = new AsciiTable();
            table.addRule();
            table.addRow("Person Id", "Name");
            table.addRule();
            personService.addPerson(personName);
            for (Person person : personService.getPersons()) {
                table.addRow(person.getPersonId(), person.getPersonName());
                table.addRule();
            }
            return table.render(150);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    private String queryPersonMeetingEvent(String personId) {
        try {
            List<Event> events = eventService.getMeetingEvents(personId);
            return displayEvents(events);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    private String displayEvents(List<Event> events) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Event Id", "Event Title", "Start Time", "End Time");
        table.addRule();
        for (Event event : events) {
            //print event in table
            table.addRow(event.getEventId(), event.getEventTitle(), event.getStartTime(), event.getEndTime());
            table.addRule();
        }
        return table.render(150);
    }

    private String shareCalendarEvents(String ownerPersonId, String personId) {
        try {
            List<Event> events = eventService.shareCalendarEvents(ownerPersonId, personId);
            return displayEvents(events);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }


    private String queryConferenceLink(String eventId) {
        try {
            AsciiTable table = new AsciiTable();
            table.addRule();
            table.addRow("Event Id", "Meeting Code");
            table.addRule();
            VideoConferenceLink conferenceLink = videoConferenceService.getEventConferenceLink(eventId);
            table.addRow(conferenceLink.getEventId(), conferenceLink.getMeetingCode());
            table.addRule();
            return table.render(150);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    private String createConferenceLink(String eventId) {
        try {
            AsciiTable table = new AsciiTable();
            table.addRule();
            table.addRow("Event Id", "Provider", "Meeting Code");
            table.addRule();
            VideoConferenceLink conferenceLink = videoConferenceService.createEventConferenceLink(eventId);
            table.addRow(conferenceLink.getEventId(), conferenceLink.getConferenceLinkProvider(), conferenceLink.getMeetingCode());
            table.addRule();
            return table.render(150);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }


    private String joinConferenceLink(String eventId) {
        try {
            videoConferenceService.joinVideoConference(eventId);
            return "Connecting...";
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    private String deleteConferenceLink(String eventId) {
        videoConferenceService.removeEventConferenceLink(eventId);
        return "Done";
    }

    private static void showMenu() {
        System.out.println("Welcome to Simple Calendar app.");
        System.out.println(String.format("Enter %s to see persons menu.", QUERY_PERSONS_COMMAND));
        System.out.println(String.format("Enter %s to see event menu.", QUERY_EVENTS_COMMAND));
        System.out.println(String.format("Enter %s to see conference link menu.", QUERY_CONFERENCE_LINKS_COMMAND));
        System.out.println(String.format("Enter %s to exit the calendar application.", QUIT_COMMAND));
    }

    private static void showEventMenu() {
        System.out.println("Welcome to Event Menu.");
        System.out.println(String.format("Enter %s to retrieve events", GET_COMMAND));
        System.out.println(String.format("Enter %s to copy event", SHARE_COMMAND));
        System.out.println(String.format("Enter %s to go to main menu.", EXIT_COMMAND));
    }

    private static void showPersonMenu() {
        System.out.println("Welcome to Person Menu.");
        System.out.println(String.format("Enter %s to add person", CREATE_COMMAND));
        System.out.println(String.format("Enter %s to retrieve persons", GET_COMMAND));
        System.out.println(String.format("Enter %s to go to main menu.", EXIT_COMMAND));
    }

    private static void showConferenceLinkMenu() {
        System.out.println("Welcome to Conference Link Menu.");
        System.out.println(String.format("Enter %s to add a conference link", CREATE_COMMAND));
        System.out.println(String.format("Enter %s to retrieve event conference link", GET_COMMAND));
        System.out.println(String.format("Enter %s to remove event conference link.", DELETE_COMMAND));
        System.out.println(String.format("Enter %s to join video conference.", JOIN_COMMAND));
        System.out.println(String.format("Enter %s to go to main menu.", EXIT_COMMAND));
    }
}