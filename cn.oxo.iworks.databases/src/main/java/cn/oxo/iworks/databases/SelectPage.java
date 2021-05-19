package cn.oxo.iworks.databases;

import java.util.Collections;
import java.util.List;

// @ApiModel(value="查询结果")
public class SelectPage<V> {

      private Long total;

      private List<V> records = Collections.emptyList();

      private long size = 10;

      private long current = 1;

      public Long getTotal() {
            return total;
      }

      public void setTotal(Long total) {
            this.total = total;
      }

      public List<V> getRecords() {
            return records;
      }

      public void setRecords(List<V> records) {
            this.records = records;
      }

      public long getSize() {
            return size;
      }

      public void setSize(long size) {
            this.size = size;
      }

      public long getCurrent() {
            return current;
      }

      public void setCurrent(long current) {
            this.current = current;
      }

}
