package xyz.jplan.room.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jakarta.websocket.DecodeException;
import xyz.jplan.room.services.data.Message;

class MessageDecoderTest {

    @Test
    void test_WillDecodeNull() {
	// GIVEN
	String s = null;
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeEmpty() {
	// GIVEN
	String s = "";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeEmpty2() {
	// GIVEN
	String s = "{}";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeValid() {
	// GIVEN
	String s = """
		{
		"action" : "SET_NAME",
		"data" : "450"
		}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertTrue(actual);
    }

    @Test
    void test_WillDecodeValid2() {
	// GIVEN
	String s = """
		{"action":"CREATE_ROOM","data":"450011"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertTrue(actual);
    }

    @Test
    void test_WillDecodeValid3() {
	// GIVEN
	String s = """
		{"data":"Message to test.","action":"SEND_MESSAGE"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertTrue(actual);
    }

    @Test
    void test_WillDecodeValid4() {
	// GIVEN
	String s = """
		{"action":"SEND_MESSAGE","data":"Test message"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertTrue(actual);
    }

    @Test
    void test_WillDecodeNotValid1() {
	// GIVEN
	String s = """
		{"action":"create_room","data":"123456"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeNotValid2() {
	// GIVEN
	String s = """
		{"action":"SET_NAME","datx":"45"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeNotValid3() {
	// GIVEN
	String s = """
		{"action":"CREATE_ROOMm","data":"45a"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeNotValid4() {
	// GIVEN
	String s = """
		{"data":"ASD","dataa":"ASDF"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_DecodeValid1() throws DecodeException {
	// GIVEN
	String s = """
		{"data":"Test message","action":"SEND_MESSAGE"}""";
	// WHEN
	Message actual = sut.decode(s);
	// THEN
	assertEquals("SEND_MESSAGE", actual.getAction());
	assertEquals("Test message", actual.getData());
    }

    MessageDecoder sut = new MessageDecoder();
}
