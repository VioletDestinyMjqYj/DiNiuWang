package com.gxweilan.diniuwang.wechatpay.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import com.gxweilan.diniuwang.wechatpay.util.ReturnUtil;
import com.gxweilan.diniuwang.wechatpay.entity.WeChatPayV3;
import com.gxweilan.diniuwang.wechatpay.constant.WeChatPayV3Constants;
import com.gxweilan.diniuwang.wechatpay.server.WeChatPayV3Server;
import com.gxweilan.diniuwang.wechatpay.util.AesUtil;
import com.gxweilan.diniuwang.wechatpay.util.WeChatPayV3Util;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;


/**
 * @author LiMY
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxPayV3")
public class WeChatPayV3Controller {

	private final WeChatPayV3Server wxPayV3Server;

	/**
	 * 统一下单测试接口
	 * @param myConfig
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/unifiedOrder")
	public JSONObject unifiedOrder(@RequestBody WeChatPayV3 myConfig) throws Exception {
		System.out.println(myConfig);
		String uuid = WeChatPayV3Util.generateNonceStr();
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode rootNode = objectMapper.createObjectNode();
		rootNode.put("description", myConfig.getDescription()) //前端需要传给后端的字段
				.put("out_trade_no", uuid)
				.put("notify_url", "http://weixin.qq.com/");
		rootNode.putObject("amount")
				.put("total", myConfig.getTotalFee());	//前端需要传给后端的字段
		rootNode.putObject("payer")
				.put("openid", myConfig.getOpenId());	//前端需要传给后端的字段
		System.out.println("商户订单号：" + uuid);
		JSONObject jsonObject = wxPayV3Server.unifiedOrder(wxPayV3Server.fillRequestData(rootNode));
		System.out.println("test" + jsonObject);
		return ReturnUtil.data(wxPayV3Server.payMap(jsonObject));
	}

	/**
	 * 通过 商户订单号 查询订单
	 * @param myConfig
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/orderQuery")
	public JSONObject orderQuery(@RequestBody WeChatPayV3 myConfig) throws Exception {
		return ReturnUtil.data(wxPayV3Server.orderQueryNO(myConfig));
	}

	/**
	 * 统一下单回调地址
	 * @param notifyData
	 * @return
	 */
	@PostMapping("/orderNotify")
	public String orderNotify(@RequestBody JSONObject notifyData) throws GeneralSecurityException {
		System.out.println("统一下单回调地址数据" + notifyData);
		AesUtil aesUtil = new AesUtil(new WeChatPayV3().getApiV3Key().getBytes());
		JSONObject data = aesUtil.decryptJsonToString(notifyData);
		System.out.println(data);
		return WeChatPayV3Constants.NOTIFY_SUCCESS;

	}

	/**
	 * 申请退款
	 * @param myConfig
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/refunds")
	public JSONObject refunds(@RequestBody WeChatPayV3 myConfig) throws Exception {
		String uuid = WeChatPayV3Util.generateNonceStr();
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode rootNode = objectMapper.createObjectNode();
		rootNode.put("out_trade_no", myConfig.getOutTradeNo())
				.put("out_refund_no", uuid);
		rootNode.putObject("amount")
				.put("refund", myConfig.getRefundFee())
				.put("total", myConfig.getTotalFee())
				.put("currency", myConfig.getCurrency());
		System.out.println("商户退款订单号" + uuid);
		return ReturnUtil.data(wxPayV3Server.refunds(rootNode));
	}

	/**
	 * 查询退款订单
	 * @param myConfig
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/refundQuery")
	public JSONObject refundQuery(@RequestBody WeChatPayV3 myConfig) throws Exception {
		return ReturnUtil.data(wxPayV3Server.refundQueryNO(myConfig));
	}

	/**
	 * 申请退款回调地址
	 * @param notifyData
	 * @return
	 */
	@PostMapping("/refundNotify")
	public String refundNotify(@RequestBody JSONObject notifyData) throws GeneralSecurityException {
		System.out.println("申请退款回调地址数据" + notifyData);
		AesUtil aesUtil = new AesUtil(new WeChatPayV3().getApiV3Key().getBytes());
		JSONObject data = aesUtil.decryptJsonToString(notifyData);
		System.out.println(data);
		return WeChatPayV3Constants.NOTIFY_SUCCESS;

	}

	/**
	 * 申请交易账单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@PostMapping("/tradeBill")
	public JSONObject refundNotify(@RequestBody WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException{
		return wxPayV3Server.tradeBill(weChatPayV3);

	}

}
