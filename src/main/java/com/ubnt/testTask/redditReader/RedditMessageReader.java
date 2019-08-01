package com.ubnt.testTask.redditReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Read and compose reddit message from stream
 *
 */
public class RedditMessageReader extends BufferedReader {

    private static final String ID_PREFIX = "id: ";
    private static final String EVENT_PREFIX = "event: ";
    private static final String DATA_PREFIX = "data: ";

    private static final int ID_PREFIX_LEN = ID_PREFIX.length();
    private static final int EVENT_PREFIX_LEN = EVENT_PREFIX.length();
    private static final int DATA_PREFIX_LEN = DATA_PREFIX.length();

    private ObjectMapper objectMapper;

    public RedditMessageReader(Reader in) {
        super(in);
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }

    public RedditMessage readMessage() throws IOException {
        try {
            String line = readLine();
            while (line != null && !line.startsWith(ID_PREFIX)) {
                line = readLine();
            }
            Long id = Long.parseLong(line.substring(ID_PREFIX_LEN));
            String eventLine = readLine();
            if (!eventLine.startsWith(EVENT_PREFIX)) {
                throw new IOException("Can't read event type from stream " + eventLine);
            }
            String event = eventLine.substring(EVENT_PREFIX_LEN);
            String dataLine = readLine();
            if (!dataLine.startsWith(DATA_PREFIX)) {
                throw new IOException("Can't read JSON data from stream " + dataLine);
            }

            String data = dataLine.substring(DATA_PREFIX_LEN);
            RedditMessagePayload payload = objectMapper.readValue(data.getBytes(), RedditMessagePayload.class);
            if (payload.getSubredditId() == null) {
                // not subreddit event, keepalive, read next
                return readMessage();
            }
            RedditMessage msg = new RedditMessage(id, event, payload.getSubredditId(), data);
            return msg;
        } catch (Exception e) {
            throw new IOException("RedditMessageReader error occured", e);
        }
    }

}
