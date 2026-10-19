package com.example.broker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** MQTT 5.0 4.7절 본문 예시와 절차서 18·19번 토픽 */
public class TopicFilterTest {

    @Test
    public void procedure18And19() {
        assertTrue(TopicFilter.matches("sensor/+/temp", "sensor/1/temp"));
        assertFalse(TopicFilter.matches("sensor/+/temp", "sensor/1/hum"));
        assertFalse(TopicFilter.matches("sensor/+/temp", "other/x"));
        assertTrue(TopicFilter.matches("sensor/#", "sensor/1/temp"));
        assertTrue(TopicFilter.matches("sensor/#", "sensor/1/hum"));
        assertFalse(TopicFilter.matches("sensor/#", "other/x"));
        assertTrue(TopicFilter.matches("a/1", "a/1"));
        assertTrue(TopicFilter.matches("a/#", "a/1"));
        assertFalse(TopicFilter.matches("a/1", "a/2"));
    }

    @Test
    public void specExamples() {
        assertTrue(TopicFilter.matches("sport/tennis/player1/#", "sport/tennis/player1"));
        assertTrue(TopicFilter.matches("sport/tennis/player1/#", "sport/tennis/player1/score/wimbledon"));
        assertTrue(TopicFilter.matches("sport/#", "sport"));
        assertTrue(TopicFilter.matches("#", "a/b/c"));
        assertTrue(TopicFilter.matches("sport/tennis/+", "sport/tennis/player1"));
        assertFalse(TopicFilter.matches("sport/tennis/+", "sport/tennis/player1/ranking"));
        assertFalse(TopicFilter.matches("sport/+", "sport"));
        assertTrue(TopicFilter.matches("sport/+", "sport/"));
        assertTrue(TopicFilter.matches("+/+", "/finance"));
        assertTrue(TopicFilter.matches("/+", "/finance"));
        assertFalse(TopicFilter.matches("+", "/finance"));
        assertFalse(TopicFilter.matches("#", "$SYS/x"));
        assertFalse(TopicFilter.matches("+/monitor/Clients", "$SYS/monitor/Clients"));
        assertTrue(TopicFilter.matches("$SYS/#", "$SYS/monitor/Clients"));
        assertFalse(TopicFilter.matches("ACCOUNTS", "Accounts"));
    }

    @Test
    public void syntax() {
        assertTrue(TopicFilter.isValidFilter("sport/tennis/#"));
        assertTrue(TopicFilter.isValidFilter("+/tennis/#"));
        assertTrue(TopicFilter.isValidFilter("#"));
        assertTrue(TopicFilter.isValidFilter("+"));
        assertFalse(TopicFilter.isValidFilter("sport/tennis#"));
        assertFalse(TopicFilter.isValidFilter("sport/tennis/#/ranking"));
        assertFalse(TopicFilter.isValidFilter("sport+"));
        assertFalse(TopicFilter.isValidFilter(""));
        assertTrue(TopicFilter.isValidTopic("sensor/1/temp"));
        assertFalse(TopicFilter.isValidTopic("sensor/+/temp"));
        assertFalse(TopicFilter.isValidTopic("sensor/#"));
    }
}
