package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-18 20:00
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有
     * @return
     */
   @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list  =   checkItemService.findAll();
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);

        return result;
    }

    /**
     * 添加检查项
     */
    @PostMapping("/add")
    @PostAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
    checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS) ;
    }
    /**
     * 检查项分页
     */
  @PostMapping("/findPage")
  @PostAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
     PageResult<CheckItem> pageResult= checkItemService.findPage(queryPageBean);

      return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     *删除检查项
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){

        checkItemService.deleteById(id);

        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }


    /**
     * 通过id查询
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }
    /**
     * 编辑检查项
     */
@PostMapping("/update")
    public Result update(@RequestBody  CheckItem checkitem){

    checkItemService.update(checkitem);
    return  new Result(true ,MessageConstant.EDIT_CHECKITEM_SUCCESS);
}
}
