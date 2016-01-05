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

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.mukprojects.exclycore.model.ExclyDate;
import at.mukprojects.exclycore.model.ExclyDateBlank;
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
 * XLSXReader is an abstract helper class. It can be extended by any other class
 * to extract data from an Excel (.xlsx) file.
 *
 * @author Mathias Markl
 */
public abstract class XLSXReader {
	private static final Logger log = LoggerFactory.getLogger(XLSXReader.class);

	/**
	 * Date formats.
	 */
	private static final String[] formats = { "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
			"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy'T'HH:mm:ss.SSSZ",
			"MM/dd/yyyy'T'HH:mm:ss.SSS", "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss", "yyyy:MM:dd HH:mm:ss",
			"yyyyMMdd", "dd.MM.yyyy", "MM/dd/yyyy" };

	/**
	 * Checks if the string has a numeric value.
	 * 
	 * @param str
	 *            The string which should be tested.
	 * @return Returns true if the string has a numeric value, otherwise it will
	 *         return false.
	 */
	protected boolean isNumeric(String str) {
		return str.matches("\\d+(\\.\\d*)?(\\,\\d*)?|\\,\\d+") || str.matches("\\d+(\\,\\d*)?(\\.\\d*)?|\\.\\d+");
	}

	/**
	 * Checks if the string has a numeric value in German format.
	 * 
	 * @param str
	 *            The string which should be tested.
	 * @return Returns true if the string has a numeric value, otherwise it will
	 *         return false.
	 */
	protected boolean isNumericGerman(String str) {
		return str.matches("\\d+(\\.\\d*)?(\\,\\d*)?|\\,\\d+");
	}

	/**
	 * Checks if the string has a numeric value in UK format.
	 * 
	 * @param str
	 *            The string which should be tested.
	 * @return Returns true if the string has a numeric value, otherwise it will
	 *         return false.
	 */
	protected boolean isNumericUK(String str) {
		return str.matches("\\d+(\\,\\d*)?(\\.\\d*)?|\\.\\d+");
	}

	/**
	 * Checks if the string has a numeric value at the beginning.
	 * 
	 * @param str
	 *            The string which should be tested.
	 * @return Returns true if the string has a numeric value, otherwise it will
	 *         return false.
	 */
	protected boolean startsWithNumeric(String str) {
		return str.matches("^[0-9]+[0-9a-zA-ZäüöÄÜÖ ]*");
	}

	/**
	 * Tries to read the value of the given cell. If it's possible to parse the
	 * value into a string it will return an ExclyString with the parsed value.
	 * Otherwise an ExcelStringError is returned. An ExcelStringError has the
	 * value of an empty string ("").
	 * 
	 * @param cell
	 *            The Excel cell.
	 * @return Return the parsed value of the cell as an ExclyString.
	 */
	public ExclyString readStringCellValue(Cell cell) {
		ExclyString output = null;

		if (cell == null) {
			return new ExclyStringError();
		}

		try {
			output = readString(cell, cell.getCellType());
		} catch (Exception e) {
			log.error("The reader was unable to read the data from cell [Row, Column] (" + cell.getRowIndex() + ", "
					+ cell.getColumnIndex() + ")", e);
			output = new ExclyStringError();
		}

		return output;
	}

	private ExclyString readString(Cell cell, int type) throws Exception {
		ExclyString output = null;

		if (type == Cell.CELL_TYPE_STRING) {
			output = new ExclyString(cell.getStringCellValue());
		} else if (type == Cell.CELL_TYPE_ERROR) {
			output = new ExclyStringError();
		} else if (type == Cell.CELL_TYPE_FORMULA) {
			int formulaType = cell.getCachedFormulaResultType();
			output = readString(cell, formulaType);
		} else if (type == Cell.CELL_TYPE_BLANK) {
			output = new ExclyString("");
		} else if (type == Cell.CELL_TYPE_BOOLEAN) {
			Boolean data = cell.getBooleanCellValue();
			if (data) {
				output = new ExclyString("WAHR");
			} else {
				output = new ExclyString("FALSCH");
			}
		} else if (DateUtil.isCellDateFormatted(cell)) {
			Date data = cell.getDateCellValue();
			output = new ExclyString(data.toString());
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			double cellValue = cell.getNumericCellValue();
			String data = String.valueOf(cellValue);
			if (cellValue % 1 == 0 && data.endsWith(".0")) {
				data = data.substring(0, data.length() - 2);
			}
			output = new ExclyString(data);
		} else {
			log.warn("The reader was unable to find a valid parser for the cell [Row, Column] (" + cell.getRowIndex()
					+ ", " + cell.getColumnIndex() + ")");
			output = new ExclyStringError();
		}

		return output;
	}

	/**
	 * Tries to read the value of the given cell. If it's possible to parse the
	 * value into a double it will return an ExclyDouble with the parsed value.
	 * Otherwise a ExcelDoubleError or a ExclyDoubleBlank is returned, depending
	 * on the cell value wasn't parsable or blank. Both have a value of zero.
	 * 
	 * @param cell
	 *            The Excel cell.
	 * @return Return the parsed value of the cell as an ExclyDouble.
	 */
	public ExclyDouble readDoubleCellValue(Cell cell) {
		ExclyDouble output = null;

		if (cell == null) {
			return new ExclyDoubleError();
		}

		try {
			output = readDouble(cell, cell.getCellType());
		} catch (Exception e) {
			log.error("The reader was unable to read the data from cell [Row, Column] (" + cell.getRowIndex() + ", "
					+ cell.getColumnIndex() + ")", e);
			output = new ExclyDoubleError();
		}

		return output;
	}

	private ExclyDouble readDouble(Cell cell, int type) throws Exception {
		ExclyDouble output = null;

		if (type == Cell.CELL_TYPE_STRING) {
			String data = cell.getStringCellValue();
			if (isNumericGerman(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.GERMAN).parse(data);
				output = new ExclyDouble(number.doubleValue());
			} else if (isNumericUK(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.UK).parse(data);
				output = new ExclyDouble(number.doubleValue());
			} else if (data.equals("") || data.equals(" ") || data.equals("-")) {
				output = new ExclyDoubleBlank();
			} else {
				output = new ExclyDoubleError();
				log.warn("The reader has expected a numeric value, but found a string value. [Row, Column] ("
						+ cell.getRowIndex() + ", " + cell.getColumnIndex() + ")");
			}
		} else if (type == Cell.CELL_TYPE_BLANK) {
			output = new ExclyDoubleBlank();
		} else if (type == Cell.CELL_TYPE_FORMULA) {
			int formulaType = cell.getCachedFormulaResultType();
			output = readDouble(cell, formulaType);
		} else if (type == Cell.CELL_TYPE_BOOLEAN) {
			Boolean data = cell.getBooleanCellValue();
			if (data) {
				output = new ExclyDouble(1);
			} else {
				output = new ExclyDouble(0);
			}
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			double data = cell.getNumericCellValue();
			output = new ExclyDouble(data);
		} else if (type == Cell.CELL_TYPE_ERROR) {
			output = new ExclyDoubleError();
		} else {
			log.warn("The reader was unable to find a valid parser for the cell [Row, Column] (" + cell.getRowIndex()
					+ ", " + cell.getColumnIndex() + ")");
			output = new ExclyDoubleError();
		}

		return output;
	}

	/**
	 * Tries to read the value of the given cell. If it's possible to parse the
	 * value into an integer it will return an ExclyInteger with the parsed
	 * value. Otherwise a ExclyIntegerError or a ExclyIntegerBlank is returned,
	 * depending on the cell value wasn't parsable or blank. Both have a value
	 * of zero.
	 * 
	 * @param cell
	 *            The Excel cell.
	 * @return Return the parsed value of the cell as an ExclyInteger.
	 */
	public ExclyInteger readIntegerCellValue(Cell cell) {
		ExclyInteger output = null;

		if (cell == null) {
			return new ExclyIntegerError();
		}

		try {
			output = readInteger(cell, cell.getCellType());
		} catch (Exception e) {
			log.error("The reader was unable to read the data from cell [Row, Column] (" + cell.getRowIndex() + ", "
					+ cell.getColumnIndex() + ")", e);
			output = new ExclyIntegerError();
		}

		return output;
	}

	private ExclyInteger readInteger(Cell cell, int type) throws Exception {
		ExclyInteger output = null;

		if (type == Cell.CELL_TYPE_STRING) {
			String data = cell.getStringCellValue();
			if (isNumericGerman(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.GERMAN).parse(data);
				output = new ExclyInteger(number.intValue());
			} else if (isNumericUK(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.UK).parse(data);
				output = new ExclyInteger(number.intValue());
			} else if (data.equals("") || data.equals(" ") || data.trim().equals("-")) {
				output = new ExclyIntegerBlank();
			} else {
				output = new ExclyIntegerError();
				log.warn("The reader has expected a numeric value, but found a string value. [Row, Column] ("
						+ cell.getRowIndex() + ", " + cell.getColumnIndex() + ")");
			}
		} else if (type == Cell.CELL_TYPE_BLANK) {
			output = new ExclyIntegerBlank();
		} else if (type == Cell.CELL_TYPE_FORMULA) {
			int formulaType = cell.getCachedFormulaResultType();
			output = readInteger(cell, formulaType);
		} else if (type == Cell.CELL_TYPE_BOOLEAN) {
			Boolean data = cell.getBooleanCellValue();
			if (data) {
				output = new ExclyInteger(1);
			} else {
				output = new ExclyInteger(0);
			}
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			double data = cell.getNumericCellValue();
			output = new ExclyInteger(data);
		} else if (type == Cell.CELL_TYPE_ERROR) {
			output = new ExclyIntegerError();
		} else {
			log.warn("The reader was unable to find a valid parser for the cell [Row, Column] (" + cell.getRowIndex()
					+ ", " + cell.getColumnIndex() + ")");
			output = new ExclyIntegerError();
		}

		return output;
	}

	/**
	 * Tries to read the value of the given cell. If it's possible to parse the
	 * value into a long it will return an ExclyLong with the parsed value.
	 * Otherwise a ExclyLongError or a ExclyLongBlank is returned, depending on
	 * the cell value wasn't parsable or blank. Both have a value of zero.
	 * 
	 * @param cell
	 *            The Excel cell.
	 * @return Return the parsed value of the cell as an ExclyLong.
	 */
	public ExclyLong readLongCellValue(Cell cell) {
		ExclyLong output = null;

		if (cell == null) {
			return new ExclyLongError();
		}

		try {
			output = readLong(cell, cell.getCellType());
		} catch (Exception e) {
			log.error("The reader was unable to read the data from cell [Row, Column] (" + cell.getRowIndex() + ", "
					+ cell.getColumnIndex() + ")", e);
			output = new ExclyLongError();
		}

		return output;
	}

	private ExclyLong readLong(Cell cell, int type) throws Exception {
		ExclyLong output = null;

		if (type == Cell.CELL_TYPE_STRING) {
			String data = cell.getStringCellValue();
			if (isNumericGerman(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.GERMAN).parse(data);
				output = new ExclyLong(number.intValue());
			} else if (isNumericUK(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.UK).parse(data);
				output = new ExclyLong(number.intValue());
			} else if (data.equals("") || data.equals(" ") || data.equals("-")) {
				output = new ExclyLongBlank();
			} else {
				output = new ExclyLongError();
				log.warn("The reader has expected a numeric value, but found a string value. [Row, Column] ("
						+ cell.getRowIndex() + ", " + cell.getColumnIndex() + ")");
			}
		} else if (type == Cell.CELL_TYPE_BLANK) {
			output = new ExclyLongBlank();
		} else if (type == Cell.CELL_TYPE_FORMULA) {
			int formulaType = cell.getCachedFormulaResultType();
			output = readLong(cell, formulaType);
		} else if (type == Cell.CELL_TYPE_BOOLEAN) {
			Boolean data = cell.getBooleanCellValue();
			if (data) {
				output = new ExclyLong(1);
			} else {
				output = new ExclyLong(0);
			}
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			double data = cell.getNumericCellValue();
			output = new ExclyLong(data);
		} else if (type == Cell.CELL_TYPE_ERROR) {
			output = new ExclyLongError();
		} else {
			log.warn("The reader was unable to find a valid parser for the cell [Row, Column] (" + cell.getRowIndex()
					+ ", " + cell.getColumnIndex() + ")");
			output = new ExclyLongError();
		}

		return output;
	}

	/**
	 * Tries to read the value of the given cell. If it's possible to parse the
	 * value into a date it will return an ExclyDate with the parsed value.
	 * Otherwise a ExclyDateError or a ExclyDateBlank is returned, depending on
	 * the cell value wasn't parsable or blank. Both have a value of null.
	 * 
	 * @param cell
	 *            The Excel cell.
	 * @return Return the parsed value of the cell as an ExclyDate.
	 */
	public ExclyDate readDateCellValue(Cell cell) {
		ExclyDate output = null;

		if (cell == null) {
			return new ExclyDateError();
		}

		try {
			output = readDate(cell, cell.getCellType());
		} catch (Exception e) {
			log.error("The reader was unable to read the data from cell [Row, Column] (" + cell.getRowIndex() + ", "
					+ cell.getColumnIndex() + ")", e);
			output = new ExclyDateError();
		}

		return output;
	}

	private ExclyDate readDate(Cell cell, int type) throws Exception {
		ExclyDate output = null;

		if (type == Cell.CELL_TYPE_STRING) {
			String data = cell.getStringCellValue();
			if (isNumericGerman(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.GERMAN).parse(data);
				output = new ExclyDate(DateUtil.getJavaDate(number.intValue()));
			} else if (isNumericUK(data)) {
				Number number = NumberFormat.getNumberInstance(Locale.UK).parse(data);
				output = new ExclyDate(DateUtil.getJavaDate(number.intValue()));
			} else if (data.equals("") || data.equals(" ") || data.trim().equals("-")) {
				output = new ExclyDateBlank();
			} else {
				ExclyDate parsedDate = parse(cell.getStringCellValue());
				output = parsedDate;
			}
		} else if (type == Cell.CELL_TYPE_BLANK) {
			output = new ExclyDateBlank();
		} else if (type == Cell.CELL_TYPE_FORMULA) {
			int formulaType = cell.getCachedFormulaResultType();
			output = readDate(cell, formulaType);
		} else if (DateUtil.isCellDateFormatted(cell)) {
			Date data = cell.getDateCellValue();
			output = new ExclyDate(data);
		} else if (type == Cell.CELL_TYPE_NUMERIC) {
			double data = cell.getNumericCellValue();
			output = new ExclyDate(DateUtil.getJavaDate(data));
		} else if (type == Cell.CELL_TYPE_ERROR) {
			output = new ExclyDateError();
		} else {
			log.warn("The reader was unable to find a valid parser for the cell [Row, Column] (" + cell.getRowIndex()
					+ ", " + cell.getColumnIndex() + ")");
			output = new ExclyDateError();
		}

		return output;
	}

	private ExclyDate parse(String date) throws ParseException {
		if (date != null && !date.isEmpty()) {
			ExclyDate output = null;

			for (String format : formats) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				try {
					Date parsedDate = sdf.parse(date);
					output = new ExclyDate(parsedDate);
				} catch (ParseException e) {
				}
			}

			if (output != null) {
				return output;
			} else {
				throw new ParseException(date, 0);
			}
		} else {
			throw new ParseException(date, 0);
		}
	}
}
