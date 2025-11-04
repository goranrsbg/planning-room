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

    Validator sut = new Validator();
}
