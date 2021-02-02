package cn.oxo.iworks.builds.base;




import cn.oxo.iworks.databases.annotation.ColumnType;
import cn.oxo.iworks.databases.annotation.Index;
import cn.oxo.iworks.databases.annotation.SelectType;

public class ColumnBean {

    private String name;

    private ColumnType columnType;

    private SelectType selectType;

    private int length;
    
    private int scale;

    private Class<?> fieldType;

    private String fieldName;

    private boolean canNull;

    private String desc;
    
    private Index  index;
    
    private String defaultValue;

    public String getName() {

	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public ColumnType getColumnType() {

	return columnType;
    }
    
   

    public void setColumnType(ColumnType columnType) {
	this.columnType = columnType;
    }

    public SelectType getSelectType() {
	return selectType;
    }

    public void setSelectType(SelectType selectType) {
	this.selectType = selectType;
    }

    public int getLength() {
	return length;
    }

    public void setLength(int length) {
	this.length = length;
    }

    public Class<?> getFieldType() {
	return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
	this.fieldType = fieldType;
    }

    public String getFieldName() {
	return fieldName;
    }

    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }

    public boolean isCanNull() {
	return canNull;
    }

    public void setCanNull(boolean canNull) {
	this.canNull = canNull;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
    public String toString() {
	return "ColumnBean [name=" + name + ", columnType=" + columnType + ", selectType=" + selectType + ", length="
		+ length + ", fieldType=" + fieldType + ", fieldName=" + fieldName + ", canNull=" + canNull + ", desc="
		+ desc + ", index=" + index + ", defaultValue=" + defaultValue + "]";
    }

    

}
