package br.com.jpttrindade.calendarview.data;

/**
 * Created by jpttrindade on 10/09/16.
 */
public class Day {
    public int value;
    public boolean hasEvent;

    public Day(int value) {
        this.value = value;
    }

    public Day(int value, boolean hasEvent) {
        this(value);
        this.hasEvent = hasEvent;
    }
}
