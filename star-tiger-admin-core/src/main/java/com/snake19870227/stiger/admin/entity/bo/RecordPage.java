package com.snake19870227.stiger.admin.entity.bo;

import cn.hutool.core.util.PageUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/17
 */
public class RecordPage<T> extends Page<T> implements IPage<T> {

    private static final long serialVersionUID = -2424719265604032004L;

    private int[] rainbow;

    public RecordPage() {
    }

    public RecordPage(long current, long size) {
        super(current, size);
    }

    public RecordPage(long current, long size, long total) {
        super(current, size, total);
    }

    public RecordPage(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public RecordPage(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    @Override
    public RecordPage<T> setTotal(long total) {
        super.setTotal(total);
        this.rainbow = PageUtil.rainbow(Long.valueOf(getCurrent()).intValue(), Long.valueOf(getPages()).intValue(), 11);
        return this;
    }

    public int[] getRainbow() {
        return rainbow;
    }

    public void setRainbow(int[] rainbow) {
        this.rainbow = rainbow;
    }
}
