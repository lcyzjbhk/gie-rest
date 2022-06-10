package cn.taotao.service;

import cn.taotao.dao.*;
import cn.whjg.taotao.common.utils.SFtpUtils;
import cn.whjg.taotao.common.vo.DatagridResult;
import cn.whjg.taotao.common.vo.PictureResult;
import cn.whjg.taotao.common.vo.ResponseResult;
import cn.whjg.taotao.pojo.*;
import cn.whjg.taotao.vo.CarTreeVo;
import cn.whjg.taotao.vo.ItemParamVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItmService {
    @Value("${SFTP_HOSTNAME}")
    String hostname;
    @Value("${SFTP_PORT}")
    int port;
    @Value("${SFTP_USERNAME}")
    String username;
    @Value("${SFTP_PASSWORD}")
    String password;
    @Value("${SFTP_TIMEOUT}")
    int timeout;
    @Value("${SFTP_FILEPATH}")
    String filePath;
    @Value("${SFTP_IMGPATH}")
    String imgPath;

    @Autowired
    TbItemDao tbItemDao;
    @Autowired
    TbItemParamDao tbItemParamDao;
    @Autowired
    TbItemCatDao tbItemCatDao;
    @Autowired
    TbItemDescDao tbItemDescDao;
    @Autowired
    TbItemParamItemDao tbItemParamItemDao;

    public DatagridResult<TbItem> selectItems(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = tbItemDao.selectByExample(null);
        PageInfo<TbItem> info = new PageInfo<>(tbItems);
        return new DatagridResult<>(info.getTotal(),tbItems);
    }

    public ResponseResult reshelf(String ids) {
        TbItem tbItem = new TbItem();
        tbItem.setStatus((byte) 1);
        List<String> list = Arrays.asList(ids.split(","));
        List<Long> isLong = list.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
        TbItemExample example = new TbItemExample();
        example.createCriteria().andIdIn(isLong);
        int i = tbItemDao.updateByExampleSelective(tbItem, example);
        if (i>0){
             return ResponseResult.ok();
        }else {
            return ResponseResult.error();
        }
    }


    public DatagridResult<ItemParamVo> prarmList(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<TbItemParam> tbItemParams = tbItemParamDao.selectByExample(null);
        PageInfo<TbItemParam> info = new PageInfo<>(tbItemParams);
        List<ItemParamVo> voList = tbItemParams.stream().map(i -> {
            ItemParamVo vo = new ItemParamVo();

            BeanUtils.copyProperties(i, vo);
            TbItemCat cat = tbItemCatDao.selectByPrimaryKey(i.getItemCatId());
            vo.setItemCatName(cat.getName());
            return vo;
        }).collect(Collectors.toList());
        return new DatagridResult<>(info.getTotal(),voList);

    }
//查询数控键列表
    public List<CarTreeVo> selectcatlist(Long id) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbItemCat> tbItemCats = tbItemCatDao.selectByExample(example);
        return tbItemCats.stream().map(cat->{
                CarTreeVo vos = new CarTreeVo();
                BeanUtils.copyProperties(cat,vos);
                return vos;
                }
                ).collect(Collectors.toList());

    }

    public ResponseResult selectBycatid(Long catid) {
        TbItemParamExample example= new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catid);
        List<TbItemParam> tbItemParams = tbItemParamDao.selectByExample(example);
        if (tbItemParams!=null && tbItemParams.size()>0) {
        return ResponseResult.ok(tbItemParams.get(0));
        }else {
            return ResponseResult.error();
        }
    }

    public ResponseResult insertParam(Long catid,String paramData) {
        TbItemParam param = new TbItemParam();
        param.setItemCatId(catid);
        param.setParamData(paramData);
        param.setCreated(new Date());
        param.setUpdated(new Date());
        int i = tbItemParamDao.insertSelective(param);
        if (i>0){
            return ResponseResult.ok();
        }else {
            return ResponseResult.error();
        }

    }
//上传图片
    public PictureResult upload(MultipartFile images) throws Exception {
        System.out.println(images.getOriginalFilename());
        String img = images.getOriginalFilename();
        String fileName = UUID.randomUUID().toString()+ img.substring(img.lastIndexOf("."));
        String dateStr= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String newFilePath = filePath+dateStr+"/";
        SFtpUtils.uploadFile(hostname,  port,  username,  password,  timeout,  images.getInputStream(), newFilePath, fileName);
        String url = imgPath +dateStr +"/"+fileName;
        System.out.println(url);
        return PictureResult.ok(url);
    }
    @Transactional
    public ResponseResult insert(TbItem tbItem, String desc, String itemParams) {
        tbItem.setStatus((byte)1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItemDao.insertSelective(tbItem);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(tbItem.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        tbItemDescDao.insertSelective(itemDesc);
        
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(tbItem.getId());
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        tbItemParamItemDao.insert(itemParamItem);



        return ResponseResult.ok();
    }
}
