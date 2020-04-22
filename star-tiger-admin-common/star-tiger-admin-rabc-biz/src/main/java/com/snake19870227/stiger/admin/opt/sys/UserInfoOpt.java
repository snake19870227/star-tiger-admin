package com.snake19870227.stiger.admin.opt.sys;

import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/16
 */
public interface UserInfoOpt {

    UserInfo loadUserInfo(String userFlow);

    UserInfo loadUserInfo(SysUser user);
}
