package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-24 13:28
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService  setmealService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result findAll(){
        //查询所有
        List<Setmeal>  list  =setmealService.findAll();
        // 拼接图片的完整路径
        list.forEach(setmeal->
            setmeal.setImg(QiNiuUtils.DOMAIN  + setmeal.getImg())
        );
        return  new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list );
    }

    /**
     * 通过id查询套餐详情
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(Integer id){

        //通过id查询
        Setmeal setmeal = setmealService.findDetailById(id);
         //图片拼接
        setmeal.setImg(QiNiuUtils.DOMAIN+ setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
    /**
     * 通过id查询套餐详情
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id){

        //通过id查询
        Setmeal setmeal = setmealService.findById(id);
        //图片拼接
        setmeal.setImg(QiNiuUtils.DOMAIN+ setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
