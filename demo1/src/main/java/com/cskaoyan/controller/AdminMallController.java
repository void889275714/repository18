package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.service.*;
import com.cskaoyan.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 商场管理的Controller
 */
@RestController
public class AdminMallController {

    @Autowired
    RegionService regionService;





    /*------------------------------属于自己的Controller---------------------------------------*/


    /**
     * 商场管理-->行政区域
     *
     */
    @RequestMapping("admin/region/list")
    public BaseRespVo list(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        List<Region> list = regionService.list();
        baseRespVo.setData(list);
        return baseRespVo;
    }

    /*--------------------------------------关键词--------------------------------------------*/
    @Autowired
    KeyWordService keyWordService;
    /**
     * 商场管理--> 关键词 --> 显示及查找
     */
    @RequestMapping("admin/keyword/list")
    public BaseRespVo keyWordList(ListMallCondition listCondition) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = keyWordService.queryKeyWord(listCondition.getPage(),listCondition.getLimit(),listCondition.getKeyword(),listCondition.getUrl(),listCondition.getSort(),listCondition.getOrder());
        baseRespVo.setData(map);
        return baseRespVo;
    }


    /**
     * 商场管理--> 关键词 --> 添加
     * @param keyword
     * @return
     */
    @RequestMapping("admin/keyword/create")
    public BaseRespVo keyWordCreate(@RequestBody Keyword keyword) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        int i = (int) (1 + Math.random() * 100);
        keyword.setSortOrder(i);

        //这个感觉很奇怪
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        keyword.setAddTime(parse);

        keyword.setDeleted(true);


        Keyword data= keyWordService.createKeyWord(keyword);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 商场管理--> 关键词 --> 编辑
     */
    @RequestMapping("admin/keyword/update")
    public BaseRespVo keyWordUpdate(@RequestBody Keyword keyword) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        keyword.setUpdateTime(parse);

        //增加跟修改用同一个方法吧
        Keyword data= keyWordService.updateKeyWord(keyword);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 商场管理--> 关键词 --> 删除
     * 假删，把deleted的状态修改为1
     */
    @RequestMapping("admin/keyword/delete")
    public BaseRespVo keyWordDelete(@RequestBody Keyword keyword) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        boolean data= keyWordService.deleteKeyWord(keyword);
        if (data) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }
        return baseRespVo;
    }


    /*--------------------------------------品牌制造商--------------------------------------------*/

    @Autowired
    BrandService brandService;
    /**
     * 品牌制造商 --> 显示
     * GET
     * page=1&limit=20&sort=add_time&order=desc
     */
    @RequestMapping("admin/brand/list")
    public BaseRespVo brandShow(ListBrandCondition listCondition) {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Map<String, Object> map = brandService.queryBrand(listCondition.getPage(),listCondition.getLimit(),listCondition.getName(),listCondition.getId(),listCondition.getSort(),listCondition.getOrder());
        baseRespVo.setData(map);
        return baseRespVo;
    }


    @Autowired
    StorageService storageService;
   /* *
     * 品牌制造商 --> 添加 --> Before
     * 在品牌添加里面有一个商品图片的上传
     * @return
     * */
    @RequestMapping("admin/storage/create")
    public BaseRespVo mallFileUpload(MultipartFile file) throws IOException {
        Storage storage = FileUploadUtil.uploadReturnStorage(file);
        //将数据保存到数据库
        int i1 = storageService.insertStorage(storage);
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        Storage data = new Storage();
        if (i1 > 0) {
            data = storageService.queryStorage(storage);
        }
        baseRespVo.setData(data);
        return baseRespVo;
    }


    /**
     * 品牌制造商 --> 新增
     * @param brand
     * @return
     * @throws ParseException
     */
    @RequestMapping("admin/brand/create")
    public BaseRespVo brandCreate(@RequestBody Brand brand) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        brand.setAddTime(parse);
        brand.setUpdateTime(parse);
        brand.setDeleted(false);

        Brand data= brandService.createBrand(brand);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;

    }

    /**
     * 品牌制造商 --> 编辑
     */
    @RequestMapping("admin/brand/update")
    public BaseRespVo brandUpdate(@RequestBody Brand brand) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        brand.setUpdateTime(parse);

        //增加跟修改用同一个方法吧
        Brand data= brandService.updateBrand(brand);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 品牌制造商 --> 删除
     */
    @RequestMapping("admin/brand/delete")
    public BaseRespVo brandDelete(@RequestBody Brand brand){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        boolean data= brandService.deleteBrand(brand);
        if (data) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }
        return baseRespVo;
    }



    /*--------------------------------------通用问题--------------------------------------------*/


    @Autowired
    IssueService issueService;

    /**
     * 通用信息--> 显示
     * @param listIssueCondition
     * @return
     */
    @RequestMapping("admin/issue/list")
    public BaseRespVo issueShow(ListIssueCondition listIssueCondition){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrmsg("成功");
        baseRespVo.setErrno(0);
        Map<String, Object> map = issueService.queryIssue(listIssueCondition.getPage(), listIssueCondition.getLimit(), listIssueCondition.getQuestion(), listIssueCondition.getSort(), listIssueCondition.getOrder());
        baseRespVo.setData(map);
        return baseRespVo;
    }


    /**
     * 通用问题 --> 新增
     * @param issue
     * @return
     * @throws ParseException
     */
     @RequestMapping("admin/issue/create")
     public BaseRespVo issueCreate(@RequestBody Issue issue) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        issue.setAddTime(parse);
        issue.setUpdateTime(parse);
        issue.setDeleted(false);

        Issue data= issueService.createIssue(issue);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;

    }


    /**
     * 通用问题 --> 修改
     * @param issue
     * @return
     * @throws ParseException
     */
    @RequestMapping("admin/issue/update")
    public BaseRespVo issueUpdate(@RequestBody Issue issue) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        issue.setUpdateTime(parse);

        //增加跟修改用同一个方法吧
        Issue data= issueService.updateIssue(issue);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 通用问题 --> 删除
     * @param issue
     * @return
     */
    @RequestMapping("admin/issue/delete")
    public BaseRespVo issueDelete(@RequestBody Issue issue){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        boolean data= issueService.deleteIssue(issue);
        if (data) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }
        return baseRespVo;
    }


    /*--------------------------------------商品类目--------------------------------------------*/


    @Autowired
    CateGoryService cateGoryService;


    /**
     * 商品类目 --> 显示(除类目名之外的)
     */
    @RequestMapping("admin/category/list")
    public BaseRespVo cateGoryShow(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        List<CateGory> cateGories = cateGoryService.cateList();
        baseRespVo.setData(cateGories);
        return baseRespVo;
    }

    /**
     * 商品类目 --> 显示类目名
     * value: Level:L1 的 id
     * label : Level:L1 的 name
     */
    @RequestMapping("admin/category/l1")
    public BaseRespVo cateGoryL1Show(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        List<Map<String,Object>> cateGoriesName = cateGoryService.cateL1List();
        baseRespVo.setData(cateGoriesName);
        return baseRespVo;
    }


    /**
     * 商品类目 --> 新增类目
     * @param cateGory
     * @return
     * @throws ParseException
     */
    @RequestMapping("admin/category/create")
    public BaseRespVo categoryCreate(@RequestBody CateGory cateGory) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        cateGory.setAddTime(parse);
        cateGory.setUpdateTime(parse);
        cateGory.setDeleted(false);

        CateGory data= cateGoryService.createCategory(cateGory);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;

    }


    /**
     * 商品类目 --> 修改
     * 如果一级标题改为二级标题，children的怎么办? 这也是个bug
     * @param cateGory
     * @return
     * @throws ParseException
     */
    @RequestMapping("admin/category/update")
    public BaseRespVo categoryUpdate(@RequestBody CateGory cateGory) throws ParseException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = simpleDateFormat.parse(format);
        cateGory.setUpdateTime(parse);

        //增加跟修改用同一个方法吧
        CateGory data= cateGoryService.updateCateGory(cateGory);
        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }


    /**
     * 商品类目 --> 假删
     * 请求体: Json 数据
     * 响应体:
     * {"errno":0,"errmsg":"成功"}
     */
    @RequestMapping("admin/category/delete")
    public BaseRespVo categoryDelete(@RequestBody CateGory cateGory){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        boolean data= cateGoryService.deleteCateGory(cateGory);
        if (data) {
            baseRespVo.setErrno(0);
            baseRespVo.setErrmsg("成功");
        }
        return baseRespVo;
    }

}
