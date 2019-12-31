package com.cskaoyan.demo;

import com.cskaoyan.util.Md5Util;
import org.junit.jupiter.api.Test;

public class Md5Test {

     @Test
     public void mytest1() throws Exception {
         String md5 = Md5Util.getMd5("admin123", "Pined");
         System.out.println(md5);
     }
}
