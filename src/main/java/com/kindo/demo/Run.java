package com.kindo.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Run {

	public static void main(String[] args) {
		
		List<TypeDeptCount> list = new ArrayList<>();
		for(int i=0;i<10;i++) {
			TypeDeptCount ty = new TypeDeptCount();
			ty.setCykbbm("10"+i);
			ty.setCount(i);
		}
        List<Map<String, Object>> datas = list.stream().collect(Collectors.groupingBy(TypeDeptCount::getType))
                .entrySet().stream().map(item -> item.getValue().stream().reduce(new HashMap<String, Object>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put("count", 0);
                        put("type", item.getKey());
                    }
                }, (data1, val) -> {
                    data1.put(val.getCykbbm(), val.getCount());
                    data1.put("count", (Integer) data1.get("count") + val.getCount());
                    return data1;
                }, (data1, data2) -> {
                    data2.put("count", (Integer) data1.get("count") + (Integer) data2.get("count"));
                    data1.putAll(data2);
                    return data1;
                })).sorted((item1, item2) -> (Integer) item2.get("count") - (Integer) item1.get("count"))
                .collect(Collectors.toList());
        System.out.println(datas);

	}

}
