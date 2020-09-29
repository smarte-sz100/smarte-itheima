package com.itheima.health.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: chencongming
 * @date: 2020-09-22 00:31
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);
    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 套餐上传图片
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //- 获取原有图片名称，截取到后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //- 生成唯一文件名，拼接后缀名
        String uniqueName = UUID.randomUUID().toString() + substring;
        //- 调用七牛上传文件
        try {
            //- 调用QiNiuUtils上传文件
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), uniqueName);
            //- 响应结果给页面
            //  - 封装结果到map
            Map<String, String> resultMap = new HashMap<String, String>();
            //  - map有2个key
            //    - domain
            resultMap.put("domain", QiNiuUtils.DOMAIN);
            //    - imgName
            resultMap.put("imgName", uniqueName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, resultMap);
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("上传图片失败", e);
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 添加检查套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        //调用服务添加
        setmealService.add(setmeal, checkgroupIds);
        //获取redis连接对象
        Jedis jedis = jedisPool.getResource();
        // redis中set 集合中保存的元素格式为:套餐id | 操作类型|时间戳
        jedis.sadd("setmeal:static:html",setmeal.
                getId()+"|1|"+System.currentTimeMillis());
             //还回连接池
              jedis.close();
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 套餐分页查询
     *
     * @param
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);

        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    /**
     * 通过id查找套餐
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        //调用业务
       Setmeal setmeal= setmealService.findById(id);
        //回显图片
          //把数据跟图片封装到map里
        Map<String, Object> map = new HashMap<>();
        map.put("setmeal", setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    /**
     * 选中检查组id
     * @return
     */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){

     List<Integer> checkgroupIds =setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
    }

    /**
     * 更新套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds ){
           //调用服务修改
            setmealService.update(setmeal,checkgroupIds);
            //获取redis连接
        Jedis jedis = jedisPool.getResource();
            //redis中set集合中保存的元素格式为: 套餐id|操作类型|时间戳
          jedis.sadd("setmeal:static:html",
                  setmeal.getId()+"|1|"+System.currentTimeMillis());
            jedis.close();

        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);

    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public Result deleteById(int id){
        //调用服务方法
        setmealService.deleteById(id);
        //获取redis连接
        Jedis jedis = jedisPool.getResource();
         //redis中set集合中保存的元素格式为: 套餐id|操作类型|时间戳
         jedis.sadd("setmeal:static:html",
                 id+"|0|"+System.currentTimeMillis());
          //关闭连接
          jedis.close();
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

}
