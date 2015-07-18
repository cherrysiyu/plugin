package org.apache.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

/**
 * 
 * <code>ResultSetHandler</code> implementation that converts one
 * <code>ResultSet</code> String into a <code>List</code> of
 * <code>Object</code>s. This class is thread safe.
 *
 * @see org.apache.commons.dbutils.ResultSetHandler
 * @Description :
 * @author： 李波
 * @version 1.0
 * @date:2011-11-29
 */
public class StringListHandler extends AbstractListHandler<String> {
	 /**
     * The column number to retrieve.
     */
    private final int columnIndex;

    /**
     * The column name to retrieve.  Either columnName or columnIndex
     * will be used but never both.
     */
    private final String columnName;
    
    /**
     * Creates a new instance of StringListHandler.  The first column of each
     * row will be returned from <code>handle()</code>.
     */
	public StringListHandler() {
		this(1, null);
	}

	/**
     * Creates a new instance of StringListHandler.
     *
     * @param columnIndex The index of the column to retrieve from the
     * <code>ResultSet</code>.
     */
	public StringListHandler(int columnIndex) {
		this(columnIndex, null);
	}

	/**
     * Creates a new instance of StringListHandler.
     *
     * @param columnName The name of the column to retrieve from the
     * <code>ResultSet</code>.
     */
	public StringListHandler(String columnName) {
		this(1, columnName);
	}

	/** Private Helper
     * @param columnIndex The index of the column to retrieve from the
     * <code>ResultSet</code>.
     * @param columnName The name of the column to retrieve from the
     * <code>ResultSet</code>.
     */
	private StringListHandler(int columnIndex, String columnName) {
		 super();
		this.columnIndex = columnIndex;
		this.columnName = columnName;
	}
	
	/**
     * Returns one <code>ResultSet</code> column value as <code>Object</code>.
     * @param rs <code>ResultSet</code> to process.
     * @return <code>Object</code>, never <code>null</code>.
     *
     * @throws SQLException if a database access error occurs
     *
     * @see org.apache.commons.dbutils.handlers.AbstractListHandler#handle(ResultSet)
     */
    @Override
	protected String handleRow(ResultSet rs) throws SQLException {
		Object val = null;
		if (this.columnName == null) {
			val = rs.getObject(this.columnIndex);
		} else {
			val = rs.getObject(this.columnName);
		}

		return ((val == null) ? "" : val.toString());
	}
}
