package com.gxweilan.diniuwang.wechatpay.server;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import com.gxweilan.diniuwang.wechatpay.entity.WeChatPayV3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface WeChatPayV3Server {
	/**
	 * 向 ObjectNode 中添加 appid、mch_id、notify_url
	 *
	 * @param rootNode
	 * @return
	 */
	ObjectNode fillRequestData(ObjectNode rootNode);

	/**
	 * 微信鉴权
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject getAccessToken() throws IOException, URISyntaxException;

	/**
	 * 获取手机号（新版本）
	 * @param jsonObject
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject getPhoneNumber(JSONObject jsonObject) throws IOException, URISyntaxException;

	/**
	 * 统一下单接口
	 * @param rootNode
	 * @return
	 * @throws IOException
	 */
	JSONObject unifiedOrder(ObjectNode rootNode) throws IOException;

	/**
	 * 统一下单二次签名数据处理(个人编写，可自行修改)
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	Map<String, String> payMap(JSONObject jsonObject) throws Exception;

	/**
	 * 通过 商户订单号 查询订单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject orderQueryNO(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException;

	/**
	 * 通过 微信支付订单号 查询订单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject orderQueryId(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException;

	JSONObject getJsonObject(CloseableHttpClient httpClient, URIBuilder newBuilder) throws URISyntaxException, IOException;

	/**
	 * 通过 商户订单号 申请退款
	 * @param rootNode
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject refunds(ObjectNode rootNode) throws IOException, URISyntaxException;

	/**
	 * 通过 商户订单号 查询退款订单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject refundQueryNO(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException;

	/**
	 * 申请交易账单
	 * @param weChatPayV3
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject tradeBill(WeChatPayV3 weChatPayV3) throws IOException, URISyntaxException;

	/**
	 * 下载交易账单（官方文档不全，接口不好使）
	 * @param jsonObject
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	JSONObject billDownload(JSONObject jsonObject) throws IOException, URISyntaxException;

}
