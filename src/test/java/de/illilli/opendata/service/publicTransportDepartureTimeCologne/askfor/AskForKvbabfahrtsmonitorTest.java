package de.illilli.opendata.service.publicTransportDepartureTimeCologne.askfor;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.illilli.opendata.service.AskFor;
import de.illilli.opendata.service.publicTransportDepartureTimeCologne.model.Kvbabfahrt;

/**
 * <p>
 * Integrationstest
 * </p>
 * <p>
 * TODO: Integrationstestumgebung einrichten
 * </p>
 */
public class AskForKvbabfahrtsmonitorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@Ignore
	public void test() throws IOException {
		AskFor<List<Kvbabfahrt>> askfor = new AskForKvbabfahrtsmonitor(2);
		System.out.println(askfor.getData().toString());
	}

}
