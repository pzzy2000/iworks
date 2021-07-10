/*
 * Copyright 2002-2007 the original author or authors. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package cn.oxo.iworks.databases.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CHAR String VARCHAR String LONGVARCHAR String NUMERIC java.math.BigDecimal
 * DECIMAL java.math.BigDecimal BIT boolean BOOLEAN boolean TINYINT byte
 * SMALLINT short INTEGER int BIGINT long REAL float FLOAT double DOUBLE double
 * BINARY byte[] VARBINARY byte[] LONGVARBINARY byte[] DATE java.sql.Date TIME
 * java.sql.Time TIMESTAMP java.sql.Timestamp CLOB Clob BLOB Blob ARRAY Array
 * DISTINCT mapping of underlying type STRUCT Struct REF Ref DATALINK
 * java.net.URL
 */

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

      // public enum SelectType{
      // LIKE,EQ
      // }

      public String name();

      public ColumnType columnType();

      public SelectType selectType() default SelectType.EQ;

      public int length() default -1;

      public int scale() default -1;

      public String desc() default "";

      public String defaultValue() default "";

      public boolean isCanNull() default false;
      
      public  boolean isCache() default true;

}
