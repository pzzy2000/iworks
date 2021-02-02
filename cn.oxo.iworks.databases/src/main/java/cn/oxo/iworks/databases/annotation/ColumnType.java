package cn.oxo.iworks.databases.annotation;

import java.sql.JDBCType;

public enum ColumnType {

	CHAR("CHAR", JDBCType.CHAR,String.class),
	// VARCHAR String
	VARCHAR("VARCHAR",JDBCType.VARCHAR ,String.class),
	// LONGVARCHAR String
	LONGVARCHAR("LONGVARCHAR",JDBCType.LONGVARCHAR ,String.class),
	// NUMERIC java.math.BigDecimal
	NUMERIC("NUMERIC", JDBCType.NUMERIC ,java.math.BigDecimal.class),
	// DECIMAL java.math.BigDecimal
	DECIMAL("DECIMAL",JDBCType.DECIMAL  ,java.math.BigDecimal.class),
	// BIT boolean
	BIT("BIT",JDBCType.BIT  ,boolean.class),
	// BOOLEAN boolean
	BOOLEAN("BOOLEAN",JDBCType.BOOLEAN ,Boolean.class),
	// TINYINT byte
	TINYINT("TINYINT", JDBCType.TINYINT ,byte.class),
	// SMALLINT short
	SMALLINT("SMALLINT",JDBCType.SMALLINT, short.class),
	// INTEGER int
	INTEGERint("INTEGER", JDBCType.INTEGER,int.class), 
	// INTEGER int
	INTEGERInteger("INTEGER", JDBCType.INTEGER,Integer.class),
	// BIGINT long
	BIGINTlong("BIGINT", JDBCType.BIGINT,long.class), 
	// BIGINT long
	BIGINTLong("BIGINT",JDBCType.BIGINT ,Long.class),
	// REAL float
	REAL("REAL", JDBCType.REAL ,float.class),
	// FLOAT double
	FLOAT("FLOAT", JDBCType.FLOAT ,double.class),
	// DOUBLE double
	DOUBLEdouble("DOUBLE", JDBCType.DOUBLE ,double.class), 
	// DOUBLE double
	DOUBLE("DOUBLE",  JDBCType.DOUBLE ,Double.class),
	
	ENUM("ENUM",  JDBCType.VARCHAR ,Enum.class),

	// BINARY byte[]
	BINARY("BINARY", JDBCType.BINARY ,byte[].class),
	// VARBINARY byte[]
	VARBINARY("VARBINARY",JDBCType.VARBINARY , byte[].class),
	// LONGVARBINARY byte[]
	LONGVARBINARY("LONGVARBINARY",JDBCType.LONGVARBINARY , byte[].class),
	// DATE java.sql.Date
	DATE("DATE", JDBCType.DATE ,java.util.Date.class),
	// TIME java.sql.Time
	TIME("TIME",  JDBCType.TIME , java.sql.Time.class),
	// TIMESTAMP java.sql.Timestamp
	TIMESTAMP("TIMESTAMP", JDBCType.TIMESTAMP , java.sql.Timestamp.class);
	// CLOB Clob
	// BLOB Blob
	// ARRAY Array
	// DISTINCT mapping of underlying type
	// STRUCT Struct
	// REF Ref
	// DATALINK java.net.URL

	private String type;

	private Class<?> javaType;
	
	private JDBCType jdbcType;
	

	private ColumnType(String type,JDBCType jdbcType, Class<?> javaType) {
		this.type = type;
		this.javaType = javaType;
		this.jdbcType = jdbcType;
	}

	public String getType() {
		return type;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public JDBCType getJdbcType() {
	    return jdbcType;
	}

}
