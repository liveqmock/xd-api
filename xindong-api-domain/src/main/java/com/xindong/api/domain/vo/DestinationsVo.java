package com.xindong.api.domain.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: otto
 * Date: 2016-3-31
 * Time: 14:55:55
 * To change this template use File | Settings | File Templates.
 */
public class DestinationsVo {

     private  Integer id;
     private String name;
     private List<DestinationVo> children ;

     DestinationsVo(){}


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

    public List<DestinationVo> getChildren() {
        return children;
    }

    public void setChildren(List<DestinationVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FirstDestination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
