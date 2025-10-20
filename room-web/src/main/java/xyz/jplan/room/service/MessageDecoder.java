package xyz.jplan.room.service;

import java.util.regex.Pattern;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class MessageDecoder implements Decoder.Text<Message> {

    private static final Jsonb jsonb = JsonbBuilder.create();
    private static final Pattern pattern = Pattern
	    .compile("\\{[\\n ]*\"from\" ?: ?\"[A-Za-z\\.0-9-+]*\",[\\n ]*\"content\" ?: ?\"[0-9]*\"[\\n ]*}");

    @Override
    public Message decode(String s) throws DecodeException {
	return jsonb.fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
	return s != null && pattern.matcher(s).matches();
    }

}
