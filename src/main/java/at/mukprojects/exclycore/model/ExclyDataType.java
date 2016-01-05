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
 * High level representation of an Excly data type.
 * 
 * @author Mathias Markl
 */
public interface ExclyDataType {
	public final String ERRORCODE = "###ERROR###";

	/**
	 * Sets a cell without a given style. If the cell contains an error the cell
	 * style will adapt to make the error more visible.
	 * 
	 * @param cell
	 *            The cell which should be set.
	 */
	public void setCell(Cell cell);

	/**
	 * Sets a cell with a given style. If the cell contains an error the cell
	 * style will adapt to make the error more visible.
	 * 
	 * @param cell
	 *            The cell which should be set.
	 * @param cellStyle
	 *            The cell style which should be set.
	 */
	public void setCell(Cell cell, CellStyle cellStyle);
}
