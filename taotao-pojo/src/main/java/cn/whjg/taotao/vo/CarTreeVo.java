package cn.whjg.taotao.vo;

import cn.whjg.taotao.pojo.TbItemCat;
import lombok.Data;

@Data
public class CarTreeVo extends TbItemCat {
    private String text;
    private String state;

    public String getText() {
        return this.getName();
    }

    public String getState() {
        return this.getIsParent()?"closed":"open";
    }
}
