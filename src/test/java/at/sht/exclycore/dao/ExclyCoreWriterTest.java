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

package at.sht.exclycore.dao;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.sht.exclycore.dao.XLSXWriter;

/**
 * ExclyCoreWriterTest implements a couple of JUnit tests to test the
 * functionality of the XLSXWriter class.
 *
 * @author Mathias Markl
 */
public class ExclyCoreWriterTest {
	private static final Logger log = LoggerFactory.getLogger(ExclyCoreWriterTest.class);

	private final static String OUTFILE = "src/test/resources/output.xlsx";

	private WriterTestImpl writer;
	private File outputFile;
	private File outputTestFile;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		log.debug("SetUp ExclyCoreTest.");

		writer = new WriterTestImpl();
		outputFile = new File(OUTFILE);
		outputTestFile = new File(OUTFILE);
	}

	/**
	 * Tests the XLSXWriter functions.
	 */
	@Test
	public void testXLSXWriter() throws Exception {
		XSSFWorkbook workbook = writer.createWorkbook(outputFile, false);
		workbook.createSheet("Sheet New");
		writer.closeWorkbook();

		assertTrue(outputTestFile.exists());

		workbook = writer.createWorkbook(outputFile, true);
		workbook.createSheet("Sheet Add");
		writer.closeWorkbook();

		assertTrue(outputTestFile.exists());

		FileInputStream inputStream = new FileInputStream(outputTestFile);
		workbook = new XSSFWorkbook(inputStream);

		assertTrue(workbook.getNumberOfSheets() == 2);

		workbook.close();
	}

	@After
	public void tearDown() throws IOException {
		log.debug("TearDown ExclyCoreTest.");

		if (outputTestFile.exists()) {
			outputTestFile.delete();
		}
	}

	/**
	 * Test implementation of the XLSXWriter.
	 */
	class WriterTestImpl extends XLSXWriter {

		@Override
		protected XSSFWorkbook createWorkbook(File output, boolean add) throws IOException {
			return super.createWorkbook(output, add);
		}

		@Override
		protected void closeWorkbook() throws IOException {
			super.closeWorkbook();
		}
	}
}
