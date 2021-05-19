package cn.oxo.iworks.builds.html;

import java.util.ArrayList;
import java.util.List;

public class ModuleDifBean {

      private ModuleDif moduleDif;

      private List<HtmlGridDefBean> htmlGridDefBeans = new ArrayList<HtmlGridDefBean>();

      public ModuleDif getModuleDif() {
            return moduleDif;
      }

      public void setModuleDif(ModuleDif moduleDif) {
            this.moduleDif = moduleDif;
      }

      public List<HtmlGridDefBean> getHtmlGridDefBeans() {
            return htmlGridDefBeans;
      }

      public void setHtmlGridDefBeans(List<HtmlGridDefBean> htmlGridDefBeans) {
            this.htmlGridDefBeans = htmlGridDefBeans;
      }

      // public List<List<HtmlGridDefBean>> listHtmlGridDefBeans(String
      // fieldShow){
      //
      // List<HtmlGridDefBean> filer =new ArrayList<HtmlGridDefBean>();
      //
      // for(HtmlGridDefBean iHtmlGridDefBean : htmlGridDefBeans) {
      // for(FieldShow iFieldShow :
      // iHtmlGridDefBean.getHtmlGridDef().fieldShow() ) {
      // if(iFieldShow.name().equals(fieldShow)){
      // filer.add(iHtmlGridDefBean);
      // }
      // }
      // }
      //
      // int xx= filer.size()/2 +((filer.size()%2 ==0)?0:1);
      //
      // for(int i=0;i< xx;i++) {
      //
      //
      // }
      //
      // }

}
