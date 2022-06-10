package cn.whjg.taotao.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_item_param_item
 * @author 
 */
@Data
public class TbItemParamItem implements Serializable {
    private Long id;

    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 参数数据，格式为json格式
     */
    private String paramData;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}