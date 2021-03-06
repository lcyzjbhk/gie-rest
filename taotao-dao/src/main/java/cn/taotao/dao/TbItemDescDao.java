package cn.taotao.dao;

import cn.whjg.taotao.pojo.TbItemDesc;
import cn.whjg.taotao.pojo.TbItemDescExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbItemDescDao {
    long countByExample(TbItemDescExample example);

    int deleteByExample(TbItemDescExample example);

    int deleteByPrimaryKey(Long itemId);

    int insert(TbItemDesc record);

    int insertSelective(TbItemDesc record);

    List<TbItemDesc> selectByExample(TbItemDescExample example);

    TbItemDesc selectByPrimaryKey(Long itemId);

    int updateByExampleSelective(@Param("record") TbItemDesc record, @Param("example") TbItemDescExample example);

    int updateByExample(@Param("record") TbItemDesc record, @Param("example") TbItemDescExample example);

    int updateByPrimaryKeySelective(TbItemDesc record);

    int updateByPrimaryKey(TbItemDesc record);
}