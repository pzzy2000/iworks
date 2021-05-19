package cn.oxo.iworks.databases;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class SQLUilts {

      public static enum OptType {
            save, update, remove, search, init
      }

      @SuppressWarnings("unchecked")
      public static <V> V getValue(Object object, Class<V> clazz) {
            if (object instanceof String) {
                  return (V) object;
            } else if (object instanceof BigDecimal) {
                  if (clazz.equals(Double.class)) {

                        return (V) Double.valueOf(((BigDecimal) object).doubleValue());
                  } else if (clazz.equals(Integer.class)) {
                        return (V) Integer.valueOf(((BigDecimal) object).intValue());
                  }
                  if (clazz.equals(Long.class)) {
                        return (V) Long.valueOf(((BigDecimal) object).longValue());
                  } else {
                        return (V) ((BigDecimal) object).toString();
                  }

            } else
                  return clazz.cast(object);

      }

      // public static String desc(String in) {
      // return ToolsUnits.isNOtNulll(in) ? in : "desc";
      // }

      public static String getLike(String in) {
            return "%" + in + "%";
      }

      public static String generatorUUID() {
            UUID uuid = UUID.randomUUID();
            return uuid.toString();
      }

      public static String getRandomChar(int pwd_len) {
            int i;
            int count = 0;
            char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
                        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

            StringBuffer pwd = new StringBuffer("");
            Random r = new Random();
            while (count < pwd_len) {

                  i = Math.abs(r.nextInt(str.length));

                  if (i >= 0 && i < str.length) {
                        pwd.append(str[i]);
                        count++;
                  }
            }
            return pwd.toString();
      }

      public static String generateColor(int len) {
            if (len > 6 || len <= 0)
                  len = 6;
            int i;
            int count = 0;
            char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            StringBuffer pwd = new StringBuffer("#");
            Random r = new Random();
            while (count < len) {

                  i = Math.abs(r.nextInt(str.length));

                  if (i >= 0 && i < str.length) {
                        pwd.append(str[i]);
                        count++;
                  }
            }
            return pwd.toString();
      }

      public static String getUniqueId(int len) {
            if (len < 10)
                  len = 10;
            return getUniqueId(len, 999999999);
      }

      /**
       * 
       * 
       * @param
       * @return
       */
      public static String getUniqueId() {

            return getUniqueId(10, 999999999);
      }

      public static int getIUniqueId(int len) {
            return Integer.parseInt(getUniqueId(len, 999999999));
      }

      /**
       * 
       * 
       * @return
       */
      public static int getIUniqueId() {
            return Integer.parseInt(getUniqueId(10, 999999999));
      }

      private static String getUniqueId(int length, int maxrandom) {
            String tmpstr = "";
            String thread = (new SimpleDateFormat("yyyyMMddhhmmssSSS")).format(new Date()) + Integer.toString(getRandom(maxrandom));
            thread = Integer.toString(thread.hashCode());

            if (thread.indexOf("-") >= 0)
                  thread = thread.substring(thread.indexOf("-") + 1);
            if (thread.length() < length) {
                  for (int i = thread.length(); i < length; i++)
                        tmpstr = tmpstr + "0";

                  thread = tmpstr + thread;
            }
            return thread;
      }

      public static int getRandom(int max) {
            return (int) (Math.random() * max);
      }

}
