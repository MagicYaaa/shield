package com.weapp.api.domain.response;

public class BaseResp<T> {
   public int code;
   public String msg;
   public T data;

   public BaseResp() {
   }

   public BaseResp(int code, String msg) {
      this.code = code;
      this.msg = msg;
   }

   public BaseResp(int code, String msg, T data) {
      this.code = code;
      this.msg = msg;
      this.data = data;
   }
}
