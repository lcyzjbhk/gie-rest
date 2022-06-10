package cn.whjg.taotao.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_item_desc
 * @author 
 */
@Data
public class TbItemDesc implements Serializable {
    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 商品描述
     */
    private String itemDesc;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    private static final long serialVersionUID = 1L;
}