package com.briup.client;

public class Test {
	public static void main(String[] args) {
		GatherImpl g = new GatherImpl();
		try {
			g.gather();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
