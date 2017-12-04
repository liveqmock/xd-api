package com.xindong.api.domain.vo;

/**
 * Created by IntelliJ IDEA.
 * User: otto
 * Date: 2016-3-31
 * Time: 14:54:39
 * To change this template use File | Settings | File Templates.
 */
public class DestinationVo {

     private  Integer id;
    private String name;

    DestinationVo(){}

    DestinationVo(Integer id,String name){
        this.id = id;
        this.name = name;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinationVo d = (DestinationVo) o;

        if (id != null ? !id.equals(d.id) : d.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "a{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
