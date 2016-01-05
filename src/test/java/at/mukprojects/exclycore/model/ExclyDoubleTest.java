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

import at.mukprojects.exclycore.model.ExclyDouble;
import at.mukprojects.exclycore.model.ExclyDoubleBlank;
import at.mukprojects.exclycore.model.ExclyDoubleError;

/**
 * ExclyDoubleTest tests the model ExclyDouble.
 *
 * @author Mathias Markl
 */
public class ExclyDoubleTest extends AbstractModelTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyDoubleTest.class);

	private ExclyDouble doubleOne;
	private ExclyDouble doubleTwo;
	private ExclyDouble doubleThree;

	private ExclyDouble doubleError;
	private ExclyDouble doubleBlank;

	@Before
	@Override
	public void setUp() throws FileNotFoundException, IOException {
		super.setUp();

		doubleOne = new ExclyDouble(5.5);
		doubleTwo = new ExclyDouble(10.5);
		doubleThree = new ExclyDouble(15.5);

		doubleError = new ExclyDoubleError();
		doubleBlank = new ExclyDoubleBlank();
	}

	/**
	 * Tests the ExclyDouble basic functions.
	 */
	@Test
	public void testExclyDoubleBasic() throws Exception {
		assertTrue(!doubleOne.isError());
		assertTrue(doubleError.isError());
		assertTrue(!doubleBlank.isError());

		assertTrue(doubleOne.getData() == 5.5);
		assertTrue(doubleError.getData() == 0);
		assertTrue(doubleBlank.getData() == 0);
	}

	/**
	 * Tests the ExclyDouble setCell function.
	 */
	@Test
	public void testExclyDoubleSetCell() throws Exception {
		Row row = sheet.createRow(0);

		doubleOne.setCell(row.createCell(0));
		log.debug(row.getCell(0).getNumericCellValue() + " / " + 5.5);
		assertTrue(row.getCell(0).getNumericCellValue() == 5.5);

		doubleError.setCell(row.createCell(1));
		log.debug(row.getCell(1).getStringCellValue() + " / " + "###ERROR###");
		assertEquals(row.getCell(1).getStringCellValue(), "###ERROR###");

		doubleBlank.setCell(row.createCell(2));
		log.debug(row.getCell(2).getStringCellValue() + " / " + "");
		assertEquals(row.getCell(2).getStringCellValue(), "");
	}

	/**
	 * Tests the ExclyDouble compareTo function.
	 */
	@Test
	public void testExclyDoubleCompareTo() throws Exception {
		log.debug("doubleOne.compareTo(doubleTwo) == -1 / " + doubleOne.compareTo(doubleTwo));
		assertTrue(doubleOne.compareTo(doubleTwo) == -1);

		log.debug("doubleOne.compareTo(doubleError) == -1 / " + doubleOne.compareTo(doubleError));
		assertTrue(doubleOne.compareTo(doubleError) == -1);

		log.debug("doubleError.compareTo(doubleError) == 0 / " + doubleError.compareTo(doubleError));
		assertTrue(doubleError.compareTo(doubleError) == 0);

		log.debug("doubleError.compareTo(doubleTwo) == 1 / " + doubleError.compareTo(doubleTwo));
		assertTrue(doubleError.compareTo(doubleTwo) == 1);

		log.debug("doubleError.compareTo(doubleBlank) == 1 / " + doubleError.compareTo(doubleBlank));
		assertTrue(doubleError.compareTo(doubleBlank) == 1);

		log.debug("doubleOne.compareTo(doubleBlank) == 1 / " + doubleOne.compareTo(doubleBlank));
		assertTrue(doubleOne.compareTo(doubleBlank) == 1);
	}

	/**
	 * Tests the ExclyDouble add function.
	 */
	@Test
	public void testExclyDoubleAdd() throws Exception {
		ExclyDouble addOneTwo = ExclyDouble.add(doubleOne, doubleTwo);
		log.debug(16 + " / " + addOneTwo);
		assertTrue(16 == addOneTwo.getData());

		ExclyDouble addOneTwoThree = ExclyDouble.add(doubleOne, doubleTwo, doubleThree);
		log.debug(31.5 + " / " + addOneTwoThree);
		assertTrue(31.5 == addOneTwoThree.getData());

		ExclyDouble addOneError = ExclyDouble.add(doubleOne, doubleError);
		log.debug("Error: true / " + addOneError.isError());
		assertTrue(addOneError.isError());

		ExclyDouble addOneBlank = ExclyDouble.add(doubleOne, doubleBlank);
		log.debug(5.5 + " / " + addOneBlank);
		assertTrue(5.5 == addOneBlank.getData());
	}

	/**
	 * Tests the ExclyDouble sub function.
	 */
	@Test
	public void testExclyDoubleSub() throws Exception {
		ExclyDouble subOneTwo = ExclyDouble.sub(doubleOne, doubleTwo);
		log.debug(-5 + " / " + subOneTwo);
		assertTrue(-5 == subOneTwo.getData());

		ExclyDouble subOneError = ExclyDouble.sub(doubleOne, doubleError);
		log.debug("Error: true / " + subOneError.isError());
		assertTrue(subOneError.isError());

		ExclyDouble subOneBlank = ExclyDouble.sub(doubleOne, doubleBlank);
		log.debug(5.5 + " / " + subOneBlank);
		assertTrue(5.5 == subOneBlank.getData());
	}
	
	/**
	 * Tests the ExclyDouble multi function.
	 */
	@Test
	public void testExclyDoubleMulti() throws Exception {
		ExclyDouble multiOneTwo = ExclyDouble.multi(doubleOne, doubleTwo);
		log.debug(57.75 + " / " + multiOneTwo);
		assertTrue(57.75 == multiOneTwo.getData());
	}
	
	/**
	 * Tests the ExclyDouble div function.
	 */
	@Test
	public void testExclyDoubleDiv() throws Exception {
		ExclyDouble divOneTwo = ExclyDouble.div(doubleOne, doubleTwo);
		log.debug(0.5238095238095238 + " / " + divOneTwo);
		assertTrue(0.5238095238095238 == divOneTwo.getData());
	}

	@After
	@Override
	public void tearDown() throws IOException {
		super.tearDown();
	}
}
