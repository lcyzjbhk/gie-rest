package cn.taotao.controller;

import cn.taotao.service.ItmService;
import cn.whjg.taotao.common.vo.DatagridResult;
import cn.whjg.taotao.common.vo.PictureResult;
import cn.whjg.taotao.common.vo.ResponseResult;
import cn.whjg.taotao.pojo.TbItem;
import cn.whjg.taotao.pojo.TbItemParam;
import cn.whjg.taotao.vo.CarTreeVo;
import cn.whjg.taotao.vo.ItemParamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
   ItmService ItmService;
@GetMapping("/item/list")
    public DatagridResult<TbItem> itemList(Integer page, Integer rows){
    return ItmService.selectItems(page,rows);
}
@PostMapping("/item/reshelf")
    public ResponseResult reshelf(String ids){
    return ItmService.reshelf(ids);
}
@GetMapping("/item/param/list")//规格参数列表
    public DatagridResult<ItemParamVo> paramList(Integer page, Integer rows){
    return ItmService.prarmList(page,rows);
}
@PostMapping("/item/cat/list")
    public List<CarTreeVo> catlist(@RequestParam(value = "id",defaultValue = "0",required = false) Long id){
    return ItmService.selectcatlist(id);
}
@GetMapping("/item/param/query/itemcatid/{catid}")
    public ResponseResult query(@PathVariable Long catid){
    return ItmService.selectBycatid(catid);

}
@PostMapping("//item/param/save/{Catid}")
    public ResponseResult paramsave(@PathVariable Long Catid,String paramData){
    return ItmService.insertParam(Catid,paramData);

}
@PostMapping("/item/upload")
    public PictureResult upload(MultipartFile images) throws Exception {
    return ItmService.upload(images);
}
@PostMapping("/item/save")
    public ResponseResult save(TbItem tbItem,String desc,String itemParams){
    return ItmService.insert(tbItem,desc,itemParams);
}
}
