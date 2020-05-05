/**
 * @author zhang bo 
 * @ID 40108654
 * COMP352 assignment 3
 * Programming Questions 
 */




package assignment3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.MalformedInputException;
import java.util.Scanner;
import java.util.Stack;

import assignment3.evalTree.Node;

public class evalTree {
    
    static Scanner sc=new Scanner(System.in);
    static Scanner read=new Scanner(System.in);   
     
    public static class Node{  
        String data;  
        Node left;  
        Node right; 
     
        public String toString() {
        	return  this.data+left.data+right.data;
        }
        public Node(String data){  

            this.data = data;  
            this.left = null;  
            this.right = null;  
          } 
    }
    
    public static int priority(String op) {
        switch(op) {
        case "(": return 1;
        case "*": return 2;
        case "/": return 2;
        case "+": return 3;
        case "-": return 3;
        default: return 0;
        }
    }
    
    public static boolean isNumber(String item) {
        if (item == null) 
            return false;
        try {
            double d = Double.parseDouble(item);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean isVariable(String item) {
        if (item == null) 
            return false;
        if (isNumber(item))
            return false;
        if (item.contains("-")||item.contains("+")||item.contains("*")||item.contains("/")||item.contains("(")||item.contains(")"))
            return false;
        else return true;
    }
    
    
    public static Node creatTree(String s) {
    	if(s.length()==0) return null;
        Stack<Integer> brackets= new Stack<Integer>(); 
        Node root = new Node("new");
        int left=0;
        
        /*  check if need to remove brackets*/
        
       if(s.charAt(0)=='('&&(s.charAt(s.length()-1)==')')) 
       //{s=s.substring(1, s.length()-1); creatTree(s);}
        {boolean removeBrakets=true;
        	for(int i =0;i<s.length();i++) {
                String item=s.substring(i,i+1);
                if(item.equals("(")) {
                    brackets.push(i);    

                }
                else if(item.equals(")")) {
                    brackets.pop();               
               
                }
                else if((item.equals("*")||item.equals("/")||item.equals("-")||item.equals("+"))&&brackets.isEmpty()) removeBrakets=false;
        	}
        	if(removeBrakets) {s=s.substring(1, s.length()-1);}
        }
        if(isNumber(s)) {
            root.data=s;
            return root;
        }
        
        if(isVariable(s)) {
            System.out.println("pls input the value of variable "+s+" : ");           
            root.data=read.next();
            System.out.println("input the value of variable :"+root.data);
            return root;
        }
        
        
        for(int i =0;i<s.length();i++) {
            String item=s.substring(i,i+1);
            if(item.equals("(")) {
                   brackets.push(i);    

            }
            else if(item.equals(")")) {
                   brackets.pop();               
              
            }
            
            else if((item.equals("*")||item.equals("/")||item.equals("-")||item.equals("+"))&&brackets.isEmpty()) {

                 if (root.data.equals("new")){
                     root=new Node(item);
                     left=i;
                 }
            	            	
                  else {
                          if (priority(root.data)<=priority(item)) {
                          root=new Node(item);
                          left=i;}
                 }
    
            }
            else continue;
        }
        
        root.left=creatTree(s.substring(0,left));
        root.right=creatTree(s.substring(left+1));
        return root;
    }
    
    
    public static void printTree(Node Node) {
    	if (Node == null) { return; } 

    		System.out.println(Node.data); 

    	printTree(Node.left); printTree(Node.right);

    }
    
    public static double evalExpr(Node root ) {
    	
    	if(isNumber(root.data)) {
              return Double.parseDouble(root.data);
    	}
    	
    	else if (root.data.equals("+")) {
            return (evalExpr(root.left)+evalExpr(root.right));
        }
        else if (root.data.equals("-")) {
            return (evalExpr(root.left)-evalExpr(root.right));
        }
        else if (root.data.equals("*")) {
            return (evalExpr(root.left)*evalExpr(root.right));
        }           
        else  {
            return (evalExpr(root.left)/evalExpr(root.right));
        } 
     }     
      
        
    public static void main(String[] args) {
        
        try {
            sc = new Scanner(new FileInputStream("expression.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNext()) {
            String ariExpression=sc.nextLine();

            Node root=creatTree(ariExpression);

             System.out.println(root);

				evalExpr(root);
	            System.out.println("Result is " + evalExpr(root));
			


            }        
        sc.close();
     }
    
}

