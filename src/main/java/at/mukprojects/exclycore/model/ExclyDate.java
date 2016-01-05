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

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * This class represents an ExclyDate data type.
 * 
 * @author Mathias Markl
 */
public class ExclyDate implements ExclyDataType, Comparable<ExclyDate> {
	protected boolean error;
	private Date data;

	/**
	 * Constructs a new ExclyDate.
	 * 
	 * @param data
	 *            The value of the ExclyDate.
	 */
	public ExclyDate(Date data) {
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
	 * @return The value as a Java date.
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Checks if the ExclyDate has an error.
	 * 
	 * @return Returns true if the ExclyDate has an error, otherwise it will
	 *         return false.
	 */
	public boolean isError() {
		return error;
	}

	@Override
	public String toString() {
		return data.toString();
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
		ExclyDate other = (ExclyDate) obj;
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
	public int compareTo(ExclyDate other) {
		if ((!this.error && other.error) || (this.data != null && other.data == null)) {
			return -1;
		} else if ((this.error && !other.error) || (this.data == null && other.data != null)) {
			return 1;
		} else if ((this.error && other.error) || (this.data == null && other.data == null)) {
			return 0;
		} else {
			return this.data.compareTo(other.data);
		}
	}

	/**
	 * Adds multiple ExclyDates together. If any of the ExclyDates has an error,
	 * the function will return an ExclyDate from the type ExclyDateError.
	 * 
	 * @param dates
	 *            ExclyDates which should be added together.
	 * @return Return the sum as an ExclyDate.
	 */
	public static ExclyDate add(ExclyDate... dates) {
		ExclyDateError error = null;
		Date dateData = null;

		for (ExclyDate date : dates) {
			if (dateData == null) {
				if (date.isError()) {
					error = new ExclyDateError();
				} else {
					dateData = date.getData();
				}
			} else {
				if (date.isError()) {
					error = new ExclyDateError();
				} else {
					if (date.getData() != null) {
						dateData = new Date(dateData.getTime() + date.getData().getTime());
					}
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyDate(dateData);
	}

	/**
	 * Subtracts multiple ExclyDates from the first input. If any of the
	 * ExclyDates has an error, the function will return an ExclyDate from the
	 * type ExclyDateError.
	 * 
	 * @param dates
	 *            ExclyDates which should be subtracted.
	 * @return Return the result as an ExclyDate.
	 */
	public static ExclyDate sub(ExclyDate... dates) {
		ExclyDateError error = null;
		Date dateData = null;

		for (ExclyDate date : dates) {
			if (dateData == null) {
				if (date.isError()) {
					error = new ExclyDateError();
				} else {
					dateData = date.getData();
				}
			} else {
				if (date.isError()) {
					error = new ExclyDateError();
				} else {
					if (date.getData() != null) {
						dateData = new Date(dateData.getTime() - date.getData().getTime());
					}
				}
			}
			if (error != null) {
				return error;
			}
		}

		return new ExclyDate(dateData);
	}
}
