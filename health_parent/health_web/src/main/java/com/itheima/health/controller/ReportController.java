package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-28 22:14
 */
@RestController
@RequestMapping("/report")
public class ReportController {

     @Reference
     private SetmealService setmealService;


    /**
     * 套餐预约占比
     * @return
     */
    @GetMapping("/getSetmealReport")
    public Result  getSetmealReport(){
         //套餐数量
        List<Map<String,Object>>  setmealCount  =setmealService.getSetmealReport();
          //预约名称
        List<String>  setmealNames = new ArrayList<>();
             //通过套餐数量获取名称
             if (null!=setmealCount){
                 for (Map<String, Object> map : setmealCount) {
                     setmealNames.add( (String)map.get("name"));
                 }

             }
             //封装返回结果
           Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("setmealCount",setmealCount);
        resultMap.put("setmealNames",setmealNames);
        return  new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }
}
