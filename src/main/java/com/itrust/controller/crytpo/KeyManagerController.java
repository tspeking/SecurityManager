package com.itrust.controller.crytpo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itrust.controller.BaseController;
import com.itrust.service.crypto.KeyManagerService;

/**
 * 秘钥管理
 * @author tspeking
 *
 */

@Controller
@RequestMapping("/key/manager")
public class KeyManagerController extends BaseController {
	
	private Map<String,String> key = new HashMap<String,String>();
	
	@Resource(name = "keyManagerService")
	private KeyManagerService keyManagerService;
	
	@RequestMapping(value = "/genarator/rsaKeyPair" ,method=RequestMethod.POST)
	@ResponseBody
	public String genaratorRSAKeyPair() {
		try {
			keyManagerService.genaratorRSAKeyPair(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		String data = "hello world !";
		String sign = "";
		try {
			sign = keyManagerService.sign(data.getBytes(), key.get("privateKey"));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		logger.info("签名： " + sign);
		
		boolean status = false ;
		try {
			status = keyManagerService.verify(data.getBytes(), key.get("publicKey"), sign);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		logger.info("验签结果： " + status);
		
		return null;
	}
}
