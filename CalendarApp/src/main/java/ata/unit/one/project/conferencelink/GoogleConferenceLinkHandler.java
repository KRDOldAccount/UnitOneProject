package ata.unit.one.project.conferencelink;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.String.format;

public class GoogleConferenceLinkHandler implements ConferenceLinkHandler {
    @Override
    public void join(String meetingCode) {
        try {
            Desktop.getDesktop().browse(new URL("https://meet.google.com").toURI());
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(("couldn't join video conference"));
        }
    }
}
