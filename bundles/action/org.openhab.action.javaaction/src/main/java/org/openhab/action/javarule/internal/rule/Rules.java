package org.openhab.action.javarule.internal.rule;

import java.util.Collection;
import java.util.LinkedList;

import org.openhab.action.javarule.internal.Rule;

/**
 * singleton to hold Rules
 * 
 * @author gernot
 * 
 */
public class Rules {
	private static LinkedList<Rule> rules = new LinkedList<Rule>();

	/**
	 * register all rules here TODO: can we use reflection??
	 */
	public static void registerRules() {
		rules = new LinkedList<Rule>();
		addRule(new TestRule());
		addRule(new HueRule());
		addRule(new TapRule());
	}

	/**
	 * add Rule to list
	 * 
	 * @param rule
	 */
	public static void addRule(Rule rule) {
		rules.add(rule);
	}

	/**
	 * get a collection of Rules
	 * 
	 * @return
	 */
	static public Collection<Rule> rules() {
		return rules;
	}
}
