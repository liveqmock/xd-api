package com.xindong.api.domain.enumtype;

/**
 * User: iboy
 * Date: 2014-9-22
 * Time: 18:01:30
 */
public enum BusinessUserType {

    NORMAL_BUSINESSUSER(1,"普通商家"),
    AUTH_BUSINESSUSER(2,"认证商家"),
    VIP_BUSINESSUSER(3,"VIP商家");

    private int key;
    private String value;

    BusinessUserType (int key,String value){
        this.key = key;
        this.value = value;
    }

    public static BusinessUserType getBusinessUserType(int key){
        for(BusinessUserType but : BusinessUserType.values() ){
            if(but.getKey()==key){
                return but;
            }
        }
        return null;
    }

     public static String getValue(int key){
        for(BusinessUserType but : BusinessUserType.values() ){
            if(but.getKey()==key){
                return but.getValue();
            }
        }
        return null;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
