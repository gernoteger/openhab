package org.openhab.binding.fs20.internal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.types.Command;

public class FS20CommandHelperTest {
    @Test
    public void testConvertHABCommandToFS20Command() {
	Command command = new PercentType(80);
	FS20Command fs20Command = FS20CommandHelper
		.convertHABCommandToFS20Command(command);

	// Test for issue: FS20 - Converting Value to raw-message fails #1635
	// see: https://github.com/openhab/openhab/issues/1635

	assertFalse(fs20Command.equals(FS20Command.UNKOWN));
	assertNotNull(fs20Command.toString());

	// excepted value:
	assertEquals(fs20Command, FS20Command.DIM_12);
    }
}
