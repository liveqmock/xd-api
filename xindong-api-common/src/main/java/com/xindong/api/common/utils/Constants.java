package com.xindong.api.common.utils;

import java.math.BigDecimal;

public  class Constants {
	//交易支付访问协议路径待定
	public final static String trade_url ="";
	public static final String MESSAGE_SIGN="【心动旅行】";//短信签名
//	public static final String SHAREWEBURL_SUBJECT="http://h5.51wot.com/subject/details";//专题分享地址
//	public static final String SHAREWEBURL_ITEM="http://h5.51wot.com/subject/product";//商品 预售分享地址
//	public static final String SORL_URL="http://s.51wot.com/s/q";//搜索地址
	public static final Integer SORL_RECOMMEND_COUNT=6;//支付成功后最多显示的推荐商品
	
	/**
	 * 是否有效
	 * 1有效
	 */
	public static final Integer YES =1;
	/**
	 * 是否有效
	 * 0无效
	 */
	public static final Integer NO =0;
	/**
	 * 订单多次支付 与支付单号的分割符号
	 */
	public static final String ORDER_PAYMENT_SPIT ="_";
	/**
	 * 1元钱=100积分
	 */
	public static final BigDecimal MONEY_INTEGRAL =new BigDecimal(100);
	/**
	 * 使用礼品卡支付  1元钱=1000积分
	 */
	public static final String MONEY_GIFT_INTEGRAL ="gift_money_integral";
	/**
	 * 使用礼品卡支付  5元钱=2000积分
	 */
	public static final Long MONEY_GIFT_FIVE_INTEGRAL =2000L;
	/**
	 * token加密密钥
	 */
	public static final String TOKEN_DES ="xin8dong";

	/**
	 * 系统参数 值
	 * @author lichaoxiong
	 *
	 */
	public static class SystemConstantTypeValue{
		public static final String redis_address ="xd_redis_address";
		public static final String redis_password ="xd_redis_password";
		public static final String upload_img_url ="xd_upload_img_url";
		public static final String domain_url ="xd_domain_url";
		public static final String union_notify_url ="xd_union_notify_url";
		public static final String alipay_notify_url ="xd_alipay_notify_url";
		public static final String h5_pay_success ="xd_h5_pay_success";
		public static final String shareweburl_subject ="xd_shareweburl_subject";
		public static final String shareweburl_item ="xd_shareweburl_item";
		public static final String sorl_url ="xd_sorl_url";
		public static final String share_gift_url ="xd_share_gift_url";
		public static final String service_phone ="xd_service_phone";
		public static final String cancle_order_time ="xd_cancle_order_time";
		public static final String cancle_special_order_time ="xd_cancle_special_order_time";
		public static final String consult_order_money ="xd_consult_order_money";
		public static final String chinay_merId ="xd_chinay_merId";
		public static final String mail_username ="xd_mail_username";
		public static final String mail_password ="xd_mail_password";
		public static final String xd_register_invitation_money ="xd_register_invitation_money";//邀请用户注册获得的钱
		public static final String xd_register_money ="xd_register_money";//用户注册获得的钱
		public static final String xd_invitation_num ="xd_invitation_num";//一个用户最多邀请的人数
		public static final String xd_wx_notify_url ="xd_wx_notify_url";//微信通知地址
		public static final String xd_service_phone ="xd_service_phone";//微信通知地址
	}
	/**
	 * 用户级别
	 * 用户级别(0:普通用户,1:一级用户,1:二级用户,1:三级用户)
	 */
	public static class UserLevel{
		public static final Integer COMMON =0;
		public static final Integer FIRST =1;
		public static final Integer SECOND =2;
		public static final Integer THREE =3;
	}
	/**
	       用户类型(1普通用户 2 erp用户)
	 */
	public static class UserType{
		public static final Integer COMMON =1;
		public static final Integer ERP =2;
	}
	/**
	     用户旅行偏好(#1#人文艺术、#2#亲子女性、#3#户外运动、
    //#4#商旅交流、#5#教育公益、#6#历史军事、#7#美食养生、#8#行修国学、#9#自驾游艇)
	 */
	public static class UserFlag{
		public static final Integer rwys =1;
		public static final Integer qznx =2;
		public static final Integer hwyd =3;
		public static final Integer sljl =4;
		public static final Integer jygy =5;
		public static final Integer lsjs =6;
		public static final Integer msys =7;
		public static final Integer xxgx =8;
		public static final Integer zjyt =9;
	}
	
	/**
	 * 来源
	 * 第三方来源来源(0-H5  1-android 2-IOS 3-PC)
	 */
	public static class FromWhere{
		public static final Integer H5 =0;
		public static final Integer ADNROID =1;
		public static final Integer IOS =2;
		public static final Integer PC =3;
	}
	
	/**
	 * 商品状态（0-新创建 1-上架、2-下架'3预售）
	 * @author lichaoxiong
	 */
	public static class ItemStatus{
		public static final Integer CREATE =0;
		public static final Integer ONSALE =1;
		public static final Integer OFFSALE =2;
		public static final Integer PRESALE =3;
	}
	/**
	 * 商品类型（1-产品特色;2-每日行程;3-出行说明;4-费用说明;5-预定须知）
	 * @author lichaoxiong
	 */
	public static class ItemDescType{
		public static final Integer PRODUCT_FEATURE =1;
		public static final Integer DAILY_TRAVEL =2;
		public static final Integer TRIP_EXPLAIN =3;
		public static final Integer COST_EXPLAIN =4;
		public static final Integer BOOKING_NOTICE =5;
	}
	
	/**
	 * 标签类型（1 旅游商品标签 2供应商资质标签）
	 * @author lichaoxiong
	 */
	public static class FlagType{
		public static final Integer ITEM =1;
		public static final Integer SUPPLIER =2;
	}
	/**
	 * 旅客认证类型(1身份证2护照3军官证4回乡证5台胞证6港澳通行证7台湾通行证8士兵证)
	 * @author lichaoxiong
	 */
	public static class OrderPassengerType{
		public static final Integer ID =1;
		public static final Integer HZ =2;
		public static final Integer JGZ =3;
		public static final Integer HXZ =4;
		public static final Integer TBZ =5;
		public static final Integer GATXZ =6;
		public static final Integer TWTXZ =7;
		public static final Integer SBZ =8;
	}
	/**
	 * 订单支付类型
	 * @author lichaoxiong
	 * 支付方式支付方式（0alipay 1银联 2 微信 3线下）
	 */
	public static final String SYSTEM_PAY_TYPE ="XD_02";
	public static class PayType{
		public static final Integer ALIPAY =0;
		public static final Integer UNION_PAY =1;
		public static final Integer  WEI_XIN=2;
		public static final Integer OFFLINE =3;
	}
	/**
	 * 订单状态
	 * @author lichaoxiong
	 （0-待库存确认  1-待付款 2-已支付 3- 4-待出行 5-已取消 6-已删除 7已完成  8已退款 9取消申请）
	 */
	public static final String SYSTEM_ORDER_TYPE ="XD_01";
	public static class OrderStatus{
		public static final Integer DQR =0;
		public static final Integer DFK =1;
		public static final Integer YZF =2;
//		public static final Integer  =3;
		public static final Integer DCX =4;
		public static final Integer YQX =5;
		public static final Integer YSC =6;
		public static final Integer YWC=7;
//		public static final Integer  =8;
		public static final Integer QXSQ=9;
	}
	/**
	 * 订单类型
	 * @author lichaoxiong
	 * 订单类型（1旅行订单;2咨询订单  ）
	 */
	public static class OrderType{
		public static final Integer TRAVEL =1;
		public static final Integer CONSULT =2;
	}
	
	/**
	 * 发票状态
	 * @author lichaoxiong
	 * 发票状态(1:未开;2已开;)
	 */
	public static class InvoiceStatus{
		public static final Integer WK =1;
		public static final Integer YK =2;
	}
	
	
	/**
	 * 订单支付来源
	 * @author lichaoxiong
	 * 0 普通; 1 企业  2 h5 3 alipay
	 */
	public static class PayFromType{
		public static final Integer NORMAL =0;
		public static final Integer COMPANY =1;
		public static final Integer H5 =2;
		public static final Integer ALIPAY =3;
	}
	
	/**
	 * 订单操作日志类型
	 * 操作类型 0订单1商品
	 * @author lichaoxiong
	 */
	public static final String SYSTEM_LOG_TYPE ="XD_05";
	public static class OperaLogType{
		public static final Integer ORDER =0;
		public static final Integer ITEM =1;
	}
	
	/**
	 * 活动状态(0待上线;1已上线;2已下线)
	 * @author lichaoxiong
	 */
	public static class ActivityStatus{
		public static final Integer DSX =0;
		public static final Integer YSX =1;
		public static final Integer YXX =2;
	}
	
	/**
	 * 活动回复审核状态(0未审核;1已审核;2已删除)
	 * @author lichaoxiong
	 */
	public static class ActivityReplyStatus{
		public static final Integer DSH =0;
		public static final Integer YSH =1;
		public static final Integer YSC =2;
	}
	/**
	 *筛选查询类型0-心动指数1-时间-2目的地-3主题
	 * @author lichaoxiong
	 */
	public static class SearchType{
		public static final Integer XDZS =0;
		public static final Integer SJ =1;
		public static final Integer MDD =2;
		public static final Integer ZT =3;
	}
	/**
	 *旅行人群（1家庭2商务3朋友）
	 * @author lichaoxiong
	 */
	public static final String SYSTEM_TRAVEL_TYPE ="XD_03";
	public static class TravelType{
		public static final Integer FAMILY =1;
		public static final Integer BUSINESS =2;
		public static final Integer FRIEND =3;
	}
	/**
	 * 发票状态（0不需要1未开2已开）
	 * @author wzy
	 */
	public static final String SYSTEM_INVOICE_TYPE ="XD_04";
	public static class OrderInvoiceStatus{
		public static final Integer NOTNEED =0;
		public static final Integer UNINVOICED =1;
		public static final Integer INVOICED =2;
	}
	
	/**
	 * 公司线下转账信息（1公司名称2账户3开户行4联行号）
	 * @author wzy
	 */
	public static final String SYSTEM_COMPANY_TYPE ="XD_13";
	public static class SystemCompanyStatus{
		public static final Integer company =1;
		public static final Integer accountNumber =2;
		public static final Integer bank =3;
		public static final Integer contactNumber =4;
	}
	
	/**
	 *短信模板（1下单验证码2订单生成3注册4密码重置5付款成功6出行名额已确认7取消订单）
	 * 新修改  8注册9密码重置10付款成功11出行名额已确认12取消订单
	 * 新增    17动态密码登录
	 */
	public static class SmsType{
//		public static final Integer xdd =1;
//		public static final Integer scdd =2;
		public static final Integer zc =8;
		public static final Integer mmzh =9;
		public static final Integer fkcg =10;
		public static final Integer cxqr =11;
		public static final Integer qxdd =12;
		public static final Integer dtmm =17;
	}
	/**
	 * 余额 锁定状态
	 * 0申请下单锁定;1下单锁定中;2下单锁定完成
	 * @author lichaoxiong
	 */
	public static class BalanceLockStatus{
		public static final Integer LOCK_ORDER =0;
		public static final Integer LOCK_ING =1;
		public static final Integer LOCK_DONE =2;
	}
	/**
	 * 余额 处理状态
	 * 0:未处理;1扣减余额中;2余额扣减完成;3已取消'
	 * @author lichaoxiong
	 */
	public static class BalanceWorkStatus{
		public static final Integer UNTREATED  =0;
		public static final Integer DEDUCTIONS  =1;
		public static final Integer COMPLETE =2;
		public static final Integer CANCLE =3;
	}
	/**
	   余额发放类型(1:邀请会员送余额2下单使用余额3注册奖励4增加余额5订单取消)
	 * @author lichaoxiong
	 */
	public static class BalanceGrantType{
		public static final Integer yqhd  =1;
		public static final Integer xdsy  =2;
		public static final Integer zcjl =3;
		public static final Integer zdzj =4;
		public static final Integer ddqx =5;
	}
	/**
	 * 活动类型（1看板新出发2活动专区）
	 * @author lichaoxiong
	 */
	public static final String SYSTEM_ACTIVITY_TYPE ="XD_18";
	public static class ActivityType{
		public static final Integer KB =1;
		public static final Integer HD =2;
	}
	/**
	 * 广告类型1-首页
	 * @author lichaoxiong
	 */
	public static class AdType{
		public static final Integer first =1;
	}
	/**
	 * 广告状态（1待上线2已上线3已下线）
	 * @author lichaoxiong
	 */
	public static class AdState{
		public static final Integer dsx =1;
		public static final Integer ysx =2;
		public static final Integer yxx =3;
	}
	/**
	 * 上线状态（1待上线2已上线3已下线）
	 * 通用
	 * @author lichaoxiong
	 */
	public static class LineState{
		public static final Integer dsx =1;
		public static final Integer ysx =2;
		public static final Integer yxx =3;
	}
	/**
	 * 优惠券类型
	 * 0 满减券 1直减券 2 免运费券
	 * @author lichaoxiong
	 */
	public static class CouponType{
		public static final Integer FULL =0;
		public static final Integer LAPSE =1;	
		public static final Integer FREE =2;
	}
	/**
	 * 优惠券状态
	 * 0未使用1已使用2已过期3已锁定
	 * @author lichaoxiong
	 */
	public static class CouponStatus{
		public static final Integer UNUSED =0;
		public static final Integer USED =1;	
		public static final Integer EXPIRED =2;
		public static final Integer LOCKED =3;
	}
	/**
	 * 优惠券活动状态
	 * 0未上线1已上线2已下线3已结束
	 * @author lichaoxiong
	 */
	public static class CouponPromoStatus{
		public static final Integer DSX =0;
		public static final Integer YSX =1;	
		public static final Integer YXX =2;
		public static final Integer YJS =3;
	}
	/**
	 * 优惠券类型
	 * 1全品类2具体产品3具体SKU
	 * @author lichaoxiong
	 */
	public static class CouponRuleType{
		public static final Integer ALL =1;
		public static final Integer ITEM =2;	
		public static final Integer SKU =3;
	}
	/**
	 * 优惠券_业务_类型(0 订单相关 1直接发放到账号 2用户行为相关 3通过链接领取 )
	 * @author lichaoxiong
	 */
	public static class CouponBusinessType{
		public static final Integer order =0;
		public static final Integer account =1;
		public static final Integer user =2;	
		public static final Integer link =3;
	}
	/**
	 * 专题评论审核状态
	 * @author lichaoxiong
	 * 0待审核 ，1：审核通过， 2：审核不通过 
	 */
	public static class SubjectCommentStatus{
		public static final Integer NOT_AUDIT =0;
		public static final Integer AUDIT_PASS =1;
		public static final Integer AUDIT_NOPASS =2;
		public static final Integer DELETE =3;
	}
	/**
	 * 首页系列类型
	 * 类型1-首页系列2-首页发现
	 * @author lichaoxiong
	 */
	public static class IndexSeriesType{
		public static final Integer series =1;
		public static final Integer find =2;	
	}
	
	/**
	 * 图片类型（1、商品分析图;2、商品图书）
	 */
	public static class ItemImgType{
		public static final Integer IMG_ANAALYSIS =1;
		public static final Integer IMG_BOOK =2;
	}
	
	/**
	 *促销活动状态
	 *促销状态（0-未上线1已上线2已下线）
	 */
	public static class promotionStatus{
		public static final Integer init =0;
		public static final Integer online =1;
		public static final Integer offline =2;
	}
}
