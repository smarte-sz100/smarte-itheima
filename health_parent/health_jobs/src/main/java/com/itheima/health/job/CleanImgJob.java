package com.itheima.health.job;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-23 18:11
 */
@Component("cleanImgJob")
public class CleanImgJob {
    //订阅服务
   @Reference
    private SetmealService setmealService;

    /**
     * 清除垃圾图片
     */
    public void  cleanImg(){
         //查七牛上的所有图片
        List<String> imgInQiniu = QiNiuUtils.listFile();
         //查询数据库里的所有图片
        List<String> imgInDb =setmealService.findSetmealImg();
         // 七牛所有图片减去数据库的图片
         //剩下的就是要删除的
        imgInQiniu.removeAll(imgInDb);
         //把所有的图片名转成数组
        String[] strings = imgInQiniu.toArray(new String[]{});
        //然后再清除图片
        QiNiuUtils.removeFiles( strings);

    }

}
