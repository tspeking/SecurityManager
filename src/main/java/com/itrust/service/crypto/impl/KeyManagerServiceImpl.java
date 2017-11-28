package com.itrust.service.crypto.impl;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itrust.service.crypto.KeyManagerService;

/**
 * PKCS Public Key Cryptograph Standards 公钥密码学标准
 * 
 * PKCS#1：定义RSA公开密钥算法加密和签名机制，主要用于组织PKCS#7中所描述的数字签名和数字信封。
 * PKCS#3：定义Diffie-Hellman密钥交换协议。
 * PKCS#5：描述一种利用从口令派生出来的安全密钥加密字符串的方法。使用MD2或MD5 从口令中派生密钥，
 * 		      并采用DES-CBC模式加密。主要用于加密从一个计算机传送到另一个计算机的私人密钥，不能用于加密消息。
 * PKCS#6：描述了公钥证书的标准语法，主要描述X.509证书的扩展格式。
 * PKCS#7：定义一种通用的消息语法，包括数字签名和加密等用于增强的加密机制，PKCS#7与PEM兼容，
 * 		      所以不需其他密码操作，就可以将加密的消息转换成PEM消息。
 * PKCS#8：描述私有密钥信息格式，该信息包括公开密钥算法的私有密钥以及可选的属性集等。
 * PKCS#9：定义一些用于PKCS#6证书扩展、PKCS#7数字签名和PKCS#8私钥加密信息的属性类型。
 * PKCS#10：描述证书请求语法。
 * PKCS#11：称为Cyptoki，定义了一套独立于技术的程序设计接口，用于智能卡和PCMCIA卡之类的加密设备。
 * PKCS#12：描述个人信息交换语法标准。描述了将用户公钥、私钥、证书和其他相关信息打包的语法。
 * PKCS#13：椭圆曲线密码体制标准。
 * PKCS#14：伪随机数生成标准。
 * PKCS#15：密码令牌信息格式标准。
 */

/**
 * 秘钥管理服务类
 * @author tspeking
 *
 */
@Service("keyManagerService")
public class KeyManagerServiceImpl implements KeyManagerService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    private final static String ALGORITHM = "RSA";  
    private final static String SIGNATURE_ALGORITHM = "MD5withRSA";
    private final static String PROVIDER = "BC";

	@Override
	public void genaratorRSAKeyPair(Map<String,String> key) throws NoSuchAlgorithmException, 
		NoSuchProviderException, InvalidKeySpecException {
		
		Security.addProvider(new BouncyCastleProvider()); 
		KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER); 
		generator.initialize(1024, new SecureRandom()); 
		KeyPair pair = generator.generateKeyPair();
		PublicKey pubKey = pair.getPublic();
		PrivateKey privKey = pair.getPrivate();
		byte[] pk = pubKey.getEncoded();  
	    byte[] privk = privKey.getEncoded();  
	    String strpk = new String(Base64.encodeBase64(pk));  
	    String strprivk = new String(Base64.encodeBase64(privk));
	    key.put("privateKey", strprivk);
	    key.put("publicKey", strpk);
	    
	    logger.info("公钥:" + Arrays.toString(pk));  
	    logger.info("私钥:" + Arrays.toString(privk));  
	    logger.info("公钥Base64编码:" + strpk);  
	    logger.info("私钥Base64编码:" + strprivk);  
	}

	@Override
	public String sign(byte[] data, String privateKey) throws NoSuchAlgorithmException, 
		NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		String str = "";  
		// 解密由base64编码的私钥  
        byte[] bytes = Base64.decodeBase64(privateKey.getBytes());  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);  
        // 指定的加密算法  
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM, PROVIDER);  
        // 取私钥对象  
        PrivateKey key = factory.generatePrivate(pkcs);  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(key);  
        signature.update(data);  
        str =  new String(Base64.encodeBase64(signature.sign()));
        
		return str;
	}

	@Override
	public boolean verify(byte[] data, String publicKey, String sign) throws NoSuchAlgorithmException, 
		NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		
		boolean flag = false; 
		// 解密由base64编码的公钥  
        byte[] bytes = Base64.decodeBase64(publicKey.getBytes()); 
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);  
        // 指定的加密算法  
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM, PROVIDER);  
        // 取公钥对象  
        PublicKey key = factory.generatePublic(keySpec);  
        // 用公钥验证数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initVerify(key);  
        signature.update(data);  
        flag = signature.verify(Base64.decodeBase64(sign.getBytes()));
        
		return flag;
	}
	
	
}
