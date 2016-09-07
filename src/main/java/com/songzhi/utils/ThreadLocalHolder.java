package com.songzhi.utils;

public class ThreadLocalHolder {
	
	private static final ThreadLocal<Double> holder = new ThreadLocal<>();
	
	public static void set(Double num) {
		holder.set(num);
	}

	public static Double get() {
		return holder.get();
	}

	public static void remove() {
		holder.remove();
	}
}
