package cn.oxo.iworks.web;

import java.util.Date;

public class LoginSessionBean {

      private Long id;

      private String name;

      private String phone;

      private String access;

//      private UserType userType;

      private Long roleId;

      private Date createDate;

      private String token;

      public Long getId() {
            return id;
      }

      public void setId(Long id) {
            this.id = id;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getPhone() {
            return phone;
      }

      public void setPhone(String phone) {
            this.phone = phone;
      }

      public String getAccess() {
            return access;
      }

      public void setAccess(String access) {
            this.access = access;
      }

      
      public Date getCreateDate() {
            return createDate;
      }

      public void setCreateDate(Date createDate) {
            this.createDate = createDate;
      }

      public String getToken() {
            return token;
      }

      public void setToken(String token) {
            this.token = token;
      }

      public Long getRoleId() {
            return roleId;
      }

      public void setRoleId(Long roleId) {
            this.roleId = roleId;
      }

}
