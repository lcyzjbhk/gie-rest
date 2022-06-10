package cn.whjg.taotao.common.vo;

import lombok.Data;

@Data
public class PictureResult {
    private Integer error;
    private String msg;
    private String url;

    public static PictureResult ok(String url){
        PictureResult result = new PictureResult();
        result.error = 0;
        result.msg = "success";
        result.url = url;
        return result;
    }
    public static PictureResult error(){
        PictureResult result = new PictureResult();
        result.error = 1;
        result.msg = "fail";
        return result;
    }
}
