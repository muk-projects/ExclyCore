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

package at.mukprojects.exclycore.helper;

import org.apache.poi.ss.usermodel.Cell;

import at.mukprojects.exclycore.dao.XLSXReader;
import at.mukprojects.exclycore.model.ExclyDate;
import at.mukprojects.exclycore.model.ExclyDouble;
import at.mukprojects.exclycore.model.ExclyInteger;
import at.mukprojects.exclycore.model.ExclyLong;
import at.mukprojects.exclycore.model.ExclyString;

/**
 * XLSXReaderUtil is an minimal implementation of the
 * {@link at.mukprojects.exclycore.dao.XLSXReader XLSXReader}.
 * 
 * @author Mathias Markl
 */
class XLSXReaderUtil extends XLSXReader {

	@Override
	public ExclyString readStringCellValue(Cell cell) {
		return super.readStringCellValue(cell);
	}

	@Override
	public ExclyDouble readDoubleCellValue(Cell cell) {
		return super.readDoubleCellValue(cell);
	}

	@Override
	public ExclyInteger readIntegerCellValue(Cell cell) {
		return super.readIntegerCellValue(cell);
	}

	@Override
	public ExclyLong readLongCellValue(Cell cell) {
		return super.readLongCellValue(cell);
	}

	@Override
	public ExclyDate readDateCellValue(Cell cell) {
		return super.readDateCellValue(cell);
	}
}