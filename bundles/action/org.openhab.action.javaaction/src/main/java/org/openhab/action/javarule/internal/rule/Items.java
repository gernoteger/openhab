package org.openhab.action.javarule.internal.rule;

import org.openhab.action.javarule.internal.AbstractItems;
import org.openhab.action.javarule.items.ColorItemProxy;
import org.openhab.action.javarule.items.GroupItemProxy;
import org.openhab.action.javarule.items.NumberItemProxy;
import org.openhab.action.javarule.items.RollershutterItemProxy;
import org.openhab.action.javarule.items.StringItemProxy;
import org.openhab.action.javarule.items.SwitchItemProxy;

public abstract class Items extends AbstractItems {

	// ========== copy generated code below ==========

	static GroupItemProxy gFS20 = new GroupItemProxy("gFS20");
	static GroupItemProxy gFS20Switches = new GroupItemProxy("gFS20Switches");
	static GroupItemProxy gSwitch1 = new GroupItemProxy("gSwitch1");
	static SwitchItemProxy sw2_1 = new SwitchItemProxy("sw2_1");
	static SwitchItemProxy sw2_2 = new SwitchItemProxy("sw2_2");
	static GroupItemProxy gSwitchBig = new GroupItemProxy("gSwitchBig");
	static SwitchItemProxy sw1_1 = new SwitchItemProxy("sw1_1");
	static SwitchItemProxy sw1_2 = new SwitchItemProxy("sw1_2");
	static SwitchItemProxy sw1_3 = new SwitchItemProxy("sw1_3");
	static SwitchItemProxy sw1_4 = new SwitchItemProxy("sw1_4");
	static SwitchItemProxy sw1_5 = new SwitchItemProxy("sw1_5");
	static SwitchItemProxy sw1_6 = new SwitchItemProxy("sw1_6");
	static SwitchItemProxy sw1_7 = new SwitchItemProxy("sw1_7");
	static SwitchItemProxy sw1_8 = new SwitchItemProxy("sw1_8");
	static GroupItemProxy gKlingel = new GroupItemProxy("gKlingel");
	static SwitchItemProxy klingel1 = new SwitchItemProxy("klingel1");
	static GroupItemProxy gShutter = new GroupItemProxy("gShutter");
	static GroupItemProxy gShutterUI = new GroupItemProxy("gShutterUI");
	static GroupItemProxy gShutWZ_West = new GroupItemProxy("gShutWZ_West");
	static GroupItemProxy gShutWZ_Sued = new GroupItemProxy("gShutWZ_Sued");
	static SwitchItemProxy shutGaeste = new SwitchItemProxy("shutGaeste");
	static SwitchItemProxy shutBad = new SwitchItemProxy("shutBad");
	static SwitchItemProxy shutSchlaf = new SwitchItemProxy("shutSchlaf");
	static GroupItemProxy gShutterWohn = new GroupItemProxy("gShutterWohn");
	static SwitchItemProxy shutWZ_sued_kueche = new SwitchItemProxy(
			"shutWZ_sued_kueche");
	static SwitchItemProxy shutWZ_sued_links = new SwitchItemProxy(
			"shutWZ_sued_links");
	static SwitchItemProxy shutWZ_sued_rechts = new SwitchItemProxy(
			"shutWZ_sued_rechts");
	static SwitchItemProxy shutWZ_west_tuer = new SwitchItemProxy(
			"shutWZ_west_tuer");
	static SwitchItemProxy shutWZ_west_links = new SwitchItemProxy(
			"shutWZ_west_links");
	static SwitchItemProxy shutWZ_west_rechts = new SwitchItemProxy(
			"shutWZ_west_rechts");
	static SwitchItemProxy shutWZ_spare = new SwitchItemProxy("shutWZ_spare");
	static SwitchItemProxy shutALL = new SwitchItemProxy("shutALL");
	static RollershutterItemProxy gShutWZ_WestUI = new RollershutterItemProxy(
			"gShutWZ_WestUI");
	static RollershutterItemProxy shutGaesteUI = new RollershutterItemProxy(
			"shutGaesteUI");
	static RollershutterItemProxy shutSchlafUI = new RollershutterItemProxy(
			"shutSchlafUI");
	static RollershutterItemProxy shutBadUI = new RollershutterItemProxy(
			"shutBadUI");
	static RollershutterItemProxy shutWZ_sued_kuecheUI = new RollershutterItemProxy(
			"shutWZ_sued_kuecheUI");
	static RollershutterItemProxy shutWZ_sued_linksUI = new RollershutterItemProxy(
			"shutWZ_sued_linksUI");
	static RollershutterItemProxy shutWZ_sued_rechtsUI = new RollershutterItemProxy(
			"shutWZ_sued_rechtsUI");
	static RollershutterItemProxy shutWZ_west_tuerUI = new RollershutterItemProxy(
			"shutWZ_west_tuerUI");
	static RollershutterItemProxy shutWZ_west_linksUI = new RollershutterItemProxy(
			"shutWZ_west_linksUI");
	static RollershutterItemProxy shutWZ_west_rechtsUI = new RollershutterItemProxy(
			"shutWZ_west_rechtsUI");
	static RollershutterItemProxy shutWZ_suedUI = new RollershutterItemProxy(
			"shutWZ_suedUI");
	static RollershutterItemProxy shutWZ_westUI = new RollershutterItemProxy(
			"shutWZ_westUI");
	static GroupItemProxy gHue = new GroupItemProxy("gHue");
	static GroupItemProxy Switching = new GroupItemProxy("Switching");
	static GroupItemProxy Lichter = new GroupItemProxy("Lichter");
	static GroupItemProxy Colorize = new GroupItemProxy("Colorize");
	static GroupItemProxy CTDimmer = new GroupItemProxy("CTDimmer");
	static GroupItemProxy WhiteDimmer = new GroupItemProxy("WhiteDimmer");
	static GroupItemProxy rGaeste = new GroupItemProxy("rGaeste");
	static GroupItemProxy rBad = new GroupItemProxy("rBad");
	static GroupItemProxy rSchlaf = new GroupItemProxy("rSchlaf");
	static GroupItemProxy rWohn = new GroupItemProxy("rWohn");
	static NumberItemProxy sceneWohnzimmer = new NumberItemProxy(
			"sceneWohnzimmer");
	static NumberItemProxy sceneSchlafzimmer = new NumberItemProxy(
			"sceneSchlafzimmer");
	static GroupItemProxy gLichtWohnzimmer = new GroupItemProxy(
			"gLichtWohnzimmer");
	static GroupItemProxy gLichtSchlafzimmer = new GroupItemProxy(
			"gLichtSchlafzimmer");
	static ColorItemProxy Color_1 = new ColorItemProxy("Color_1");
	static ColorItemProxy Color_2 = new ColorItemProxy("Color_2");
	static ColorItemProxy Color_3 = new ColorItemProxy("Color_3");
	static ColorItemProxy Color_5 = new ColorItemProxy("Color_5");
	static SwitchItemProxy Tap_1_1 = new SwitchItemProxy("Tap_1_1");
	static SwitchItemProxy Tap_1_2 = new SwitchItemProxy("Tap_1_2");
	static SwitchItemProxy Tap_1_3 = new SwitchItemProxy("Tap_1_3");
	static SwitchItemProxy Tap_1_4 = new SwitchItemProxy("Tap_1_4");
	static SwitchItemProxy Set_Tap_1_2 = new SwitchItemProxy("Set_Tap_1_2");
	static SwitchItemProxy Set_Tap_1_3 = new SwitchItemProxy("Set_Tap_1_3");
	static SwitchItemProxy Set_Tap_1_4 = new SwitchItemProxy("Set_Tap_1_4");
	static GroupItemProxy gIntertechno = new GroupItemProxy("gIntertechno");
	static SwitchItemProxy swTestIT = new SwitchItemProxy("swTestIT");
	static SwitchItemProxy swMarkise = new SwitchItemProxy("swMarkise");
	static GroupItemProxy gTest = new GroupItemProxy("gTest");
	static GroupItemProxy gEvents = new GroupItemProxy("gEvents");
	static SwitchItemProxy testNotification = new SwitchItemProxy(
			"testNotification");
	static SwitchItemProxy testKlingel = new SwitchItemProxy("testKlingel");
	static SwitchItemProxy testTapRule = new SwitchItemProxy("testTapRule");
	static SwitchItemProxy setTapRules = new SwitchItemProxy("setTapRules");
	static SwitchItemProxy testJavaRule = new SwitchItemProxy("testJavaRule");
	static NumberItemProxy Event_Chart_Period = new NumberItemProxy(
			"Event_Chart_Period");
	static NumberItemProxy alarmDelay = new NumberItemProxy("alarmDelay");
	static StringItemProxy alarmRemaining = new StringItemProxy(
			"alarmRemaining");
	static StringItemProxy alarmEnd = new StringItemProxy("alarmEnd");
	static SwitchItemProxy motion1 = new SwitchItemProxy("motion1");
	static SwitchItemProxy motion2 = new SwitchItemProxy("motion2");
	static SwitchItemProxy presence = new SwitchItemProxy("presence");
	static SwitchItemProxy intrusion = new SwitchItemProxy("intrusion");
	static SwitchItemProxy intrusionArmed = new SwitchItemProxy(
			"intrusionArmed");
	// ========== END: generated code ==========
}
