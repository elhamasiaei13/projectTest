package com.parvanpajooh.cargoarchive.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoTools {
	/**
	 * 
	 * @param rs
	 * @param strColName
	 * @return
	 * @throws SQLException
	 */
    static public Integer getInteger(ResultSet rs, String strColName) throws SQLException {
        int nValue = rs.getInt(strColName);
        return rs.wasNull() ? null : nValue;
    }
    
    /**
     * 
     * @param rs
     * @param strColName
     * @return
     * @throws SQLException
     */
    static public Long getLong(ResultSet rs, String strColName) throws SQLException {
        long nValue = rs.getLong(strColName);
        return rs.wasNull() ? null : nValue;
    }
}