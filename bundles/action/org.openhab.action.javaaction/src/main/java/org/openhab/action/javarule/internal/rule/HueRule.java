package org.openhab.action.javarule.internal.rule;

import java.util.HashMap;
import java.util.Map;

import org.openhab.action.hue.internal.Rule;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implement my hue actions
 * 
 * @author gernot
 * 
 */
public class HueRule extends ItemsRule {
	protected static final Logger logger = LoggerFactory
			.getLogger(HueRule.class);

	/***********************
	 * 
	 * Farben + Helligkeiten für szenen
	 * 
	 * 
	 * # hue sunset: ff8113 # relax: "bri":144,"hue":13088,"sat":212 #
	 * konzentration: "bri":219,"hue":33849,"sat":44 # feetUp:
	 * "bri":209,"hue":13198,"sat":209 kathy "bri":237,"hue":8407,"sat":252
	 * beach "bri":242,"hue":16323,"sat":48 ski "bri":254,"hue":34258,"sat":176
	 * sunset "bri":254,"hue":10658,"sat":252 lesen
	 * "bri":240,"hue":15331,"sat":121 aktivieren
	 * "bri":203,"hue":34495,"sat":232 # hue # Scene Lila: FF29F8 # Scene Abend:
	 * # Schlafzimmer: ECBE40 # Arbeiten/Hell # Früh: Farben nur über
	 * hue/Saturation/brightness
	 * 
	 * können direct aus hue ausgelesen werden:
	 * http://hue/api/1e71f86488b253ca2dfcb4d0d51d8414/lights/3/ hue: / 182.0
	 * sat: / 2.55 z.B. relax: "bri":144,"hue":13088,"sat":212 =>
	 * hue=71.91208791208791, bri=56.47058823529412, sat=83.13725490196078
	 * string ist <hue>,<sat>,<bri> tabelle für Umrechnung liegt im rules
	 * directory
	 * 
	 * 
	 * val String relax="71.91209,83.13725,56" val String
	 * konzentration="185.98352,17.2549,86" val String
	 * feetUp="72.51648,81.96078,82" val String kathy="46.19231,98.82353,93" val
	 * String beach="89.68681,18.82353,95" val String
	 * ski="188.23077,69.01961,100" val String sunset="58.56044,98.82353,100"
	 * val String lesen="84.23626,47.45098,94" val String
	 * aktivieren="189.53297,90.98039,80"
	 * 
	 * // und sonstige val String warmweiss="58.125,21.0,100" val String
	 * blau="254.8760330578512,96.8,99.0" val String
	 * lila="296.96202531645565,92.94117647058823,50" val String
	 * rot="351,93.70,60" //val String lila="58.125,21.0,100"
	 * 
	 * 
	 * val String testRuleName="Tap 2.4" val String tapDevice="2" val String
	 * testGroup="0" //??? // wohnzimmer aus: "scene": "35df7fe0d-off-0"
	 * //scenes: "scene": beach: "5e680bd82-on-0", off: "35df7fe0d-off-0"
	 * 
	 * val String sceneOff="35df7fe0d-off-0" val String
	 * sceneBeach="5e680bd82-on-0" val String sceneLesen="da3a6b19f-on-0" val
	 * String sceneFeetUp="f151ddfe9-on-0"
	 * 
	 * 
	 * val String rule1="Tap 2.1 4 Lampen" val String rule2="Tap 2.2 Feet up"
	 * val String rule3="Tap 2.3 Lesen" val String rule4="Tap 2.4"
	 **/

	// some commands, taken from hue
	private static final String off = "OFF";
	private static final String relax = "71.91209,83.13725,56";
	private static final String konzentration = "185.98352,17.2549,86";
	private static final String feetUp = "72.51648,81.96078,82";
	private static final String kathy = "46.19231,98.82353,93";
	private static final String beach = "89.68681,18.82353,95";
	private static final String ski = "188.23077,69.01961,100";
	private static final String sunset = "58.56044,98.82353,100";
	private static final String lesen = "84.23626,47.45098,94";
	private static final String aktivieren = "189.53297,90.98039,80";

	// and others
	private static final String warmweiss = "58.125,21.0,100";
	private static final String blau = "254.8760330578512,96.8,99.0";
	private static final String lila = "296.96202531645565,92.94117647058823,50";
	private static final String rot = "351,93.70,60";

	/**
	 * map scene ids to colors
	 */
	private static final Map<Integer, String> commands = new HashMap<Integer, String>();
	static {
		commands.put(0, off);
		commands.put(1, konzentration);
		commands.put(12, ski);
		commands.put(7, aktivieren);
		commands.put(13, warmweiss);
		commands.put(2, lesen);
		commands.put(3, beach);
		// relax
		commands.put(4, feetUp);
		commands.put(11, relax);
		commands.put(5, sunset);
		commands.put(6, kathy);

		commands.put(8, lila);
		commands.put(9, rot);
		commands.put(10, blau);
	}

	@Override
	public void receiveCommand(Item item, Command command) throws Exception {
		super.receiveCommand(item, command);

		if (item.equals(sceneSchlafzimmer)) {
			setGroupScene(sceneSchlafzimmer, command, gLichtSchlafzimmer);
		} else if (item.equals(sceneWohnzimmer)) {
			setGroupScene(sceneWohnzimmer, command, gLichtWohnzimmer);
		} else {
			logger.debug("other");
		}
	}

	/**
	 * set values for a group..
	 * 
	 * @param item
	 * @param command
	 * @param group
	 */
	private void setGroupScene(Item item, Command command, Item group) {
		logger.debug("setScene({},{},{})", item, command, group);

		Integer newState = toInteger(command);
		String cmd = commands.get(newState);

		sendCommand(group.getName(), cmd);
		logger.debug("state of item {} is now {}", item, item.getState());
	}

	@Override
	public void stateUpdated(Item item, State state) throws Exception {
		super.stateUpdated(item, state);

		/**
		 * handle tap rules
		 */
		if (item.equals(Tap_1_1)) {
			logger.debug("pressed {}", item);

			Rule r = new Rule();
		}
	}

}
