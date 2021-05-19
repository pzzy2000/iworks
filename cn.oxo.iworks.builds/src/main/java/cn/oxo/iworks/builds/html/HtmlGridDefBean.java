package cn.oxo.iworks.builds.html;

import cn.oxo.iworks.builds.html.FieldParams.FieldShow;

public class HtmlGridDefBean {

      private HtmlGridDef htmlGridDef;

      private String name;

      private Object detail;

      public HtmlGridDef getHtmlGridDef() {
            return htmlGridDef;
      }

      public void setHtmlGridDef(HtmlGridDef htmlGridDef) {
            this.htmlGridDef = htmlGridDef;
      }

      public Object getDetail() {
            return detail;
      }

      public void setDetail(Object detail) {
            this.detail = detail;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public boolean isGrid() {
            for (FieldShow fieldShow : htmlGridDef.fieldShow()) {
                  if (fieldShow.equals(FieldShow.Grid)) {
                        return true;
                  }
            }
            return false;
      }

      public boolean isFieldShow(String fieldShow_) {
            for (FieldShow fieldShow : htmlGridDef.fieldShow()) {
                  if (fieldShow.name().equals(fieldShow_)) {
                        return true;
                  }
            }
            return false;
      }

}
