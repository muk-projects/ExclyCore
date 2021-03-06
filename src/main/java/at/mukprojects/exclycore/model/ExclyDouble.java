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
 * This class represents an ExclyDouble data type.
 * 
 * @author Mathias Markl
 */
public class ExclyDouble implements ExclyDataType, Comparable<ExclyDouble> {
	protected boolean error;
	private Double data;

	/**
	 * Constructs a new ExclyDouble.
	 * 
	 * @param data
	 *            The value of the ExclyDouble.
	 */
	public ExclyDouble(double data) {
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
	 * @return The value as a Java double.
	 */
	public double getData() {
		return data;
	}

	/**
	 * Checks if the ExclyDouble has an error.
	 * 
	 * @return Returns true if the ExclyDouble has an error, otherwise it will
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
		long temp;
		temp = Double.doubleToLongBits(data);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ExclyDouble other = (ExclyDouble) obj;
		if (Double.doubleToLongBits(data) != Double.doubleToLongBits(other.data))
			return false;
		if (error != other.error)
			return false;
		return true;
	}

	@Override
	public int compareTo(ExclyDouble other) {
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
	 * Adds multiple ExclyDoubles together. If any of the ExclyDoubles has an
	 * error, the function will return an ExclyDouble from the type
	 * ExclyDoubleError.
	 * 
	 * @param values
	 *            ExclyDouble which should be added together.
	 * @return Return the sum as an ExclyDouble.
	 */
	public static ExclyDouble add(ExclyDouble... values) {
		ExclyDoubleError error = null;
		Double data = null;

		for (ExclyDouble value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = data + value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyDouble(data);
	}

	/**
	 * Subtracts multiple ExclyDoubles from the first input. If any of the
	 * ExclyDoubles has an error, the function will return an ExclyDouble from
	 * the type ExclyDoubleError.
	 * 
	 * @param values
	 *            ExclyDoubles which should be subtracted.
	 * @return Return the result as an ExclyDouble.
	 */
	public static ExclyDouble sub(ExclyDouble... values) {
		ExclyDoubleError error = null;
		Double data = null;

		for (ExclyDouble value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = data - value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyDouble(data);
	}

	/**
	 * Multiplies multiple ExclyDoubles together. If any of the ExclyDoubles has
	 * an error, the function will return an ExclyDouble from the type
	 * ExclyDoubleError.
	 * 
	 * @param values
	 *            ExclyDoubles which should be multiplied.
	 * @return Return the result as an ExclyDouble.
	 */
	public static ExclyDouble multi(ExclyDouble... values) {
		ExclyDoubleError error = null;
		Double data = null;

		for (ExclyDouble value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = data * value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyDouble(data);
	}

	/**
	 * Divides multiple ExclyDoubles from the first input. If any of the
	 * ExclyDoubles has an error, the function will return an ExclyDouble from
	 * the type ExclyDoubleError.
	 * 
	 * @param values
	 *            ExclyDoubles which should be divided.
	 * @return Return the result as an ExclyDouble.
	 */
	public static ExclyDouble div(ExclyDouble... values) {
		ExclyDoubleError error = null;
		Double data = null;

		for (ExclyDouble value : values) {
			if (data == null) {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = value.getData();
				}
			} else {
				if (value.isError()) {
					error = new ExclyDoubleError();
				} else {
					data = data / value.getData();
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyDouble(data);
	}
}
