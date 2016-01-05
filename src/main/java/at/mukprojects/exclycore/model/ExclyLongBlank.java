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
 * This class represents an ExclyLongBlank data type.
 * 
 * @author Mathias Markl
 */
public class ExclyLongBlank extends ExclyLong {

	/**
	 * Constructs a new ExclyLong.
	 */
	public ExclyLongBlank() {
		super(0);
		this.error = false;
	}

	@Override
	public void setCell(Cell cell, CellStyle cellStyle) {
		cell.setCellStyle(cellStyle);
	}

	@Override
	public String toString() {
		return "";
	}

}
