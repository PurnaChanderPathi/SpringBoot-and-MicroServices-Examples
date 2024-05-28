package com.example.SortingKrunal;

public class MejorityElement {
	public static void main(String[] args) {
		int[] nums = {3,2,3};
		System.out.println(majorityElement(nums));
	}

	public static int majorityElement(int[] nums) {
		int result = 0;
		int max=0;

		for(int i=0; i<nums.length; i++) {
			int count=0;
			for(int j=i; j<nums.length; j++) {
 				if(nums[i]==nums[j]) {
					count++;
					}
				}
			if(max<count) {
				max=count;
				result = nums[i];				
			}
			}
		

			return result;
	}

}

// pavan code
/*
 * public int majorityElement(int[] nums) {
      Integer key=0;
       Map<Integer,Integer> map=new HashMap();
      for(int i=0;i<nums.length;i++)
      {
          if(map.containsKey(nums[i]))
          {
              map.put(nums[i],map.get(nums[i])+1);
          }
          else
          {
              map.put(nums[i],1);
          }
      }
       key = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
       return key;
    }
    */
