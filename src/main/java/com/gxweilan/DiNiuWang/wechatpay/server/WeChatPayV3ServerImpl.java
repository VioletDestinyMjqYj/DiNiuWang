package com.gxweilan.diniuwang.wechatpay.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import com.gxweilan.diniuwang.wechatpay.auth.WechatPayHttpClientBuilder;
import com.gxweilan.diniuwang.wechatpay.entity.WeChatPayV3;
import com.gxweilan.diniuwang.wechatpay.config.WeChatPayV3Config;
import com.gxweilan.diniuwang.wechatpay.constant.WeChatPayV3Constants;
import com.gxweilan.diniuwang.wechatpay.util.RsaCryptoUtil;
import com.gxweilan.diniuwang.wechatpay.util.WeChatPayV3Util;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 86151
 */
@Service
public class WeChatPayV3ServerImpl implements WeChatPayV3Server {

	private final WeChatPayV3Config config;

	public WeChatPayV3ServerImpl(WeChatPayV3Config config) {
		this.config = config;
	}

	/**
	 * 向 ObjectNode 中添加 appid、mch_id、notify_url
	 *
	 * @param rootNode
	 * @return
	 */
	@Override
	public ObjectNode fillRequestData(ObjectNode rootNode){
		rootNode.put("mchid",config.getMchID())
			.put("appid", config.getAppID());
		return rootNode;
	}

	/**
	 * 微信鉴权
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject getAccessToken() throws IOException, URISyntaxException {
		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		URIBuilder newBuilder = new URIBuilder(WeChatPayV3Constants.ACCESS_TOKEN);
		newBuilder.setParameter("appid", config.getAppID());
		newBuilder.setParameter("secret", config.getAppSecret());
		newBuilder.setParameter("grant_type", "client_credential");

		HttpGet httpGet = new HttpGet(newBuilder.build());
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-type","application/json; charset=utf-8");

		CloseableHttpResponse response = httpClient.execute(httpGet);
		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 获取手机号（新版）
	 * @param jsonObject
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject getPhoneNumber(JSONObject jsonObject) throws IOException, URISyntaxException {
		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		HttpPost httpPost = new HttpPost(WeChatPayV3Constants.PHONE_NUMBER + getAccessToken().getString("access_token"));
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-type","application/json; charset=utf-8");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(bos, jsonObject);

		httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
		CloseableHttpResponse response = httpClient.execute(httpPost);

		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 统一下单接口
	 * @param rootNode
	 * @return
	 * @throws IOException
	 */
	@Override
	public JSONObject unifiedOrder(ObjectNode rootNode) throws IOException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		HttpPost httpPost = new HttpPost(WeChatPayV3Constants.BASIC_URL + WeChatPayV3Constants.UNIFIED_ORDER);
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-type","application/json; charset=utf-8");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(bos, rootNode);

		httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
		CloseableHttpResponse response = httpClient.execute(httpPost);

		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 统一下单二次签名数据处理(个人编写，可自行修改)
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> payMap(JSONObject jsonObject) throws Exception {
		HashMap<String, String> payMap = new HashMap<>();
		payMap.put("appId", config.getAppID());
		payMap.put("timeStamp", WeChatPayV3Util.getCurrentTimestamp() + "");
		payMap.put("nonceStr", WeChatPayV3Util.generateNonceStr());
		payMap.put("signType", WeChatPayV3Constants.SIGN_TYPE);
		payMap.put("package", "prepay_id=" + jsonObject.get("prepay_id"));
		String paySign = RsaCryptoUtil.structurePaySign(payMap, config.getMerchantPrivateKey());
		payMap.put("paySign", paySign);
		return payMap;
	}

	/**
	 * 通过 商户订单号 查询订单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject orderQueryNO(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		URIBuilder newBuilder = new URIBuilder(WeChatPayV3Constants.BASIC_URL + WeChatPayV3Constants.QUERY_ORDER_OUT_TRADE_NO + weChatPayV3.getOutTradeNo());
		return getJsonObject(httpClient, newBuilder);
	}

	/**
	 * 通过 微信支付订单号 查询订单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject orderQueryId(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		URIBuilder newBuilder = new URIBuilder(WeChatPayV3Constants.BASIC_URL + WeChatPayV3Constants.QUERY_ORDER_TRANSACTION_ID + weChatPayV3.getTransactionId());
		return getJsonObject(httpClient, newBuilder);
	}

	@Override
	public JSONObject getJsonObject(CloseableHttpClient httpClient, URIBuilder newBuilder) throws URISyntaxException, IOException {
		newBuilder.setParameter("mchid", config.getMchID());

		HttpGet httpGet = new HttpGet(newBuilder.build());
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-type","application/json; charset=utf-8");

		CloseableHttpResponse response = httpClient.execute(httpGet);
		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 通过 商户订单号 申请退款
	 * @param rootNode
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject refunds(ObjectNode rootNode) throws IOException, URISyntaxException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		HttpPost httpPost = new HttpPost(WeChatPayV3Constants.BASIC_URL + WeChatPayV3Constants.REFUND);
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-type","application/json; charset=utf-8");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(bos, rootNode);

		httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
		CloseableHttpResponse response = httpClient.execute(httpPost);

		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 通过 商户订单号 查询退款订单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject refundQueryNO(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		URIBuilder newBuilder = new URIBuilder(WeChatPayV3Constants.BASIC_URL + WeChatPayV3Constants.QUERY_REFUND + weChatPayV3.getOutTradeNo());

		HttpGet httpGet = new HttpGet(newBuilder.build());
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-type","application/json; charset=utf-8");

		CloseableHttpResponse response = httpClient.execute(httpGet);
		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 申请交易账单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject tradeBill(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		URIBuilder newBuilder = new URIBuilder(WeChatPayV3Constants.BASIC_URL + WeChatPayV3Constants.TRADE_BILL);
		newBuilder.setParameter("bill_date", weChatPayV3.getBillDate());
		newBuilder = weChatPayV3.getBillType() == null ? newBuilder.setParameter("bill_type", weChatPayV3.getBillType()) : newBuilder;
		System.out.println("URL" + newBuilder.build());

		HttpGet httpGet = new HttpGet(newBuilder.build());
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-type","application/json; charset=utf-8");

		CloseableHttpResponse response = httpClient.execute(httpGet);
		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}

	/**
	 * 下载交易账单（官方文档不全，接口不好使）
	 * @param jsonObject
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public JSONObject billDownload(JSONObject jsonObject) throws IOException, URISyntaxException {

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			.withMerchant(config.getMchID(), config.getMerchantSerialNumber(), config.getMerchantPrivateKey())
			.withValidator(config.getValidator());
		// 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
		//通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		CloseableHttpClient httpClient = builder.build();

		HttpGet httpGet = new HttpGet(jsonObject.getString("download_url"));
		httpGet.addHeader("Authorization", "WECHATPAY2-SHA256-RSA2048mchid=\""+config.getMchID()+"\"");
		httpGet.addHeader("nonce_str", WeChatPayV3Util.generateNonceStr());
		httpGet.addHeader("signature", "12");
		httpGet.addHeader("timestamp", "\"" + WeChatPayV3Util.getCurrentTimestamp() + "\"");
		httpGet.addHeader("serial_no", config.getMerchantSerialNumber());

		CloseableHttpResponse response = httpClient.execute(httpGet);
		return JSON.parseObject(EntityUtils.toString(response.getEntity()));
	}
}
