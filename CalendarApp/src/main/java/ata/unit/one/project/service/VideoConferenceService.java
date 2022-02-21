package ata.unit.one.project.service;

import ata.unit.one.project.backend.Backend;
import ata.unit.one.project.backend.models.EventDto;
import ata.unit.one.project.backend.models.VideoConferenceLinkDto;
import ata.unit.one.project.conferencelink.ConferenceLinkContext;
import ata.unit.one.project.conferencelink.ConferenceLinkHandler;
import ata.unit.one.project.conferencelink.GoogleConferenceLinkHandler;
import ata.unit.one.project.conferencelink.ZoomConferenceLinkHandler;
import ata.unit.one.project.models.VideoConferenceLink;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.String.format;

public class VideoConferenceService {
    private final Backend backend;

    public VideoConferenceService(Backend backend) {
        this.backend = backend;
    }

    public VideoConferenceLink getEventConferenceLink(String eventId) {
        EventDto eventResponse = backend.getEvent(eventId);
        VideoConferenceLinkDto videoConferenceResponse = backend.getEventVideoLink(eventId);

        if (eventResponse == null || videoConferenceResponse == null) {
            throw new IllegalArgumentException("Conference link does not exist");
        }
        return VideoConferenceLink
                .builder()
                .setEventId(videoConferenceResponse.getEventId())
                .setMeetingCode(videoConferenceResponse.getMeetingCode())
                .setConferenceLinkProvider(eventResponse.getVideoConferenceProvider())
                .build();
    }

    public VideoConferenceLink createEventConferenceLink(String eventId) {
        EventDto eventResponse = backend.getEvent(eventId);
        if (eventResponse == null) {
            throw new IllegalArgumentException("Event does not exist");
        }
        VideoConferenceLinkDto videoConferenceResponse = backend.getEventVideoLink(eventId);
        if(videoConferenceResponse != null){
            throw new IllegalArgumentException("Event already has video conference link");
        }
        VideoConferenceLinkDto response = backend.createVideoLink(eventId);
        return VideoConferenceLink
                .builder()
                .setEventId(response.getEventId())
                .setMeetingCode(response.getMeetingCode())
                .setConferenceLinkProvider(eventResponse.getVideoConferenceProvider())
                .build();
    }

    public void removeEventConferenceLink(String eventId) {
        backend.removeEventVideoLink(eventId);
    }

    public void joinVideoConference(String eventId) {

        EventDto eventResponse = backend.getEvent(eventId);
        VideoConferenceLinkDto videoConferenceResponse = backend.getEventVideoLink(eventId);
        if (eventResponse == null || videoConferenceResponse == null) {
            throw new IllegalArgumentException("Conference link does not exist");
        }

        ConferenceLinkHandler handler;
        if (eventResponse.getVideoConferenceProvider().equalsIgnoreCase("google")) {
            handler = new GoogleConferenceLinkHandler();
        }
        else if (eventResponse.getVideoConferenceProvider().equalsIgnoreCase("zoom")) {
            handler = new ZoomConferenceLinkHandler();
        }
        else {
           handler = new ZoomConferenceLinkHandler();
        }
        ConferenceLinkContext context = new ConferenceLinkContext(handler);
        context.join(videoConferenceResponse.getMeetingCode());

//
//
//
//        try {
//            if (eventResponse.getVideoConferenceProvider().equalsIgnoreCase("google")) {
//                String formattedCode = format("%s-%s-%s", videoConferenceResponse.getMeetingCode().substring(0, 3), videoConferenceResponse.getMeetingCode().substring(3, 6), videoConferenceResponse.getMeetingCode().substring(6));
//                Desktop.getDesktop().browse(new URL(format("https://meet.google.com", formattedCode)).toURI());
//            }
//            if (eventResponse.getVideoConferenceProvider().equalsIgnoreCase("zoom")) {
//                Desktop.getDesktop().browse(new URL(format("https://zoom.us/j/%s", videoConferenceResponse.getMeetingCode())).toURI());
//            }
//        } catch (IOException | URISyntaxException e) {
//            throw new IllegalArgumentException("Could not join video conference");
//        }
    }
}