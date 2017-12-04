package com.xindong.api.domain.been;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 公共常用方法
 * @author lichaoxiong
 *
 */
public class CommUtils {

	/**
	 * 转换BigDecimal的值添加2位小数
	 * 返回2位小数的值
	 */
	public static BigDecimal twoDecimal(BigDecimal value){
		if(value == null){
			value =BigDecimal.valueOf(0.00);
		}
		DecimalFormat df = new DecimalFormat("#.00");
		BigDecimal newValue = new BigDecimal(df.format(value));
		return newValue;
	}
	
}
