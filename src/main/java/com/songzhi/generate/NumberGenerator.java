package com.songzhi.generate;

import java.math.BigDecimal;
import java.util.Random;

import com.songzhi.model.TableModel;

/***
 * 数字生成策略
 */
public class NumberGenerator  extends DataGeneratorAdapter  {
	
	
	/** Result should be short. */
	public static final int SHORT = 0;
	/** Result should be integer. */
	public static final int INTEGER = 1;
	/** Result should be long. */
	public static final int LONG = 2;
	/** Result should be float. */
	public static final int FLOAT = 3;
	/** Result should be double. */
	public static final int DOUBLE = 4;
	/** The result should be numeric. */
	public static final int NUMERIC = 5;
	/** Minimal value. */
	private BigDecimal minValue = new BigDecimal("0");
	/** Maximal value. */
	private BigDecimal maxValue = new BigDecimal("127");
	/** Scale. */
	private int scale = 0;
	/** Returned type. */
	private int returnedType = SHORT;
	/** Random number generator. */
	private Random random = null;

	@Override
	public void initialize(Container container) {
		super.initialize(container);
		random = new Random();
		if (minValue.compareTo(maxValue) > 0) {
			throw new RuntimeException("MinValue < maxValue");
		}
	}

	/**
	 * Generates random number.
	 * 
	 * @return number
	 */
	@Override
	public Object generator(TableModel tableModel) {

		BigDecimal retValue = null;
		BigDecimal length = maxValue.subtract(minValue);
		BigDecimal factor = new BigDecimal(random.nextDouble());
		retValue = length.multiply(factor).add(minValue);
		if (returnedType == SHORT) {
			return new Short((short) retValue.toBigInteger().intValue());
		}
		if (returnedType == INTEGER) {
			return new Integer(retValue.toBigInteger().intValue());
		}
		if (returnedType == LONG) {
			return new Long(retValue.toBigInteger().longValue());
		}
		if (returnedType == FLOAT) {
			return new Float(retValue.floatValue());
		}
		if (returnedType == DOUBLE) {
			return new Double(retValue.doubleValue());
		}
		retValue = retValue.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
		return retValue;
	}

	public String getMinValue() {
		return minValue.toString();
	}

	public void setMinValue(String minVal) {
		minValue = new BigDecimal(minVal);
	}

	public String getMaxValue() {
		return maxValue.toString();
	}

	public void setMaxValue(String maxVal) {
		maxValue = new BigDecimal(maxVal);
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int s) {
		scale = s;
	}

	/**
	 * Returns type.
	 */
	public String getReturnedType() {
		if (returnedType == INTEGER) {
			return "integer";
		}
		if (returnedType == LONG) {
			return "long";
		}
		if (returnedType == FLOAT) {
			return "float";
		}
		if (returnedType == DOUBLE) {
			return "double";
		}
		if (returnedType == NUMERIC) {
			return "numeric";
		}
		return "short";
	}

	/**
	 * Sets the returned type.
	 */
	public void setReturnedType(String type) {
		if ("integer".equals(type)) {
			returnedType = INTEGER;
		} else if ("long".equals(type)) {
			returnedType = LONG;
		} else if ("float".equals(type)) {
			returnedType = FLOAT;
		} else if ("double".equals(type)) {
			returnedType = DOUBLE;
		} else if ("numeric".equals(type)) {
			returnedType = NUMERIC;
		} else {
			returnedType = SHORT;
		}
	}
	
}
