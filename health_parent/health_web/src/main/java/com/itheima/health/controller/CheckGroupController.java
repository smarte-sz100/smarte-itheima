package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-20 16:50
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return  new Result(true , MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 检查组分页查询
     * @param queryPageBean
     * @return
     */
   @PostMapping("/ findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult<CheckGroup> pageResult= checkGroupService.fingPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     *  通过id查询
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){

        CheckGroup  checkGroup =  checkGroupService.findById(id);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id集合
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
    }

    /**
     * 修改
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds ){

          checkGroupService.update(checkGroup,checkitemIds);

          return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> all= checkGroupService.findAll();

        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,all);
    }
}
