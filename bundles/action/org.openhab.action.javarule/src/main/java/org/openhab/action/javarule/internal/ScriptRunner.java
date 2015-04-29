package org.openhab.action.javarule.internal;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import my.TryMeTrait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.scalascriptengine.ScalaScriptEngine;

/**
 * fire up script runner engines
 * 
 * @author gernot
 * 
 */
public class ScriptRunner {
    protected static final Logger logger = LoggerFactory
	    .getLogger(ScriptRunner.class);

    public ScriptRunner() {
	// TODO Auto-generated constructor stub
    }

    public String getTestMessage() {
	return "Hello, Scripts!";
    }

    /**
     * fire up script engines TODO: experimental!!
     */
    public static void startScriptEngines() {
	// startScalaScriptEnginne();
	startGroovyScriptEnginne();
    }

    /**
     * start scripts for groovy
     */
    public static void startGroovyScriptEnginne() {
	File sourceDir = new File("scripts"); // TODO: configure
	try {
	    URL dir = sourceDir.toURI().toURL();

	    URL[] dirs = { dir };

	    // [tmpDir.toURI().toURL()] as
	    GroovyScriptEngine engine = new GroovyScriptEngine(dirs);
	    // def scriptCount= tmpDir.list().findAll { it =~ /.*\.groovy/
	    // }.size()
	    // println("found ${scriptCount} files");
	    // engine config verbose=true
	    engine.getConfig().setVerbose(true);

	    // while (true) {
	    Binding binding = new Binding();

	    // binding.setVariable("items",[1:"one",2:"two",3:"three",13:"dreizehn"]);
	    Map<Integer, String> values = new HashMap<Integer, String>();
	    values.put(1, "one");
	    values.put(2, "two");
	    values.put(3, "three");
	    values.put(13, "dreizehn");
	    binding.setVariable("items", values);

	    binding.setVariable("onUpdate", "onupdate"); // replace with updater
	    binding.setVariable("onCommand", "oncommand"); // replace
							   // withupdater

	    // def greeter = engine.run('ReloadingTest.groovy', binding)
	    try {
		Script greeter = (Script) engine.run("ReloadingTest.groovy",
			binding);
		logger.info("message: {}", greeter.sayHello());
	    } catch (ResourceException e) {
		logger.warn("failed to execute groovy script", e);
	    } catch (ScriptException e) {
		logger.warn("failed to execute groovy script", e);
	    }

	    // Thread.sleep(1000)
	    // }
	} catch (MalformedURLException e) {
	    logger.warn("failed to parse url", e);
	}

    }

    /**
     * start scripts for scala
     */
    public static void startScalaScriptEnginne() {
	try {
	    File sourceDir = new File("scripts"); // TODO: configure
	    String[] files = sourceDir.list();
	    ScalaScriptEngine sse = (ScalaScriptEngine) ScalaScriptEngine
		    .onChangeRefresh(sourceDir);

	    Object result = sse.newInstance("my.TryMe");

	    // must set -usejavacp ? ist his working in osgi???? I doubt it..
	    TryMeTrait t = (TryMeTrait) result;

	    System.out.println(t.result());
	} catch (Exception e) {
	    logger.warn("failed to execute scala script", e);
	}
	// TODO: can not (easily) be started from java; need scala dependency on
	// server side
	// sse.
	// while (true) {

	// String t = sse.newInstance<TryMeTrait>("my.TryMe");

	// println("code version %d, result : %s".format(sse.versionNumber,
	// t.result))

    }
}
