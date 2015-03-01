package org.openhab.action.javarule.user;

import java.util.Arrays;
import java.util.List;

import org.openhab.action.javarule.internal.Rule;
import org.openhab.action.javarule.osgi.AbstractRuleService;

/**
 * This will be my service..
 * 
 * @author gernot
 * 
 */
public class RuleService extends AbstractRuleService {

    @Override
    protected List<? extends Rule> findRules() {
	return Arrays.asList(new TestRule(), new HueRule(), new TapRule());
    }

}
