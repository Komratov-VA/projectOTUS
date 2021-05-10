package com.otus.dao.model;

public enum Gender {
   m("мужчина"),
   w("женщина");

   String value;

   Gender(String value) {
      this.value = value;
   }
   public String getValue() {
      return value;
   }
}
