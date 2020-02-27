package com.github.platform.sf.common.util.security.std;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * DES加密和解密工具,可以对字符串进行加密和解密操作 。
 * @author zhangjj
 * @create 2017年6月19日 上午9:39:01
 * @since
 */
@Slf4j
public class DESEncryptUtil {
	
	private static final String TYPE = "DES";


	private DESEncryptUtil(){}


	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 *
	 * @since
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, TYPE);

		return key;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @since
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @since
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 加密字节数组
	 * 
	 * @since
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] arrB, String key) throws Exception {
		Cipher encryptCipher = Cipher.getInstance(TYPE);
		encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(key.getBytes()));
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @since
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encrypt(String strIn, String key) {
        try {
            return byteArr2HexStr(encrypt(strIn.getBytes(), key));
        } catch (Exception e) {
            log.error("加密失败", e);
            return null;
        }
    }

	/**
	 * 解密字节数组
	 * 
	 * @since
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] arrB, String key) throws Exception {
		Cipher decryptCipher = Cipher.getInstance(TYPE);
		decryptCipher.init(Cipher.DECRYPT_MODE, getKey(key.getBytes()));
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @since
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String strIn, String key) {
        try {
            return new String(decrypt(hexStr2ByteArr(strIn), key));
        } catch (Exception e) {
            log.error("解密失败", e);
            return null;
        }
    }



	/**
	 * 测试
	 * 
	 * @since
	 * @param args
	 */
	public static void main(String[] args) {
		String key = "13424";
		try {
			String test = "root";
			System.out.println("加密后的字符：" + DESEncryptUtil.encrypt(test, key));
			System.out.println("解密后的字符：" + DESEncryptUtil.decrypt("3989d09b94129916", key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
