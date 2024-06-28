package com.example.String;

public class ShuffleString {
public static void main(String[] args) {
	String s = "codeleet"; 
	int[] indices = {4,5,6,7,0,2,1,3};
	System.out.println(restoreString(s, indices));
	//System.out.println(shuffleString(s, indices));
}

public static String restoreString(String s, int[] indices) {
    char[] ch=s.toCharArray();
for(int i=0; i<indices.length-1; i++){
for(int j=i+1; j>0; j--){
    if(indices[j]<indices[j-1]){
        int temp = indices[j-1];
        indices[j-1]=indices[j];
        indices[j]=temp;

        char tempCh=ch[j-1];
        ch[j-1] = ch[j];
        ch[j] = tempCh;
    }
}
}
String sh = "";
for(int k=0; k<ch.length; k++){
sh = sh+ch[k];
}
return sh;
}

// Santhosh Method
public static String shuffleString(String s, int[] indices) {
	char[] ch = new char[indices.length];
	for(int i=0; i<s.length(); i++) {
		ch[indices[i]]=s.charAt(i);
	}
	return new String(ch);
}

}
