package com.utils;

import java.util.Comparator;

import com.yang.model.TheBag;

public class CompratorDistanceBag implements Comparator<TheBag>{
	/**
	 * 比较器
	 * @author a55660000
	 *
	 */

	@Override
	public int compare(TheBag o1, TheBag o2) {
		
		
		return (int) (o1.getsDistance()-o2.getsDistance());
	}

	}
