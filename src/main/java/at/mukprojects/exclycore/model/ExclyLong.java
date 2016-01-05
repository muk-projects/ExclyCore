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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * This class represents an ExclyLong data type.
 * 
 * @author Mathias Markl
 */
public class ExclyLong implements ExclyDataType, Comparable<ExclyLong> {

	protected boolean error;
	private Long data;

	/**
	 * Constructs a new ExclyLong.
	 * 
	 * @param data
	 *            The value of the ExclyLong.
	 */
	public ExclyLong(long data) {
		this.data = data;
		this.error = false;
	}

	/**
	 * Constructs a new ExclyLong.
	 * 
	 * @param data
	 *            The value of the ExclyLong.
	 */
	public ExclyLong(double data) {
		this((long) data);
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
	 * @return The value as a Java long.
	 */
	public long getData() {
		return data;
	}

	/**
	 * Checks if the ExclyLong has an error.
	 * 
	 * @return Returns true if the ExclyLong has an error, otherwise it will
	 *         return false.
	 */
	public boolean isError() {
		return error;
	}

	@Override
	public String toString() {
		return data + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (data * 1000);
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
		ExclyLong other = (ExclyLong) obj;
		if (data != other.data)
			return false;
		if (error != other.error)
			return false;
		return true;
	}

	@Override
	public int compareTo(ExclyLong other) {
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
	 * Adds multiple ExclyLong together. If any of the ExclyLong has an error,
	 * the function will return an ExclyLong from the type ExclyLongError.
	 * 
	 * @param values
	 *            ExclyLong which should be added together.
	 * @return Return the sum as an ExclyLong.
	 */
	public static ExclyLong add(ExclyLong... values) {
		ExclyLongError error = null;
		Long data = null;

		for (ExclyLong value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyLongError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyLongError();
				} else {
					data = data + value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyLong(data);
	}

	/**
	 * Subtracts multiple ExclyLong from the first input. If any of the
	 * ExclyDoubles has an error, the function will return an ExclyLong from the
	 * type ExclyLongError.
	 * 
	 * @param values
	 *            ExclyLong which should be subtracted.
	 * @return Return the result as an ExclyLong.
	 */
	public static ExclyLong sub(ExclyLong... values) {
		ExclyLongError error = null;
		Long data = null;

		for (ExclyLong value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyLongError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyLongError();
				} else {
					data = data - value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyLong(data);
	}
}
