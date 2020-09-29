package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-22 02:03
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public Integer add(Setmeal setmeal, Integer[] checkgroupIds) {
           //先添加套餐
           setmealDao.add(setmeal);
           //获取新增套餐id
        Integer setmealId = setmeal.getId();
        if (null!=checkgroupIds){

          //遍历选中的检查组id集合,添加套餐与检查组的关系
        for (Integer checkgroupId : checkgroupIds) {
            setmealDao.addSetmealCheckGroup(setmealId,checkgroupId );
            }
        }
        return setmealId;
    }

    /**
     * 套餐分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
            //获取当前页码和每页大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
           //模糊查询
           //先条件判断
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            ////模糊查询
        queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //条件查询 ,这个查询语句会被分页
       Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        return  new PageResult<Setmeal>(page.getTotal(),page.getResult());
    }

    /**
     * 通过id查询套餐
     * @param id
     */
    @Override
    public Setmeal findById(int id) {

        return setmealDao.findById(id);
    }

    /**
     * 选中检查组id
     * @return
     */
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {

           return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    /**
     * 更新套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds)  {
         //先更新套餐
        setmealDao.update(setmeal);
        //删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //建立新的组关系
         if (null!=checkgroupIds){
             for (Integer checkgroupId : checkgroupIds) {
                 setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
             }
         }
    }

    /**
     * 删除套餐
     * @param id
     * @throws HealthException
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
           //先要查询是否被订单使用
       int count= setmealDao.findOrderBySetmeal(id);
           //如果使用 则报错
          if (count>0){
              throw new HealthException("已经有订单使用了这个套餐，不能删除！");
          }else{
              //先删除套餐组与检查组关系
              setmealDao.deleteSetmealCheckGroup(id);
              //没有被使用  则执行删除
              setmealDao.deleteById(id);
          }

    }

    /**
     *  //查询数据库里的所有图片
     * @return
     */
    @Override
    public List<String> findSetmealImg() {
        return setmealDao.findSetmealImg();
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * //通过id查询套餐显示页面
     * @param id
     * @return
     */
    @Override
    public Setmeal findDetailById(Integer id) {
        return setmealDao.findDetailById(id);
    }

    /**
     * 套餐预约占比- 套餐数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setmealDao. getSetmealReport();
    }
}
