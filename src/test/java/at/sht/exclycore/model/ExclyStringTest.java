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

import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExclyStringTest tests the model ExclyString.
 *
 * @author Mathias Markl
 */
public class ExclyStringTest extends AbstractModelTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyStringTest.class);

	private ExclyString stringOne;
	private ExclyString stringTwo;
	private ExclyString stringThree;

	private ExclyString stringError;

	@Before
	@Override
	public void setUp() throws FileNotFoundException, IOException {
		super.setUp();

		stringOne = new ExclyString("Hello ");
		stringTwo = new ExclyString("World");
		stringThree = new ExclyString("!");
		
		stringError = new ExclyStringError();
	}

	/**
	 * Tests the ExclyString basic functions.
	 */
	@Test
	public void testExclyStringBasic() throws Exception {
		assertTrue(!stringOne.isError());
		assertTrue(stringError.isError());

		assertTrue(stringOne.getData().equals("Hello "));
		assertTrue(stringError.getData().equals(""));
	}

	/**
	 * Tests the ExclyString setCell function.
	 */
	@Test
	public void testExclyStringSetCell() throws Exception {
		Row row = sheet.createRow(0);

		stringOne.setCell(row.createCell(0));
		log.debug(row.getCell(0).getStringCellValue() + " / Hello ");
		assertEquals(row.getCell(0).getStringCellValue(), "Hello ");
		
		stringError.setCell(row.createCell(1));
		log.debug(row.getCell(1).getStringCellValue() + " / " + "###ERROR###");
		assertEquals(row.getCell(1).getStringCellValue(), "###ERROR###");
	}
	
	/**
	 * Tests the ExclyString compareTo function.
	 */
	@Test
	public void testExclyStringCompareTo() throws Exception {
		log.debug("stringOne.compareTo(stringTwo) == -15 / " + stringOne.compareTo(stringTwo));
		assertTrue(stringOne.compareTo(stringTwo) == -15);
		
		log.debug("stringOne.compareTo(stringError) == -1 / " + stringOne.compareTo(stringError));
		assertTrue(stringOne.compareTo(stringError) == -1);
	}
	
	/**
	 * Tests the ExclyString startWith function.
	 */
	@Test
	public void testExclyStringStartsWith() throws Exception {
		log.debug("stringOne.startsWith(stringTwo): false / " + stringOne.startsWith(stringTwo));
		assertTrue(!stringOne.startsWith(stringTwo));
	}
	
	/**
	 * Tests the ExclyString append function.
	 */
	@Test
	public void testExclyStringAppend() throws Exception {
		ExclyString result = ExclyString.append(stringOne, stringTwo, stringThree);
		log.debug("Hello World! / " + result);
		assertEquals(result.getData(), "Hello World!");
	}
	
	@After
	@Override
	public void tearDown() throws IOException {
		super.tearDown();
	}
}
