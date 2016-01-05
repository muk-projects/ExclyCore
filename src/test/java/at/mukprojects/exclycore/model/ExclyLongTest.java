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

import at.mukprojects.exclycore.model.ExclyLong;
import at.mukprojects.exclycore.model.ExclyLongBlank;
import at.mukprojects.exclycore.model.ExclyLongError;

/**
 * ExclyLongTest tests the model ExclyLong.
 *
 * @author Mathias Markl
 */
public class ExclyLongTest extends AbstractModelTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyLongTest.class);

	private ExclyLong longOne;
	private ExclyLong longTwo;
	private ExclyLong longThree;

	private ExclyLong longError;
	private ExclyLong longBlank;

	@Before
	@Override
	public void setUp() throws FileNotFoundException, IOException {
		super.setUp();

		longOne = new ExclyLong(5.5);
		longTwo = new ExclyLong(10);
		longThree = new ExclyLong(15.5);

		longError = new ExclyLongError();
		longBlank = new ExclyLongBlank();
	}

	/**
	 * Tests the ExclyLong basic functions.
	 */
	@Test
	public void testExclyLongBasic() throws Exception {
		assertTrue(!longOne.isError());
		assertTrue(longError.isError());
		assertTrue(!longBlank.isError());

		assertTrue(longOne.getData() == 5);
		assertTrue(longError.getData() == 0);
		assertTrue(longBlank.getData() == 0);
	}

	/**
	 * Tests the ExclyLong setCell function.
	 */
	@Test
	public void testExclyLongSetCell() throws Exception {
		Row row = sheet.createRow(0);

		longOne.setCell(row.createCell(0));
		log.debug(row.getCell(0).getNumericCellValue() + " / " + 5);
		assertTrue(row.getCell(0).getNumericCellValue() == 5);

		longError.setCell(row.createCell(1));
		log.debug(row.getCell(1).getStringCellValue() + " / " + "###ERROR###");
		assertEquals(row.getCell(1).getStringCellValue(), "###ERROR###");

		longBlank.setCell(row.createCell(2));
		log.debug(row.getCell(2).getStringCellValue() + " / " + "");
		assertEquals(row.getCell(2).getStringCellValue(), "");
	}

	/**
	 * Tests the ExclyLong compareTo function.
	 */
	@Test
	public void testExclyLongCompareTo() throws Exception {
		log.debug("longOne.compareTo(longTwo) == -1 / " + longOne.compareTo(longTwo));
		assertTrue(longOne.compareTo(longTwo) == -1);

		log.debug("longOne.compareTo(longError) == -1 / " + longOne.compareTo(longError));
		assertTrue(longOne.compareTo(longError) == -1);

		log.debug("longError.compareTo(longError) == 0 / " + longError.compareTo(longError));
		assertTrue(longError.compareTo(longError) == 0);

		log.debug("longError.compareTo(longTwo) == 1 / " + longError.compareTo(longTwo));
		assertTrue(longError.compareTo(longTwo) == 1);

		log.debug("longError.compareTo(longBlank) == 1 / " + longError.compareTo(longBlank));
		assertTrue(longError.compareTo(longBlank) == 1);

		log.debug("longOne.compareTo(longBlank) == 1 / " + longOne.compareTo(longBlank));
		assertTrue(longOne.compareTo(longBlank) == 1);
	}

	/**
	 * Tests the ExclyLong add function.
	 */
	@Test
	public void testExclyLongAdd() throws Exception {
		ExclyLong addOneTwo = ExclyLong.add(longOne, longTwo);
		log.debug(15 + " / " + addOneTwo);
		assertTrue(15 == addOneTwo.getData());

		ExclyLong addOneTwoThree = ExclyLong.add(longOne, longTwo, longThree);
		log.debug(30 + " / " + addOneTwoThree);
		assertTrue(30 == addOneTwoThree.getData());

		ExclyLong addOneError = ExclyLong.add(longOne, longError);
		log.debug("Error: true / " + addOneError.isError());
		assertTrue(addOneError.isError());

		ExclyLong addOneBlank = ExclyLong.add(longOne, longBlank);
		log.debug(5 + " / " + addOneBlank);
		assertTrue(5 == addOneBlank.getData());
	}

	/**
	 * Tests the ExclyLong sub function.
	 */
	@Test
	public void testExclyLongSub() throws Exception {
		ExclyLong subOneTwo = ExclyLong.sub(longOne, longTwo);
		log.debug(-5 + " / " + subOneTwo);
		assertTrue(-5 == subOneTwo.getData());

		ExclyLong subOneError = ExclyLong.sub(longOne, longError);
		log.debug("Error: true / " + subOneError.isError());
		assertTrue(subOneError.isError());

		ExclyLong subOneBlank = ExclyLong.sub(longOne, longBlank);
		log.debug(5 + " / " + subOneBlank);
		assertTrue(5 == subOneBlank.getData());
	}

	@After
	@Override
	public void tearDown() throws IOException {
		super.tearDown();
	}
}
