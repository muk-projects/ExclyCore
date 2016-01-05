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

package at.mukprojects.exclycore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.mukprojects.exclycore.dao.XLSXReader;
import at.mukprojects.exclycore.model.ExclyDate;
import at.mukprojects.exclycore.model.ExclyDateError;
import at.mukprojects.exclycore.model.ExclyDouble;
import at.mukprojects.exclycore.model.ExclyDoubleBlank;
import at.mukprojects.exclycore.model.ExclyDoubleError;
import at.mukprojects.exclycore.model.ExclyInteger;
import at.mukprojects.exclycore.model.ExclyIntegerBlank;
import at.mukprojects.exclycore.model.ExclyIntegerError;
import at.mukprojects.exclycore.model.ExclyLong;
import at.mukprojects.exclycore.model.ExclyLongBlank;
import at.mukprojects.exclycore.model.ExclyLongError;
import at.mukprojects.exclycore.model.ExclyString;
import at.mukprojects.exclycore.model.ExclyStringError;

/**
 * ExclyCoreReaderTest implements a couple of JUnit tests to test the
 * functionality of the XLSXReader class.
 *
 * @author Mathias Markl
 */
public class ExclyCoreReaderTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyCoreReaderTest.class);

	private final static String RESFILE = "src/test/resources/input.xlsx";

	private FileInputStream inputStream;
	private XSSFWorkbook inputWorkbook;
	private Iterator<Row> inputRowIterator;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		log.debug("SetUp ExclyCoreTest.");

		File inputFile = new File(RESFILE);
		inputStream = new FileInputStream(inputFile);
		inputWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet inputSheet = inputWorkbook.getSheet("Test");
		inputRowIterator = inputSheet.iterator();
	}

	/**
	 * Tests the XLSXReader readStringCellValue function.
	 */
	@Test
	public void testXLSXReaderString() throws Exception {

		log.debug("Start test for testXLSXReaderString.");

		int counter = 0;
		ReaderTestImpl reader = new ReaderTestImpl();

		while (inputRowIterator.hasNext()) {
			Row inputRow = inputRowIterator.next();
			Iterator<Cell> inputCellInterator = inputRow.cellIterator();

			while (inputCellInterator.hasNext()) {
				Cell inputCell = inputCellInterator.next();

				log.debug("RowIndex: " + inputCell.getRowIndex() + " - RowExcel: " + (inputCell.getRowIndex() + 1));

				switch (counter) {
				case 0:
					log.debug("0 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("0", reader.readStringCellValue(inputCell).getData());
					break;
				case 1:
					log.debug("1 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("1", reader.readStringCellValue(inputCell).getData());
					break;
				case 2:
					log.debug("100 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("100", reader.readStringCellValue(inputCell).getData());
					break;
				case 3:
					log.debug("MuK / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("MuK", reader.readStringCellValue(inputCell).getData());
					break;
				case 4:
					log.debug("1.000,10 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("1.000,10", reader.readStringCellValue(inputCell).getData());
					break;
				case 5:
					log.debug("2.000 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("2.000", reader.readStringCellValue(inputCell).getData());
					break;
				case 6:
					log.debug("2 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("2", reader.readStringCellValue(inputCell).getData());
					break;
				case 7:
					log.debug("1.9 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("1.9", reader.readStringCellValue(inputCell).getData());
					break;
				case 8:
					log.debug("0,01 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("0,01", reader.readStringCellValue(inputCell).getData());
					break;
				case 9:
					log.debug("ExclyStringError: ture / " + reader.readStringCellValue(inputCell).isError());
					assertTrue("Should be an ExclyStringError.",
							reader.readStringCellValue(inputCell) instanceof ExclyStringError);
					break;
				case 10:
					log.debug("Tue Oct 20 00:00:00 CEST 2015 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("Tue Oct 20 00:00:00 CEST 2015", reader.readStringCellValue(inputCell).getData());
					break;
				case 11:
					log.debug("10/10/2015 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("10/10/2015", reader.readStringCellValue(inputCell).getData());
					break;
				case 12:
					log.debug("- / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("-", reader.readStringCellValue(inputCell).getData());
					break;
				case 13:
					log.debug("42297 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("42297", reader.readStringCellValue(inputCell).getData());
					break;
				case 14:
					log.debug("500 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("500", reader.readStringCellValue(inputCell).getData());
					break;
				case 15:
					log.debug("#Text / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("#Text", reader.readStringCellValue(inputCell).getData());
					break;
				case 16:
					log.debug("1,00.01 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("1,00.01", reader.readStringCellValue(inputCell).getData());
					break;
				case 17:
					log.debug("WAHR / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("WAHR", reader.readStringCellValue(inputCell).getData());
					break;
				case 18:
					log.debug("  / " + reader.readStringCellValue(inputCell).getData());
					assertEquals(" ", reader.readStringCellValue(inputCell).getData());
					break;
				case 19:
					log.debug("Sun Jan 03 00:00:00 CET 2016 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("Sun Jan 03 00:00:00 CET 2016", reader.readStringCellValue(inputCell).getData());
					break;
				case 20:
					log.debug("20 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("20", reader.readStringCellValue(inputCell).getData());
					break;
				case 21:
					log.debug("20 / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("20", reader.readStringCellValue(inputCell).getData());
					break;
				case 22:
					log.debug("ABC / " + reader.readStringCellValue(inputCell).getData());
					assertEquals("ABC", reader.readStringCellValue(inputCell).getData());
					break;
				}

				counter++;
			}
		}
	}

	/**
	 * Tests the XLSXReader readDoubleCellValue function.
	 */
	@Test
	public void testXLSXReaderDouble() throws Exception {

		log.debug("Start test for testXLSXReaderDouble.");

		int counter = 0;
		ReaderTestImpl reader = new ReaderTestImpl();

		while (inputRowIterator.hasNext()) {
			Row inputRow = inputRowIterator.next();
			Iterator<Cell> inputCellInterator = inputRow.cellIterator();

			while (inputCellInterator.hasNext()) {
				Cell inputCell = inputCellInterator.next();

				log.debug("RowIndex: " + inputCell.getRowIndex() + " - RowExcel: " + (inputCell.getRowIndex() + 1));

				switch (counter) {
				case 0:
					log.debug("0.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(0.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 1:
					log.debug("1.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(1.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 2:
					log.debug("100.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(100.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 3:
					log.debug("ExclyDoubleError: ture / " + reader.readDoubleCellValue(inputCell).isError());
					assertTrue("Should be an ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleError);
					break;
				case 4:
					log.debug("1000.10 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(1000.10, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 5:
					log.debug("2000.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(2000.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 6:
					log.debug("2.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(2.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 7:
					log.debug("1.9 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(1.9, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 8:
					log.debug("0.01 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(0.01, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 9:
					log.debug("ExclyDoubleError: ture / " + reader.readDoubleCellValue(inputCell).isError());
					assertTrue("Should be an ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleError);
					break;
				case 10:
					log.debug("42297.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(42297.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 11:
					log.debug("ExclyDoubleError: ture / " + reader.readDoubleCellValue(inputCell).isError());
					assertTrue("Should be an ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleError);
					break;
				case 12:
					log.debug("ExclyDoubleError: false / " + reader.readDoubleCellValue(inputCell).isError());
					assertTrue("Should be no ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleBlank);
					break;
				case 13:
					log.debug("42297.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(42297.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 14:
					log.debug("500.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(500.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 15:
					log.debug("ExclyDoubleError: ture / " + reader.readDoubleCellValue(inputCell).isError());
					assertTrue("Should be an ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleError);
					break;
				case 16:
					log.debug("100.01 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(100.01, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 17:
					log.debug("1.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(1.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 18:
					log.debug("ExclyDoubleError: false / " + reader.readDoubleCellValue(inputCell).isError());
					assertTrue("Should be no ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleBlank);
					break;
				case 19:
					log.debug("42372.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(42372.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 20:
					log.debug("20.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(20.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 21:
					log.debug("20.0 / " + reader.readDoubleCellValue(inputCell).getData());
					assertEquals(20.0, reader.readDoubleCellValue(inputCell).getData(), 0.0001);
					break;
				case 22:
					log.debug("ExclyDoubleError: ture / " + reader.readDoubleCellValue(inputCell).getData());
					assertTrue("Should be an ExclyDoubleError.",
							reader.readDoubleCellValue(inputCell) instanceof ExclyDoubleError);
					break;
				}

				counter++;
			}
		}
	}

	/**
	 * Tests the XLSXReader readIntegerCellValue function.
	 */
	@Test
	public void testXLSXReaderInteger() throws Exception {

		log.debug("Start test for testXLSXReaderInteger.");

		int counter = 0;
		ReaderTestImpl reader = new ReaderTestImpl();

		while (inputRowIterator.hasNext()) {
			Row inputRow = inputRowIterator.next();
			Iterator<Cell> inputCellInterator = inputRow.cellIterator();

			while (inputCellInterator.hasNext()) {
				Cell inputCell = inputCellInterator.next();

				log.debug("RowIndex: " + inputCell.getRowIndex() + " - RowExcel: " + (inputCell.getRowIndex() + 1));

				switch (counter) {
				case 0:
					log.debug("0 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(0, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 1:
					log.debug("1 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(1, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 2:
					log.debug("100 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(100, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 3:
					log.debug("ExclyIntegerError: ture / " + reader.readIntegerCellValue(inputCell).isError());
					assertTrue("Should be an ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerError);
					break;
				case 4:
					log.debug("1000 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(1000, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 5:
					log.debug("2000 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(2000, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 6:
					log.debug("2 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(2, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 7:
					log.debug("1 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(1, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 8:
					log.debug("0 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(0, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 9:
					log.debug("ExclyIntegerError: ture / " + reader.readIntegerCellValue(inputCell).isError());
					assertTrue("Should be an ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerError);
					break;
				case 10:
					log.debug("42297 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(42297, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 11:
					log.debug("ExclyIntegerError: ture / " + reader.readIntegerCellValue(inputCell).isError());
					assertTrue("Should be an ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerError);
					break;
				case 12:
					log.debug("ExclyIntegerError: false / " + reader.readIntegerCellValue(inputCell).isError());
					assertTrue("Should be no ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerBlank);
					break;
				case 13:
					log.debug("42297 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(42297, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 14:
					log.debug("500 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(500, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 15:
					log.debug("ExclyIntegerError: ture / " + reader.readIntegerCellValue(inputCell).isError());
					assertTrue("Should be an ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerError);
					break;
				case 16:
					log.debug("100 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(100, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 17:
					log.debug("1 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(1, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 18:
					log.debug("ExclyIntegerError: false / " + reader.readIntegerCellValue(inputCell).isError());
					assertTrue("Should be no ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerBlank);
					break;
				case 19:
					log.debug("42372 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(42372, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 20:
					log.debug("20 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(20, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 21:
					log.debug("20 / " + reader.readIntegerCellValue(inputCell).getData());
					assertEquals(20, reader.readIntegerCellValue(inputCell).getData());
					break;
				case 22:
					log.debug("ExclyIntegerError: ture / " + reader.readIntegerCellValue(inputCell).getData());
					assertTrue("Should be an ExclyIntegerError.",
							reader.readIntegerCellValue(inputCell) instanceof ExclyIntegerError);
					break;
				}

				counter++;
			}
		}
	}

	/**
	 * Tests the XLSXReader readLongCellValue function.
	 */
	@Test
	public void testXLSXReaderLong() throws Exception {

		log.debug("Start test for testXLSXReaderLong.");

		int counter = 0;
		ReaderTestImpl reader = new ReaderTestImpl();

		while (inputRowIterator.hasNext()) {
			Row inputRow = inputRowIterator.next();
			Iterator<Cell> inputCellInterator = inputRow.cellIterator();

			while (inputCellInterator.hasNext()) {
				Cell inputCell = inputCellInterator.next();

				log.debug("RowIndex: " + inputCell.getRowIndex() + " - RowExcel: " + (inputCell.getRowIndex() + 1));

				switch (counter) {
				case 0:
					log.debug("0 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(0L, reader.readLongCellValue(inputCell).getData());
					break;
				case 1:
					log.debug("1 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(1L, reader.readLongCellValue(inputCell).getData());
					break;
				case 2:
					log.debug("100 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(100L, reader.readLongCellValue(inputCell).getData());
					break;
				case 3:
					log.debug("ExclyLongError: ture / " + reader.readLongCellValue(inputCell).isError());
					assertTrue("Should be an ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongError);
					break;
				case 4:
					log.debug("1000 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(1000L, reader.readLongCellValue(inputCell).getData());
					break;
				case 5:
					log.debug("2000 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(2000L, reader.readLongCellValue(inputCell).getData());
					break;
				case 6:
					log.debug("2 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(2L, reader.readLongCellValue(inputCell).getData());
					break;
				case 7:
					log.debug("1 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(1L, reader.readLongCellValue(inputCell).getData());
					break;
				case 8:
					log.debug("0 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(0L, reader.readLongCellValue(inputCell).getData());
					break;
				case 9:
					log.debug("ExclyLongError: ture / " + reader.readLongCellValue(inputCell).isError());
					assertTrue("Should be an ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongError);
					break;
				case 10:
					log.debug("42297 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(42297L, reader.readLongCellValue(inputCell).getData());
					break;
				case 11:
					log.debug("ExclyLongError: ture / " + reader.readLongCellValue(inputCell).isError());
					assertTrue("Should be an ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongError);
					break;
				case 12:
					log.debug("ExclyLongError: false / " + reader.readLongCellValue(inputCell).isError());
					assertTrue("Should be no ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongBlank);
					break;
				case 13:
					log.debug("42297 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(42297L, reader.readLongCellValue(inputCell).getData());
					break;
				case 14:
					log.debug("500 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(500L, reader.readLongCellValue(inputCell).getData());
					break;
				case 15:
					log.debug("ExclyLongError: ture / " + reader.readLongCellValue(inputCell).isError());
					assertTrue("Should be an ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongError);
					break;
				case 16:
					log.debug("100 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(100L, reader.readLongCellValue(inputCell).getData());
					break;
				case 17:
					log.debug("1 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(1L, reader.readLongCellValue(inputCell).getData());
					break;
				case 18:
					log.debug("ExclyLongError: false / " + reader.readLongCellValue(inputCell).isError());
					assertTrue("Should be no ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongBlank);
					break;
				case 19:
					log.debug("42372 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(42372, reader.readLongCellValue(inputCell).getData());
					break;
				case 20:
					log.debug("20 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(20, reader.readLongCellValue(inputCell).getData());
					break;
				case 21:
					log.debug("20 / " + reader.readLongCellValue(inputCell).getData());
					assertEquals(20, reader.readLongCellValue(inputCell).getData());
					break;
				case 22:
					log.debug("ExclyLongError: ture / " + reader.readLongCellValue(inputCell).getData());
					assertTrue("Should be an ExclyLongError.",
							reader.readLongCellValue(inputCell) instanceof ExclyLongError);
					break;
				}

				counter++;
			}
		}
	}

	/**
	 * Tests the XLSXReader readDateCellValue function.
	 */
	@Test
	public void testXLSXReaderDate() throws Exception {

		log.debug("Start test for testXLSXReaderDate.");

		int counter = 0;
		ReaderTestImpl reader = new ReaderTestImpl();

		while (inputRowIterator.hasNext()) {
			Row inputRow = inputRowIterator.next();
			Iterator<Cell> inputCellInterator = inputRow.cellIterator();

			while (inputCellInterator.hasNext()) {
				Cell inputCell = inputCellInterator.next();

				log.debug("RowIndex: " + inputCell.getRowIndex() + " - RowExcel: " + (inputCell.getRowIndex() + 1));

				switch (counter) {
				case 3:
					log.debug("true / " + reader.readDateCellValue(inputCell).isError());
					assertTrue("Should be an ExclyDateError.",
							reader.readDateCellValue(inputCell) instanceof ExclyDateError);
					break;
				case 10:
					log.debug("Tue Oct 20 00:00:00 CEST 2015 / "
							+ reader.readDateCellValue(inputCell).getData().toString());
					assertTrue("Tue Oct 20 00:00:00 CEST 2015"
							.equals(reader.readDateCellValue(inputCell).getData().toString()));
					break;
				case 11:
					log.debug("Tue Oct 20 00:00:00 CEST 2015 / "
							+ reader.readDateCellValue(inputCell).getData().toString());
					assertTrue("Sat Oct 10 00:00:00 CEST 2015"
							.equals(reader.readDateCellValue(inputCell).getData().toString()));
					break;
				case 13:
					log.debug("Tue Oct 20 00:00:00 CEST 2015 / "
							+ reader.readDateCellValue(inputCell).getData().toString());
					assertTrue("Tue Oct 20 00:00:00 CEST 2015"
							.equals(reader.readDateCellValue(inputCell).getData().toString()));
					break;
				case 19:
					log.debug("Sun Jan 03 00:00:00 CET 2016 / " + reader.readDateCellValue(inputCell).getData().toString());
					assertEquals("Sun Jan 03 00:00:00 CET 2016",
							reader.readDateCellValue(inputCell).getData().toString());
					break;
				}

				counter++;
			}
		}
	}

	@After
	public void tearDown() throws IOException {
		log.debug("TearDown ExclyCoreTest.");

		inputWorkbook.close();
		inputStream.close();
	}

	/**
	 * Test implementation of the XLSXReader.
	 */
	class ReaderTestImpl extends XLSXReader {

		@Override
		public ExclyString readStringCellValue(Cell cell) {
			return super.readStringCellValue(cell);
		}

		@Override
		public ExclyDouble readDoubleCellValue(Cell cell) {
			return super.readDoubleCellValue(cell);
		}

		@Override
		public ExclyInteger readIntegerCellValue(Cell cell) {
			return super.readIntegerCellValue(cell);
		}

		@Override
		public ExclyLong readLongCellValue(Cell cell) {
			return super.readLongCellValue(cell);
		}

		@Override
		public ExclyDate readDateCellValue(Cell cell) {
			return super.readDateCellValue(cell);
		}
	}
}
