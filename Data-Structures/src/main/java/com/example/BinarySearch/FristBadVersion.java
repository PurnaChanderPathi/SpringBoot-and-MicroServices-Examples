package com.example.BinarySearch;

public class FristBadVersion {
	public static void main(String[] args) {
		System.out.println(firstBadVersion(10));
	}
	static int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        int mid = start + (end - start)/2;
        boolean p = isBadVersion(mid);
        while(start!=end ){
            if(isBadVersion(mid) == false){
                start = mid + 1;
            }else if(isBadVersion(mid) == true){
                end = mid;
            }
            mid=start + (end - start)/2;
        }
        return mid;
    }
	static boolean isBadVersion(int m) {
		// TODO Auto-generated method stub
		if(m >= 6) {
    		return true;
    	}else {
    		return false;
    	}
	}
}
