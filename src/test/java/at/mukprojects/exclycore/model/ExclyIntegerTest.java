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

package at.mukprojects.exclycore.model;

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

import at.mukprojects.exclycore.model.ExclyInteger;
import at.mukprojects.exclycore.model.ExclyIntegerBlank;
import at.mukprojects.exclycore.model.ExclyIntegerError;

/**
 * ExclyIntegerTest tests the model ExclyInteger.
 *
 * @author Mathias Markl
 */
public class ExclyIntegerTest extends AbstractModelTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyIntegerTest.class);

	private ExclyInteger integerOne;
	private ExclyInteger integerTwo;
	private ExclyInteger integerThree;

	private ExclyInteger integerError;
	private ExclyInteger integerBlank;

	@Before
	@Override
	public void setUp() throws FileNotFoundException, IOException {
		super.setUp();

		integerOne = new ExclyInteger(5.5);
		integerTwo = new ExclyInteger(10);
		integerThree = new ExclyInteger(15.5);

		integerError = new ExclyIntegerError();
		integerBlank = new ExclyIntegerBlank();
	}

	/**
	 * Tests the ExclyInteger basic functions.
	 */
	@Test
	public void testExclyIntegerBasic() throws Exception {
		assertTrue(!integerOne.isError());
		assertTrue(integerError.isError());
		assertTrue(!integerBlank.isError());

		assertTrue(integerOne.getData() == 5);
		assertTrue(integerError.getData() == 0);
		assertTrue(integerBlank.getData() == 0);
	}

	/**
	 * Tests the ExclyInteger setCell function.
	 */
	@Test
	public void testExclyIntegerSetCell() throws Exception {
		Row row = sheet.createRow(0);

		integerOne.setCell(row.createCell(0));
		log.debug(row.getCell(0).getNumericCellValue() + " / " + 5);
		assertTrue(row.getCell(0).getNumericCellValue() == 5);

		integerError.setCell(row.createCell(1));
		log.debug(row.getCell(1).getStringCellValue() + " / " + "###ERROR###");
		assertEquals(row.getCell(1).getStringCellValue(), "###ERROR###");

		integerBlank.setCell(row.createCell(2));
		log.debug(row.getCell(2).getStringCellValue() + " / " + "");
		assertEquals(row.getCell(2).getStringCellValue(), "");
	}

	/**
	 * Tests the ExclyInteger compareTo function.
	 */
	@Test
	public void testExclyIntegerCompareTo() throws Exception {
		log.debug("integerOne.compareTo(integerTwo) == -1 / " + integerOne.compareTo(integerTwo));
		assertTrue(integerOne.compareTo(integerTwo) == -1);

		log.debug("integerOne.compareTo(integerError) == -1 / " + integerOne.compareTo(integerError));
		assertTrue(integerOne.compareTo(integerError) == -1);

		log.debug("integerError.compareTo(integerError) == 0 / " + integerError.compareTo(integerError));
		assertTrue(integerError.compareTo(integerError) == 0);

		log.debug("integerError.compareTo(integerTwo) == 1 / " + integerError.compareTo(integerTwo));
		assertTrue(integerError.compareTo(integerTwo) == 1);

		log.debug("integerError.compareTo(integerBlank) == 1 / " + integerError.compareTo(integerBlank));
		assertTrue(integerError.compareTo(integerBlank) == 1);

		log.debug("integerOne.compareTo(integerBlank) == 1 / " + integerOne.compareTo(integerBlank));
		assertTrue(integerOne.compareTo(integerBlank) == 1);
	}

	/**
	 * Tests the ExclyInteger add function.
	 */
	@Test
	public void testExclyIntegerAdd() throws Exception {
		ExclyInteger addOneTwo = ExclyInteger.add(integerOne, integerTwo);
		log.debug(15 + " / " + addOneTwo);
		assertTrue(15 == addOneTwo.getData());

		ExclyInteger addOneTwoThree = ExclyInteger.add(integerOne, integerTwo, integerThree);
		log.debug(30 + " / " + addOneTwoThree);
		assertTrue(30 == addOneTwoThree.getData());

		ExclyInteger addOneError = ExclyInteger.add(integerOne, integerError);
		log.debug("Error: true / " + addOneError.isError());
		assertTrue(addOneError.isError());

		ExclyInteger addOneBlank = ExclyInteger.add(integerOne, integerBlank);
		log.debug(5 + " / " + addOneBlank);
		assertTrue(5 == addOneBlank.getData());
	}

	/**
	 * Tests the ExclyInteger sub function.
	 */
	@Test
	public void testExclyIntegerSub() throws Exception {
		ExclyInteger subOneTwo = ExclyInteger.sub(integerOne, integerTwo);
		log.debug(-5 + " / " + subOneTwo);
		assertTrue(-5 == subOneTwo.getData());

		ExclyInteger subOneError = ExclyInteger.sub(integerOne, integerError);
		log.debug("Error: true / " + subOneError.isError());
		assertTrue(subOneError.isError());

		ExclyInteger subOneBlank = ExclyInteger.sub(integerOne, integerBlank);
		log.debug(5 + " / " + subOneBlank);
		assertTrue(5 == subOneBlank.getData());
	}

	@After
	@Override
	public void tearDown() throws IOException {
		super.tearDown();
	}
}
