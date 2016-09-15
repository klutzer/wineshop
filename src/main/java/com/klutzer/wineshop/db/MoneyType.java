package com.klutzer.wineshop.db;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.mentabean.DBType;

import com.klutzer.wineshop.business.Money;

class MoneyType implements DBType<Money> {

	@Override
	public Money getFromResultSet(ResultSet rset, int index) throws SQLException {
		
		Double value = rset.getDouble(index);
		
		if (rset.wasNull()) {
			return null;
		}
		
		return new Money(BigDecimal.valueOf(value));
	}

	@Override
	public void bindToStmt(PreparedStatement stmt, int index, Money value)
			throws SQLException {
		
		if (value == null)
			stmt.setNull(index, Types.NUMERIC);
		else
			stmt.setDouble(index, value.getAmount().doubleValue());
	}

	@Override
	public Money getFromResultSet(ResultSet rset, String field) throws SQLException {
		
		Double value = rset.getDouble(field);
		
		if (rset.wasNull()) {
			return null;
		}
		
		return new Money(BigDecimal.valueOf(value));
	}

	@Override
	public Class<? extends Object> getTypeClass() {
		return Money.class;
	}

	@Override
	public boolean canBeNull() {
		return true;
	}

	@Override
	public String getAnsiType() {
		return "numeric";
	}

}
