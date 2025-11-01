package xyz.jplan.room.services;

import java.util.regex.Pattern;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import xyz.jplan.room.services.data.Message;

class MessageDecoder implements Decoder.Text<Message> {

    private static final Jsonb jsonb = JsonbBuilder.create();
    private static final Pattern pattern = Pattern.compile(
	    "\\{[\n ]*\"action\" ?: ?\"[A-Z_]+\",[\n ]*\"data\" ?: ?\".{1,126}\"[\n ]*}|\\{[\n ]*\"data\" ?: ?\".{1,126}\",[\n ]*\"action\" ?: ?\"[A-Z_]+\"}");

    @Override
    public Message decode(String s) throws DecodeException {
	return jsonb.fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
	return s != null && pattern.matcher(s).matches();
    }

}
