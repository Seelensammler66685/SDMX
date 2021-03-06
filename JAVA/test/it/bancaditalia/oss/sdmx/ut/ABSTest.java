/* Copyright 2010,2014 Bank Of Italy
*
* Licensed under the EUPL, Version 1.1 or - as soon they
* will be approved by the European Commission - subsequent
* versions of the EUPL (the "Licence");
* You may not use this work except in compliance with the
* Licence.
* You may obtain a copy of the Licence at:
*
*
* http://ec.europa.eu/idabc/eupl
*
* Unless required by applicable law or agreed to in
* writing, software distributed under the Licence is
* distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied.
* See the Licence for the specific language governing
* permissions and limitations under the Licence.
*/

package it.bancaditalia.oss.sdmx.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.bancaditalia.oss.sdmx.api.DSDIdentifier;
import it.bancaditalia.oss.sdmx.api.Dimension;
import it.bancaditalia.oss.sdmx.api.PortableTimeSeries;
import it.bancaditalia.oss.sdmx.client.SdmxClientHandler;
import it.bancaditalia.oss.sdmx.exceptions.SdmxException;

public class ABSTest {
	@Test
	public void getDSDIdentifier() throws SdmxException {
		DSDIdentifier keyF = SdmxClientHandler.getDSDIdentifier("ABS", "QBIS");
		assertNotNull("Null key family for QBIS", keyF);
		assertEquals("Wrong Key Family", "QBIS", keyF.getId());
		assertEquals("Wrong agency", "ABS", keyF.getAgency());
	}

	@Test
	public void testGetFlows() throws SdmxException {
		Map<String, String> f = SdmxClientHandler.getFlows("ABS", null);
		assertNotNull("Null getFlows result", f);
		String descr = f.get("ATSI_BIRTHS_SUMM");
		assertEquals("Wrong description for ATSI_BIRTHS_SUMM", "Aboriginal and Torres Strait Islander births and confinements, summary, by state", descr);
	}

	@Test
	public void testGetDimensionsAndCodes() throws SdmxException {
		Map<String, String> codes = SdmxClientHandler.getCodes("ABS", "ATSI_BIRTHS_SUMM", "FREQUENCY");
		assertNotNull("Null getCodes result", codes);
		assertEquals("Wrong code for FREQUENCY annual", codes.get("A"), "Annual");
		List<Dimension> dim = SdmxClientHandler.getDimensions("ABS", "ATSI_BIRTHS_SUMM");
		assertNotNull("Null getDimensions result", dim);
		String result = "[Dimension [id=MEASURE, position=1, codelist=Codelist [id=ABS/CL_ATSI_BIRTHS_SUMM_MEASURE, codes={19=Births where only mother is Aboriginal or Torres Strait Islander, 20=Births where only father is Aboriginal or Torres Strait Islander, 18=Births where both parents are Aboriginal or Torres Strait Islander, 9=Ex-nuptial, ";
		assertEquals("Wrong dimensions for ATSI_BIRTHS_SUMM", result, dim.toString().substring(0, result.length()));
	}

	@Test
	public void testGetTimeSeriesFromID() throws SdmxException {
		List<PortableTimeSeries>  res = SdmxClientHandler.getTimeSeries("ABS", "ATSI_BIRTHS_SUMM/1...A", null, null);
		assertNotNull("Null time series result", res);
		assertEquals("Wrong result size", 16, res.size());
		res = SdmxClientHandler.getTimeSeries("ABS", "ATSI_BIRTHS_SUMM/1...A", "2000", "2010");
		assertNotNull("Null time series result", res);
		assertEquals("Wrong result size", 16, res.size());
		res = SdmxClientHandler.getTimeSeries("ABS", "ATSI_BIRTHS_SUMM/1+4...A", "2000", "2010");
		assertNotNull("Null time series result", res);
		assertEquals("Wrong result size", 32, res.size());
	}

}
