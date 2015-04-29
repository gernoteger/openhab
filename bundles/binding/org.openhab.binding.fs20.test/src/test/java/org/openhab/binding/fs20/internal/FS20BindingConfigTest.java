package org.openhab.binding.fs20.internal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openhab.binding.fs20.FS20BindingConfig;

public class FS20BindingConfigTest {

    @Test
    public void testNegativeCompletelyNuts() {
	FS20BindingConfig config = FS20BindingConfig.parse("xhu");
	assertNull(config);
    }

    public void testNegativeEmpty() {
	FS20BindingConfig config = FS20BindingConfig.parse("");
	assertNull(config);
    }

    @Test
    public void testNegativeWrongLength() {
	FS20BindingConfig config = FS20BindingConfig.parse("A97131B");
	assertNull(config);
    }

    @Test
    public void testNegativeWrongCharacters() {
	FS20BindingConfig config = FS20BindingConfig.parse("AX7131");
	assertNull(config);
    }

    @Test
    public void testSimpleAdressNoDuration() {
	FS20BindingConfig config = FS20BindingConfig.parse("A97131");
	assertEquals(config.getAddress(), "A97131");
	assertNull(config.getMoveDurationMillis());
    }

    @Test
    public void testSimpleAdressWithConfigFull() {
	FS20BindingConfig config = FS20BindingConfig.parse("A97131;t=100ms");
	assertNotNull(config);
	assertEquals(config.getMoveDurationMillis(), Integer.valueOf(100));
    }

    @Test
    public void testSimpleAdressWithConfigFullAndBlanks() {
	FS20BindingConfig config = FS20BindingConfig
		.parse("A97131 ;  t = 100 ms  ");
	assertNotNull(config);
	assertEquals(config.getMoveDurationMillis(), Integer.valueOf(100));
    }
}
