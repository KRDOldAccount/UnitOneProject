package ata.unit.one.project.conferencelink;

public class ConferenceLinkContext {
    private ConferenceLinkHandler handler;

    public ConferenceLinkContext(ConferenceLinkHandler handler) {
        this.handler = handler;
    }

    public void join(String meetingCode) {
        handler.join(meetingCode);
    }
}
