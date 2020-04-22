package com.snake19870227.stiger.admin.dao.mapper;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.po.SysResource;

/**
 * @author Bu HuaYang
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    default RecordPage<SysResource> get(RecordPage<SysResource> page, String resName) {
        QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(resName)) {
            queryWrapper.like("res_name", StrUtil.trim(resName));
        }
        queryWrapper.orderByAsc("res_path");
        return this.selectPage(page, queryWrapper);
    }
}
