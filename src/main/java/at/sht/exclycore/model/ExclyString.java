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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * This class represents an ExclyString data type.
 * 
 * @author Mathias Markl
 */
public class ExclyString implements ExclyDataType, Comparable<ExclyString> {
	protected boolean error;
	private String data;

	/**
	 * Constructs a new ExclyString.
	 * 
	 * @param data
	 *            The value of the ExclyString.
	 */
	public ExclyString(String data) {
		this.data = data;
		this.error = false;
	}

	@Override
	public void setCell(Cell cell) {
		setCell(cell, cell.getCellStyle());
	}

	@Override
	public void setCell(Cell cell, CellStyle cellStyle) {
		cell.setCellValue(data);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * Returns the value.
	 * 
	 * @return The value as a string.
	 */
	public String getData() {
		return data;
	}

	/**
	 * Checks if the ExclyString has an error.
	 * 
	 * @return Returns true if the ExclyString has an error, otherwise it will
	 *         return false.
	 */
	public boolean isError() {
		return error;
	}

	@Override
	public String toString() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + (error ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExclyString other = (ExclyString) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (error != other.error)
			return false;
		return true;
	}

	@Override
	public int compareTo(ExclyString other) {
		if (!this.error && other.error) {
			return -1;
		} else if (this.error && !other.error) {
			return 1;
		} else if (this.error && other.error) {
			return 0;
		} else {
			return this.data.compareTo(other.data);
		}
	}

	/**
	 * Checks if the ExclyString starts with a given ExclyString.
	 * 
	 * @param other
	 *            The ExclyString to check.
	 * @return Returns true if the ExclyString starts with the given ExclyString
	 *         or false if it doesn't start with the ExclyString.
	 */
	public boolean startsWith(ExclyString other) {
		if (!this.error && other.error) {
			return false;
		} else if (this.error && !other.error) {
			return false;
		} else if (this.error && other.error) {
			return false;
		}

		if (this.data == null) {
			return false;
		}

		if (other.data == null) {
			return false;
		}

		if (this.data.startsWith(other.data)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Appends multiple ExclyString together.
	 * 
	 * @param values
	 *            ExclyStrings which should be added.
	 * @return Return an ExclyString with the whole string.
	 */
	public static ExclyString append(ExclyString... values) {
		ExclyStringError error = null;
		String data = null;

		for (ExclyString value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyStringError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyStringError();
				} else {
					data = data + value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyString(data);
	}
}
