/** 
 * This code is copyright (c) Mathias Markl 2015
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package at.sht.exclycore.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExclyDateTest tests the model ExclyDate.
 *
 * @author Mathias Markl
 */
public class ExclyDateTest extends AbstractModelTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyDateTest.class);

	private ExclyDate dateOne;
	private ExclyDate dateTwo;
	private ExclyDate dateThree;

	private ExclyDate dateError;
	private ExclyDate dateBlank;

	private Calendar calendar;

	@Before
	@Override
	public void setUp() throws FileNotFoundException, IOException {
		super.setUp();

		calendar = Calendar.getInstance();

		dateOne = new ExclyDate(calendar.getTime());
		dateTwo = new ExclyDate(calendar.getTime());
		dateThree = new ExclyDate(calendar.getTime());

		dateError = new ExclyDateError();
		dateBlank = new ExclyDateBlank();
	}

	/**
	 * Tests the ExclyDate basic functions.
	 */
	@Test
	public void testExclyDateBasic() throws Exception {
		assertTrue(!dateOne.isError());
		assertTrue(dateError.isError());
		assertTrue(!dateBlank.isError());

		assertTrue(dateOne.getData().equals(calendar.getTime()));
		assertTrue(dateError.getData() == null);
		assertTrue(dateBlank.getData() == null);
	}

	/**
	 * Tests the ExclyDate setCell function.
	 */
	@Test
	public void testExclyDateSetCell() throws Exception {
		Row row = sheet.createRow(0);

		dateOne.setCell(row.createCell(0));
		log.debug(row.getCell(0).getDateCellValue() + " / " + calendar.getTime());
		assertEquals(row.getCell(0).getDateCellValue(), calendar.getTime());
		
		dateError.setCell(row.createCell(1));
		log.debug(row.getCell(1).getStringCellValue() + " / " + "###ERROR###");
		assertEquals(row.getCell(1).getStringCellValue(), "###ERROR###");
		
		dateBlank.setCell(row.createCell(2));
		log.debug(row.getCell(2).getStringCellValue() + " / " + "");
		assertEquals(row.getCell(2).getStringCellValue(), "");
	}
	
	/**
	 * Tests the ExclyDate compareTo function.
	 */
	@Test
	public void testExclyDateCompareTo() throws Exception {
		log.debug("dateOne.compareTo(dateTwo) == 0 / " + dateOne.compareTo(dateTwo));
		assertTrue(dateOne.compareTo(dateTwo) == 0);
		
		log.debug("dateOne.compareTo(dateError) == -1 / " + dateOne.compareTo(dateError));
		assertTrue(dateOne.compareTo(dateError) == -1);		

		log.debug("dateError.compareTo(dateError) == 0 / " + dateError.compareTo(dateError));
		assertTrue(dateError.compareTo(dateError) == 0);
		
		log.debug("dateError.compareTo(dateTwo) == 1 / " + dateError.compareTo(dateTwo));
		assertTrue(dateError.compareTo(dateTwo) == 1);
		
		log.debug("dateError.compareTo(dateBlank) == 1 / " + dateError.compareTo(dateBlank));
		assertTrue(dateError.compareTo(dateBlank) == 1);
		
		log.debug("dateOne.compareTo(dateBlank) == -1 / " + dateOne.compareTo(dateBlank));
		assertTrue(dateOne.compareTo(dateBlank) == -1);
	}
	
	/**
	 * Tests the ExclyDate add function.
	 */
	@Test
	public void testExclyDateAdd() throws Exception {
		ExclyDate addOneTwo = ExclyDate.add(dateOne, dateTwo);
		log.debug(new Date(calendar.getTime().getTime() * 2) + " / " + addOneTwo);
		assertEquals(new Date(calendar.getTime().getTime() * 2), addOneTwo.getData());
		
		ExclyDate addOneTwoThree = ExclyDate.add(dateOne, dateTwo, dateThree);
		log.debug(new Date(calendar.getTime().getTime() * 3) + " / " + addOneTwoThree);
		assertEquals(new Date(calendar.getTime().getTime() * 3), addOneTwoThree.getData());
		
		ExclyDate addOneError = ExclyDate.add(dateOne, dateError);
		log.debug("Error: true / " + addOneError.isError());
		assertTrue(addOneError.isError());
		
		ExclyDate addOneBlank = ExclyDate.add(dateOne, dateBlank);
		log.debug(new Date(calendar.getTime().getTime()) + " / " + addOneBlank);
		assertEquals(new Date(calendar.getTime().getTime()), addOneBlank.getData());
	}
	
	/**
	 * Tests the ExclyDate sub function.
	 */
	@Test
	public void testExclyDateSub() throws Exception {
		ExclyDate subOneTwo = ExclyDate.sub(dateOne, dateTwo);
		log.debug("Thu Jan 01 01:00:00 CET 1970 / " + subOneTwo);
		assertEquals("Thu Jan 01 01:00:00 CET 1970", subOneTwo.getData().toString());
		
		ExclyDate subOneError = ExclyDate.sub(dateOne, dateError);
		log.debug("Error: true / " + subOneError.isError());
		assertTrue(subOneError.isError());
		
		ExclyDate subOneBlank = ExclyDate.sub(dateOne, dateBlank);
		log.debug(new Date(calendar.getTime().getTime()) + " / " + subOneBlank);
		assertEquals(new Date(calendar.getTime().getTime()), subOneBlank.getData());
	}
	
	@After
	@Override
	public void tearDown() throws IOException {
		super.tearDown();
	}
}
