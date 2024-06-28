package com.example.String;

public class GoalParserInterpretation {
	public static void main(String[] args) {
		String command = "G()(al)";
		System.out.println(interpret(command));
	}
    public static String interpret(String command) {
        String Result = "";
        for(var i=0; i<command.length(); i++){
            char common = command.charAt(i);
            if(command.charAt(i)=='(' && command.charAt(i+1)==')') {
            	Result = Result + 'o';
            }
            if(common !='(' && common!=')') {
            	Result = Result + command.charAt(i);
            }
        }
        return Result;
    }
}
