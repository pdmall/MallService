package com.utils;

import java.util.Comparator;

import com.yang.model.StoreComment;
	/**
	 * 比较器
	 * @author a55660000
	 *
	 */
	public class CompratorStoreLevel implements Comparator<StoreComment>{


		@Override
		public int compare(StoreComment o1, StoreComment o2) {
			// TODO Auto-generated method stub
			return (int) (o1.getAllScore()-o2.getAllScore());
		}

	}
