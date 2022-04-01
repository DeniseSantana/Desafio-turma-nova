package br.com.santander.core.sheet;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * Helper class encapsulating some functionality of the Fillo lib
 *
 */
public class SheetReader implements AutoCloseable {

	private static Fillo fillo = new Fillo();
	private Connection connection;

	public SheetReader(String pathToFile) {		
		synchronized (fillo) {			
			try {
				this.connection = fillo.getConnection(pathToFile);
			} catch (FilloException e) {
				throw new SheetException(e.getMessage(), e);
			}
		}
	}

	public Recordset performQuery(String query) {
		try {
			return connection.executeQuery(query);
		} catch (FilloException e) {
			throw new SheetException(e.getMessage(), e);
		}
	}

	public int performUpdate(String query) {
		try {
			return connection.executeUpdate(query);
		} catch (FilloException e) {
			throw new SheetException(e.getMessage(), e);
		}
	}

	/**
	 * Helper to read a field from a sheet
	 * 
	 * @param recordset
	 * @param fieldName
	 * @return if the field has data a String with the data else an empty string
	 */
	public String getField(Recordset recordset, String fieldName) {
		try {
			return recordset.getField(fieldName);
		} catch (FilloException e) {
			return "";
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void close() throws Exception {
		connection.close();
	}

}
