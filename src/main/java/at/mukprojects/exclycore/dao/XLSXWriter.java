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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger is an abstract helper class. It can be extended by any other class to
 * create or extends Excel (.xlsx) files.
 * 
 * @author Mathias Markl
 */
public abstract class XLSXWriter {
	private static final Logger log = LoggerFactory.getLogger(XLSXWriter.class);

	private FileOutputStream outputStream;
	private XSSFWorkbook outputWorkbook;
	private File output;
	private File tempOutput;

	/**
	 * This method lets you create the workbook. The workbook can be used to
	 * create or edit Excel content. After finishing the writing process its
	 * necessary to close the workbook with the the method called closeWorkbook.
	 * 
	 * @param output
	 *            The output file.
	 * @param add
	 *            Set this parameter to tell the writer if you want to append
	 *            the content to an existing file or if you want to create a
	 *            completely new file.
	 * @return Returns the workbook.
	 * @throws IOException
	 *             The Exception is thrown if an error occurs.
	 */
	protected XSSFWorkbook createWorkbook(File output, boolean add) throws IOException {
		log.info("Workbook gets created...");

		this.output = output;

		if (add) {
			log.info("The content will be appended to the existing file.");

			if (output.exists() && output.getName().endsWith("xlsx")) {
				try {
					outputWorkbook = (XSSFWorkbook) WorkbookFactory.create(output);
				} catch (InvalidFormatException e) {
					log.error("The Writer is unable to open" + " the existing Workbook.", e);
					throw new IOException("The Writer is unable to open" + " the existing Workbook.", e);
				}
				tempOutput = new File(output.getAbsolutePath() + ".temp");
				outputStream = new FileOutputStream(tempOutput);
			} else {
				outputStream = new FileOutputStream(output);
				outputWorkbook = new XSSFWorkbook();
			}
		} else {
			log.info("The content will be written in a new file.");

			outputStream = new FileOutputStream(output);
			outputWorkbook = new XSSFWorkbook();
		}

		return outputWorkbook;
	}

	/**
	 * Closes the workbook and cleans up all the used resources.
	 * 
	 * @throws IOException
	 *             The Exception is thrown if an error occurs.
	 */
	protected void closeWorkbook() throws IOException {
		if (outputWorkbook != null) {
			outputWorkbook.write(outputStream);
			if (tempOutput != null) {
				output.delete();
				tempOutput.renameTo(output);
				tempOutput.delete();
			}
			outputWorkbook.close();
		}
		if (outputStream != null) {
			outputStream.close();
		}
	}
}
