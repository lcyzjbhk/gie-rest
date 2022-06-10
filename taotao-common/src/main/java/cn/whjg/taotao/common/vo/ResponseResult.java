package cn.whjg.taotao.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
         private Integer status;
         private String msg;
         private Object data;
         public static ResponseResult ok() {
             ResponseResult result = new ResponseResult();
             result.status =200;
             result.msg = "success";
             return result;
         }
    public static ResponseResult ok(Object data) {
        ResponseResult result = ok();
        result.data = data;
        return result;
    }
    public static ResponseResult error() {
        ResponseResult result = new ResponseResult();
        result.status =400;
        result.msg = "fail";
        return result;
    }
    public static ResponseResult error(Object data) {
        ResponseResult result = error();
        result.data = data;
        return result;
    }
}
