package com.songzhi.generate;

import java.util.Random;

import com.songzhi.model.TableModel;

/**
 * 字符串生成策略
 */
public class StringGenerator extends DataGeneratorAdapter {

	private Random ran = new Random();
//  private final static int delta = 0x9fa5 - 0x4e00 + 1;

  public char getRandomHan() {
//      return (char)(0x4e00 + ran.nextInt(delta)); 
      return (char) (Math.random ()*26+'A');
  }
	
	@Override
	public String generator(TableModel tableModel) {
		int length = ran.nextInt(getMaxLength()) + 1;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<length;i++) {
			sb.append(getRandomHan());
		}
		return sb.toString();
	}

   
	public static void main(String[] args) throws Exception {
		StringGenerator generator = new StringGenerator();
		generator.setMaxLength(100);
//		System.out.println(generator.generator());
	}
}
