package com.xindong.api.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class QueryParameter  implements Comparable<QueryParameter> {
	private String name;
	private String value;

	public QueryParameter(){}
	
	public QueryParameter(String name,String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

//	@Override
//	public int compare(Object o1, Object o2) {
//		QueryParameter x = (QueryParameter)o1;
//		QueryParameter y = (QueryParameter)o2;
//		if (x.getName().equals(y.getName())) {
//			return x.getValue().compareTo(y.getValue());
//		} else {
//			return x.getName().compareTo(y.getName());
//		}
//	}
	
	public static void main(String[] args) {
		QueryParameter q = new QueryParameter();
		q.name = "param1";
		q.value = "value1";
		
		QueryParameter q2 = new QueryParameter();
		q2.name = "param2";
		q2.value = "value2";
		QueryParameter q3 = new QueryParameter();
		q3.name = "param3";
		q3.value = "value3";
		QueryParameter q4 = new QueryParameter();
		q4.name = "param4";
		q4.value = "value4";
		
		QueryParameter q5 = new QueryParameter();
		q5.name = "param4";
		q5.value = "value5";
		
		List<QueryParameter> list = new ArrayList<QueryParameter>();
		list.add(q3);
		list.add(q);
		list.add(q4);
		list.add(q2);
		list.add(q5);
		
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getValue());
		}
		
		Collections.sort(list);
		
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getValue());
		}
		
	}

	@Override
	public int compareTo(QueryParameter o) {
		if (this.getName().equals(o.getName())) {
			return this.getValue().compareTo(o.getValue());
		} else {
			return this.getName().compareTo(o.getName());
		}
	}
}

