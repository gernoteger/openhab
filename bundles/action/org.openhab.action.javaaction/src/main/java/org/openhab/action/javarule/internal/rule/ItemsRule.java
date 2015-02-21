package org.openhab.action.javarule.internal.rule;

import org.openhab.action.javarule.internal.GenericRule;
import org.openhab.core.items.GroupItem;
import org.openhab.core.library.items.ColorItem;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.items.SwitchItem;

/**
 * Rule with items added from export
 * 
 * @author gernot
 * 
 */
public class ItemsRule extends GenericRule {

	GroupItem gFS20 = item("gFS20");
	GroupItem gFS20Switches = item("gFS20Switches");
	GroupItem gSwitch1 = item("gSwitch1");
	SwitchItem sw2_1 = item("sw2_1");
	SwitchItem sw2_2 = item("sw2_2");
	GroupItem gSwitchBig = item("gSwitchBig");
	SwitchItem sw1_1 = item("sw1_1");
	SwitchItem sw1_2 = item("sw1_2");
	SwitchItem sw1_3 = item("sw1_3");
	SwitchItem sw1_4 = item("sw1_4");
	SwitchItem sw1_5 = item("sw1_5");
	SwitchItem sw1_6 = item("sw1_6");
	SwitchItem sw1_7 = item("sw1_7");
	SwitchItem sw1_8 = item("sw1_8");
	GroupItem gKlingel = item("gKlingel");
	SwitchItem klingel1 = item("klingel1");
	GroupItem gShutter = item("gShutter");
	GroupItem gShutterUI = item("gShutterUI");
	GroupItem gShutWZ_West = item("gShutWZ_West");
	GroupItem gShutWZ_Sued = item("gShutWZ_Sued");
	SwitchItem shutGaeste = item("shutGaeste");
	SwitchItem shutBad = item("shutBad");
	SwitchItem shutSchlaf = item("shutSchlaf");
	GroupItem gShutterWohn = item("gShutterWohn");
	SwitchItem shutWZ_sued_kueche = item("shutWZ_sued_kueche");
	SwitchItem shutWZ_sued_links = item("shutWZ_sued_links");
	SwitchItem shutWZ_sued_rechts = item("shutWZ_sued_rechts");
	SwitchItem shutWZ_west_tuer = item("shutWZ_west_tuer");
	SwitchItem shutWZ_west_links = item("shutWZ_west_links");
	SwitchItem shutWZ_west_rechts = item("shutWZ_west_rechts");
	SwitchItem shutWZ_spare = item("shutWZ_spare");
	SwitchItem shutALL = item("shutALL");
	RollershutterItem gShutWZ_WestUI = item("gShutWZ_WestUI");
	RollershutterItem shutGaesteUI = item("shutGaesteUI");
	RollershutterItem shutSchlafUI = item("shutSchlafUI");
	RollershutterItem shutBadUI = item("shutBadUI");
	RollershutterItem shutWZ_sued_kuecheUI = item("shutWZ_sued_kuecheUI");
	RollershutterItem shutWZ_sued_linksUI = item("shutWZ_sued_linksUI");
	RollershutterItem shutWZ_sued_rechtsUI = item("shutWZ_sued_rechtsUI");
	RollershutterItem shutWZ_west_tuerUI = item("shutWZ_west_tuerUI");
	RollershutterItem shutWZ_west_linksUI = item("shutWZ_west_linksUI");
	RollershutterItem shutWZ_west_rechtsUI = item("shutWZ_west_rechtsUI");
	RollershutterItem shutWZ_suedUI = item("shutWZ_suedUI");
	RollershutterItem shutWZ_westUI = item("shutWZ_westUI");
	public GroupItem gHue = item("gHue");
	GroupItem Switching = item("Switching");
	GroupItem Lichter = item("Lichter");
	GroupItem Colorize = item("Colorize");
	GroupItem CTDimmer = item("CTDimmer");
	GroupItem WhiteDimmer = item("WhiteDimmer");
	GroupItem rGaeste = item("rGaeste");
	GroupItem rBad = item("rBad");
	GroupItem rSchlaf = item("rSchlaf");
	GroupItem rWohn = item("rWohn");
	GroupItem gTest = item("gTest");
	NumberItem sceneWohnzimmer = item("sceneWohnzimmer");
	NumberItem sceneSchlafzimmer = item("sceneSchlafzimmer");
	GroupItem gLichtWohnzimmer = item("gLichtWohnzimmer");
	GroupItem gLichtSchlafzimmer = item("gLichtSchlafzimmer");
	ColorItem Color_1 = item("Color_1");
	ColorItem Color_2 = item("Color_2");
	ColorItem Color_3 = item("Color_3");
	ColorItem Color_5 = item("Color_5");
	SwitchItem Tap_1_1 = item("Tap_1_1");
	SwitchItem Tap_1_2 = item("Tap_1_2");
	SwitchItem Tap_1_3 = item("Tap_1_3");
	SwitchItem Tap_1_4 = item("Tap_1_4");
	SwitchItem Set_Tap_1_2 = item("Set_Tap_1_2");
	SwitchItem Set_Tap_1_3 = item("Set_Tap_1_3");
	SwitchItem Set_Tap_1_4 = item("Set_Tap_1_4");
	GroupItem gIntertechno = item("gIntertechno");
	SwitchItem swTestIT = item("swTestIT");
	SwitchItem swMarkise = item("swMarkise");
	GroupItem gEvents = item("gEvents");
	SwitchItem testNotification = item("testNotification");
	SwitchItem testKlingel = item("testKlingel");
	SwitchItem testTapRule = item("testTapRule");
	SwitchItem setTapRules = item("setTapRules");
	SwitchItem testJavaRule = item("testJavaRule");
	NumberItem Event_Chart_Period = item("Event_Chart_Period");
	NumberItem alarmDelay = item("alarmDelay");
	StringItem alarmRemaining = item("alarmRemaining");
	StringItem alarmEnd = item("alarmEnd");
	SwitchItem motion1 = item("motion1");
	SwitchItem motion2 = item("motion2");
	SwitchItem presence = item("presence");
	SwitchItem intrusion = item("intrusion");
	SwitchItem intrusionArmed = item("intrusionArmed");
}
