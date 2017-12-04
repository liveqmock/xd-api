package com.xindong.api.web.alipay.config;


public class AlipayWapConfig {

	// 商户APPID
	public static String APPID = "2017110709778400";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCop4Fe1Rn+CRJSRjmMMMjLB8romT1R8C0omE3ZsCoOIpt5IWmPIsAIg4huUQKlftk3kUHBubekoteZfZgj3nEy/ITVzEUHspW/l2OjqW5hYZXEbCCJ3qY+2R5WrOXQBjkUnyJT/+vfeRYH5ybQWp/bAS49wRvhJH9+qzfsQ2fwZXWWNTMMoq8helaqT5J9JuzIdRuzx6zU68u4wOLq86zEEoRcax+K+hVlRawKNPy6ADz0/3a9nJBsPRit+LIcsiEK+YBWKtCmZCDwrYtb68QednqAzQhhkWCbFn/VQxcLTS/uIr3XPDp+SyFdKOZYDyUtXkZm1MasQxCtvpf4K80/AgMBAAECggEAfIUn9F98UF5cQ5iLtqDyL0zymCdD6fJdNSK2H+p8MnGEvrtXl5G9nMuYeHKhdJUiRW+e0CyRlSGR80aShLEgfNeKL9wgf11wSMO6kkRvNcD59csrfv9/WNmvF6mSB7QAguQ3UTOb8CQTiwQ0WRMAAY9ul7OzJNhwtZIuuObPn+HlHxulAb4fwNVrlJnMzNNjxQVLlpRVok/+DsBDzmylli2MY3NRL8TxHhdVV7Sd8fzze6AqSv1FKR/NwoAGpfV/8UEJBRAxeaeAotENfP37jW+imMJzr7COnYV72TcJkXiZl7l87578uEB2E/uXcR684LNJ5B6SzkpR8r1Jr2SGcQKBgQDiuHoikVzxygFGdZ4Z3QFpEIjUCTdQfvmJKmCe3cSlDuh+Ul9gU6uBY/MEQ6zC21FRy9l/wZ22WjiCjGenQvBAu8gjFI+wZdIZwruGkXp6lK4NKS8lyYCmKJXAq16/ZIe8nadmRYwLwJ2mXwiTvc+F+ZfTY7qu15eOLs5BlZkotwKBgQC+b1Ig5CNj0YBfcbbCfvkV5ym/xUWLSX0fV7+CzPJ7yTshceiid06+JS5CLa3sicID9ICsgxBDPd4DQqPFReMtHuZPLWDqRFfBRwDxLZJK/FM4+nP0Q51Am5xniSa1tNf2Uga4sdx5bZuLFaZ981kN3sz9ntNEZm7IN1FS6oKnuQKBgCbiTNAw7ifp5V4YPg1hgiRq2YzEJ+B+Zn3H7tpQXrGQZP+FUSZMM1X1tnA8yxyAUOoedCcdJddokdiSDBT7vgjcK+8YT6yJSgdxBoiF2uO7LD7FmXh5nr7gpw5sVcutijonL9ayhtpdJqyqD+aG31iy/f8bU6E5CJ0yGd3VsnbxAoGBAKpHD8CpPZu7cHXnT+uJbV/QWtxGoHBzPZYQ+AUVDJzyyhSVrdXXqGkNBdfW0HV4mCrBLW0TpC772tA/fgCfBQsLXhPeF5JhmbYQrx74CJ9UD3YbPCNlce6bNba+RAaltZVisu/i2akaAMLUkRJ9zVtBpToCEhZPIS+VhvoFBt7BAoGAZt0qk9S1PWdvxPxG0/SnLNplpR6SwS+iAuxD2XQvoRvhNeSMAMflE47pKVei9smVKrwsFCF6YKAYi56duT3eLOWF3xYWFqy+BLSeh8H7BLiQo40SBHU/5LoSEn/DkoM+5YzNNs/6UbBe7XRRHxnS9UxZBHLjzYUDvfX52aNA8Rw=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://api.xindong8.com/orderPay/payAlipayWap";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://www.xindong8.com/user/my/";
	// 请求网关地址                    
	public static String URL = "https://openapi.alipay.com/gateway.do";
	//沙箱环境地址  
	//public static String URL = "https://openapi.alipaydev.com/gateway.do";
	
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA16IMmk78qMXfTBs0WX6BEfvbvA5tSkzzJfbGUaQKocE2SnOUqm8x/fvyZzlSOPZGgeg6oSCpkeqX3DL3E7kpX/Ic8QphUk3ohH+tKzZY6TlfLTlDtCn6c6R2ESYxwlR0hI+7lrWa6Fcdg+45Ee+DrpkZUBEYwyP41F1Z7dH+j/CxM3f2ITOBAKY9HBVGvZcGYV+h1FEOC5uUgTTBhQxnVU8ysBISHNWWeRSqtQVrvBKuaZQl/7nMSHLxp4/P8kQcSHi+VO8VDJE1F/Pbvf+PbT+P2/0ADs9K7V7AzjA4D/mF1+hDMMkMDvZUftt28UcOz6oMVIj5Lzf7sReQllNh/wIDAQAB";
	// 日志记录目录                                            
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
