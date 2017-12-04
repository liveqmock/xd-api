package demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.service.CouponService;

public class TestJunit extends BaseTest{
	
	
	@Override
	public void test() throws Exception {
		System.out.println("============");
		
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-config.xml"); 
		
		
		CouponService service =(CouponService) ac.getBean("couponService");
		TbCouponPromoQuery couponPromoQuery =new TbCouponPromoQuery();
		System.out.println(JsonUtils.writeValue(service.getAllCouponPromo(couponPromoQuery )));
		/*PassengerGroup userCollection =new PassengerGroup();
		userCollection.setUserId(10000218);
		userCollection.setPassengerIdentityNumber("1312312312");
		userCollection.setPassengerIdentityType(1);
		userCollection.setPassengerMobile("18321315");
		userCollection.setPassengerName("121231sad大");
		userCollection.setPassengerId(3);
//		userCollection.setItemId(10026);
		service.delPassengerByUserId(userCollection);*/
	/*	CouponService service =(CouponService) ac.getBean("couponService");
		OrderInfo order =new OrderInfo();
		order.setUserId(10000018);
		order.setAdultNum(1);
		order.setItemId(10033);
		order.setSkuId(1169);
//		order
		Result rs = service.receiveCoupon(10000017,1011);
		System.out.println(rs.getResultMessage()+"==="+rs.getResult());*/
		/*IndexService service =(IndexService) ac.getBean("indexService");
		service.getDestinations();
		System.out.println("=====success======");*/
		/*OrderInfoService orderInfoService =(OrderInfoService) ac.getBean("orderInfoService");
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("MerOrderNo", "100001-2");
		map.put("OrderAmt", "10000");
		map.put("TranDate", "20160324");
		map.put("TranTime", "175320");
		map.put("TranType", "0001");
		map.put("ChannelSeqId", "15422452021");
		orderInfoService.unionPay(map);
		System.out.println("=====success======");*/
		
	}

	@Override
	public void test1() throws Exception {
		// TODO Auto-generated method stub
		
	}
	/*public static void main(String[] args) throws IOException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		String fileName = "D:\\imageUpload\\" + year +"\\" + month+"\\"+day;
	}*/
}
