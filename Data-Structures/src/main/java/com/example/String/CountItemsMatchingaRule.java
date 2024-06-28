package com.example.String;

import java.util.List;

public class CountItemsMatchingaRule {
	public static void main(String[] args) {				
		List<List<String>> items = List.of(
	            List.of("phone","blue","pixel"),
	            List.of("computer","silver","phone"),
	            List.of("phone","gold","iphone")
	        );
		String ruleKey = "type"; 
		String ruleValue = "phone";
		System.out.println(countMatches(items, ruleKey, ruleValue));
		}
	
    public static int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        int result = 0;
        if(ruleKey =="type") {
        	for(List<String> list:items) {
        		if(ruleValue.equals(list.get(0))) {
        			result++;
        		}
        	}
        }
        if(ruleKey =="color") {
        	for(List<String> list:items) {
        		if(ruleValue.equals(list.get(1))) {
        			result++;
        		}
        	}
        }
        if(ruleKey =="name") {
        	for(List<String> list:items) {
        		if(ruleValue.equals(list.get(2))) {
        			result++;
        		}
        	}
        }
        return result;
    }
}
