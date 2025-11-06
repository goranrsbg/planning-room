package xyz.jplan.room.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatorTest {

    @ParameterizedTest
    @CsvSource({ "a,true", ",false", "\n,false", " ,false", "\t,false" })
    void isNameValid_TESTnotValid(String name, boolean expected) {
	// GIVEN
	// WHEN
	boolean actual = sut.isNameValid(name);
	// THEN
	assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({ "123456,true", ",false", "12345,false", "123 456,false", "1234567,false" })
    void isRoomIdValid_TESTYesNoValid(String roomId, boolean expected) {
	// GIVEN
	// WHEN
	boolean actual = sut.isRoomIdValid(roomId);
	// THEN
	assertEquals(expected, actual);
    }

    Validator sut = new Validator();
}
