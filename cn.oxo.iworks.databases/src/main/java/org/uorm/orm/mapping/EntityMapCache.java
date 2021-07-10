package org.uorm.orm.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.oxo.iworks.databases.annotation.Column;
import cn.oxo.iworks.databases.annotation.Table;

public class EntityMapCache {
    private static final Logger logger = LogManager.getLogger(EntityMapCache.class);
    private Table table;

    private List<Field> id = new ArrayList<>();

    private List<Field> fields = new ArrayList<>();

    private Map<String, Field> fieldMap = new HashMap<String, Field>();

    public void init() {
        logger.info("entity cache table : " + table.name());
        for (Field id_ : id) {
            fieldMap.put(id_.getAnnotation(Column.class).name(), id_);
        }
        for (Field field : fields) {
            fieldMap.put(field.getAnnotation(Column.class).name(), field);
        }
    }

    public Field searchByColumnName(String name) {
        return fieldMap.get(name);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Field> getId() {
        return id;
    }

    public void setId(List<Field> id) {
        this.id = id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

}
