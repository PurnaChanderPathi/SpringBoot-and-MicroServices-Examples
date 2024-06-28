package com.example.String;

public class DecryptStringfromAlphabettoIntegerMapping {
	public static void main(String[] args) {
		String s = "10#11#12";
		System.out.println(freqAlphabets(s));
//		int ch=11;
//		System.out.println((char)(Math.abs(97+ch)-1));
	}

	
    public static String freqAlphabets(String s) {
    	String stringBuilder =  "";
    	String result = "";
    	char[] ss = s.toCharArray();
    	for(int i=0; i<ss.length; i++) {
    		if( i<ss.length-2 && ss[i+2]=='#') {
    			char sh = ss[i];
    			char sh1 = ss[i+1];
    			 stringBuilder  = Character.toString(sh).concat(Character.toString(sh1));
    			 int finalResult = Integer.valueOf(stringBuilder);
    			result = result + (char)(Math.abs(97+(finalResult))-1);
    			i=i+2;
    		}else {
    			char sh3 = ss[i];
    			stringBuilder = Character.toString(sh3);
    			int finalResult = Integer.valueOf(stringBuilder);
    			result = result + (char)(Math.abs(97+(finalResult))-1);
    		}
    	}
        return result;
    }
}
