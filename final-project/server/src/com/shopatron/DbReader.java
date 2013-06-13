package com.shopatron;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbReader<T> {
    /**
     * Read in a T, DO NOT STORE THE RESULT SET
     */
    T read(ResultSet results) throws SQLException;
}
