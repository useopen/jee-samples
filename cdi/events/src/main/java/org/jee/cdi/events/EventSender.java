package org.jee.cdi.events;

/**
 * @author Radim Hanus
 */
public interface EventSender {
    void send(String message);
}
