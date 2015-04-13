package org.openhab.io.transport.cul;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openhab.io.transport.cul.internal.CULNetworkHandlerImpl;
import org.openhab.io.transport.cul.internal.CULSerialHandlerImpl;

public class CulConstructorsTest {
	
// code below needed if testing with proper mocks for devices
//	@Before
//	public void initHandlers(){
//		CULManager.registerHandlerClass("serial", CULSerialHandlerImpl.class);
//		CULManager.registerHandlerClass("network", CULNetworkHandlerImpl.class);
//	}
	
	@Test
	public void testCreateSerialHandler() throws Exception{
		CULHandler handler=new CULSerialHandlerImpl("myDevice",CULMode.SLOW_RF);

		Assert.assertNotNull(handler);
		//will not work w/o mocking
		//handler=CULManager.getOpenCULHandler("serial:/dev/somedummyserialdevice", CULMode.SLOW_RF);
		
		Map<String,String> properties=new HashMap<String,String>();
		
		properties.put("baudrate", "9600");
		properties.put("parity","EVEN");  
		handler=new CULSerialHandlerImpl("myDevice",CULMode.SLOW_RF,properties);
		Assert.assertTrue(handler.arePropertiesEqual(properties));
		
			
	}
	
	@Test
	public void testCreateNetworkHandler() throws Exception{
		CULHandler handler=new CULNetworkHandlerImpl("myDevice",CULMode.SLOW_RF);

		Assert.assertNotNull(handler);
		//will not work w/o mocking
		//handler=CULManager.getOpenCULHandler("serial:/dev/somedummyserialdevice", CULMode.SLOW_RF);
		Map<String,String> properties=new HashMap<String,String>();

		//meaningless properties: will be ignored
		properties.put("baudrate", "9600");
		properties.put("parity","EVEN"); 
		handler=new CULNetworkHandlerImpl("myDevice",CULMode.SLOW_RF,properties);
		Assert.assertTrue(handler.arePropertiesEqual(properties));

	}
	
}
