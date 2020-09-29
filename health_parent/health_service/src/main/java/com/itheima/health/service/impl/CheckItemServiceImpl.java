package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-18 20:24
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
      private CheckItemDao checkItemDao;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return  checkItemDao.findAll();
    }

    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 检查项分页
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 判断是否有查询条件
           if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
               //模糊查询
               queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
           }
        // 紧接着的查询语句会被分页
       Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        // 封装到分页结果对象中
      PageResult<CheckItem> pageResult  = new PageResult<CheckItem>(page.getTotal(), page.getResult());
      return  pageResult;
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) throws HealthException {
         //先判断这个检查项是否被检查组使用了
        //调用dao查询检查项的id是否在t_checkgroup_checkitem表中存在记录
      int cnt=checkItemDao.findCountByCheckItemId(id);
      if (cnt>0){
          // 已经被检查组使用了，则不能删除，报错
          //??? health_web能捕获到这个异常吗？
          throw new HealthException("该检查项被检查组使用了，不能删除");
      }
        //没使用就可以调用dao删除
        checkItemDao.deleteById(id);
    }

    /**
     * 通过id查询
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }


    /**
     * 编辑检查项
     * @param checkitem
     */
    @Override
    public void update(CheckItem checkitem) {
        checkItemDao.update(checkitem);
    }


}
