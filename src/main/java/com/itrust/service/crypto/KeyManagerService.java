package com.itrust.service.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface KeyManagerService {
	/**
	 * 生成RSA公私密钥对
	 * @return
	 */
	void genaratorRSAKeyPair(Map<String,String> key) throws NoSuchAlgorithmException, NoSuchProviderException,InvalidKeySpecException;
	
	/**
	 * 数据签名
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	String sign(byte[] data, String privateKey) throws NoSuchAlgorithmException, NoSuchProviderException,InvalidKeySpecException,InvalidKeyException, SignatureException;
	
	/**
	 * 验签
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	boolean verify(byte[] data, String publicKey, String sign) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SignatureException   ;
}
