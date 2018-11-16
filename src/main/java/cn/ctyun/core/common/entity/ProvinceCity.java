package cn.ctyun.core.common.entity;

public class ProvinceCity {

    // 地区编码
    private String id;
    // 地区名称
    private String name;
    // 上级地区编码
    private String pid;
    // 备注
    private String info;
    // 是否有承诺函（针对CRM客户） 0--无；1--有
    private Integer isPromise;

    private String areaCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getIsPromise() {
        return isPromise;
    }

    public void setIsPromise(Integer isPromise) {
        this.isPromise = isPromise;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

}
