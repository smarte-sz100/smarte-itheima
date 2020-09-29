package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author: chencongming
 * @date: 2020-09-22 23:25
 */

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log= LoggerFactory.getLogger(OrderSettingController .class);
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 上传Excel文件
     * @return
     */
   @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){

       try {
           //先要读取文件内容
           List<String[]> excelStrings = POIUtils.readExcel(excelFile);
           //转成List<Ordersetting>
           ArrayList<OrderSetting> orderSettingsList = new ArrayList<OrderSetting>(excelStrings.size());
           //日期格式转化
           SimpleDateFormat format = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            //遍历文件内容
           Date orderDate=null;
           OrderSetting order=null;
           for (String[] dataArr : excelStrings) {
                        //日期格式解析
                orderDate = format.parse(dataArr[0]);
               int number = Integer.valueOf(dataArr[1]);
              order= new OrderSetting(orderDate, number);
               orderSettingsList.add(order);
           }
           //文件导入
           orderSettingService.add( orderSettingsList);
           return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
       } catch (Exception e) {
          log.error("批量导入失败",e);
       }

       return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
   }

    /**
     * 通过月份来获取预约设置信息
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
   public  Result getOrderSettingByMonth( String month ){
          //调用服务查询日期
        List<Map<String,Integer>> data = orderSettingService.getOrderSettingByMonth(month);

          return  new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data);
   }

    /**
     * 设置预约信息
     * @param orderSetting
     * @return
     */
   @PostMapping("/editNumberByDate")
   public Result editNumberByDate(@RequestBody OrderSetting orderSetting){

       orderSettingService.editNumberByDate(orderSetting);

       return  new Result(true,MessageConstant.ORDERSETTING_SUCCESS);

   }

}
