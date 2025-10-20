package xyz.jplan.room.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
		"from" : "goran.rs.bg",
		"content" : "450"
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
		{"from":"goran.rs.bg","content":"450"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertTrue(actual);
    }

    @Test
    void test_WillDecodeValid3() {
	// GIVEN
	String s = """
		{"from":"goran-rs.bg123","content":"45"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertTrue(actual);
    }

    @Test
    void test_WillDecodeNotValid1() {
	// GIVEN
	String s = """
		{"fromm":"goran-rs.bg123","content":"45"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeNotValid2() {
	// GIVEN
	String s = """
		{"from":"goran-rs.bg123","ccontent":"45"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeNotValid3() {
	// GIVEN
	String s = """
		{"from":"goran-rs.bg123","content":"45a"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    @Test
    void test_WillDecodeNotValid4() {
	// GIVEN
	String s = """
		{"from":"goran/-rs.bg123","content":"45"}""";
	// WHEN
	boolean actual = sut.willDecode(s);
	// THEN
	assertFalse(actual);
    }

    MessageDecoder sut = new MessageDecoder();
}
