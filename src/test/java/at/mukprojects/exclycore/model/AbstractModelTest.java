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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract model test class.
 *
 * @author Mathias Markl
 */
public abstract class AbstractModelTest {
	private static final Logger log = LoggerFactory.getLogger(AbstractModelTest.class);

	protected XSSFWorkbook workbook;
	protected XSSFSheet sheet;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		log.debug("SetUp ExclyCoreTest.");

		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Model");
	}

	@After
	public void tearDown() throws IOException {
		log.debug("TearDown ExclyCoreTest.");

		workbook.close();
	}
}
