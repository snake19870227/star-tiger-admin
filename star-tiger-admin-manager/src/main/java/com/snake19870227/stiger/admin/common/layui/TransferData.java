package com.snake19870227.stiger.admin.common.layui;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/08/04
 */
public class TransferData {

    private String value;
    private String title;
    private boolean disabled;
    private boolean checked;

    public TransferData() {
        this.value = "";
        this.title = "";
        this.disabled = false;
        this.checked = false;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
