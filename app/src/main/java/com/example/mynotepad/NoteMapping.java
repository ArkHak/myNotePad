package com.example.mynotepad;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoteMapping {
    public static class Fields {
        public final static String ID = "id";
        public final static String DATE = "date";
        public final static String SUBJECT = "subject";
        public final static String DESCRIPTION = "description";
    }

    public static Note toNote(Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);

        Note answer = new Note(
                (String) doc.get(Fields.ID),
                (String) doc.get(Fields.SUBJECT),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.toDate());

        return answer;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.ID, note.getId());
        answer.put(Fields.SUBJECT, note.getSubject());
        answer.put(Fields.DESCRIPTION, note.getDescription());
        answer.put(Fields.DATE, note.getDate());
        return answer;
    }
}