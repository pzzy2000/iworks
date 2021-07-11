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

package cn.oxo.iworks.web.controller.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 绑定请求参数到模型，并且暴露到模型中供页面使用
 * </p>
 * <p>
 * 不同于@ModelAttribute
 * </p>
 * 
 * @author Zhang Kaitao
 *
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormModel {

      public String parameter_null = "null";

      public enum Format {
            defaults, json
      }

      /**
       * ParameterName
       * 
       * @return
       */
      String parameterName() default parameter_null;

      Format format() default Format.defaults;
      
      String verification() default parameter_null;

}
