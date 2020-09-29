package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-20 22:46
 */
@Service(interfaceClass =CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加检查组
        checkGroupDao.add(checkGroup);
        // 获取检查组id
        Integer checkGroupId = checkGroup.getId();
        // 循环遍历选中检查项id，
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }

    /**
     * 检查组分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> fingPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 有条件，则要模糊查询
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 拼接 %
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 条件查询
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 通过id查询
     * @param id
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);

    }

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 修改
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先更新检查组
        checkGroupDao.update(checkGroup);
        //删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        // 建立新关系
        if (null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        //检查是否被套餐使用了
        //通过检查组id统计个数
       int  cnt = checkGroupDao.findSetmealCountByCheckGroupId(id);
         if (cnt>0){
             throw new HealthException(MessageConstant.CHECKGROUP_IN_USE);
         }
         //删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        // 删除检查组
        checkGroupDao.deleteById(id);

    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
