package com.cos.blog.test;

public class People {

	private int hugryState = 50; //100 default
	
	private void eat() {
		hugryState += 10;
	}

	public static void main(String[] args) {
		People p = new People();
		
		p.hugryState = 100;
	}
	
}