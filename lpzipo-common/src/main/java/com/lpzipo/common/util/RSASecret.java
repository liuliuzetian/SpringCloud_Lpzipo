package com.lpzipo.common.util;


import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 加密解密工具类
 */
public class RSASecret {

    public static final String CIPHER_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 2048;


    /**
     * 文件中获取公钥
     * @param fileName
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String fileName) throws Exception {
        InputStream inputStream = new FileInputStream(new File(fileName));
        DataInputStream dis = new DataInputStream(inputStream);
        byte[] keyBytes = new  byte[inputStream.available()];
        dis.readFully(keyBytes);
        dis.close();
        return getPublicKey(keyBytes);
    }

    /**
     * 文件中获取私钥
     * @param fileName
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String fileName) throws Exception{
        InputStream resourceAsStream = new FileInputStream(new File(fileName));
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        dis.close();
        return getPrivateKey(keyBytes);
    }

    /**
     * 获取公钥
     * @param
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(byte[] publicKey) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(CIPHER_ALGORITHM);
        return kf.generatePublic(spec);
    }

    /**
     * 获取私钥
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(byte[] privateKey) throws Exception{
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(CIPHER_ALGORITHM);
        return kf.generatePrivate(spec);
    }

    /**
     * 生成密钥对
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String,byte[]> generateKeyBytes() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CIPHER_ALGORITHM);
        //密钥位数
        keyPairGenerator.initialize(KEY_SIZE);
        //密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String,byte[]> keyMap = new HashMap<String, byte[]>();
        keyMap.put(PUBLIC_KEY,publicKey.getEncoded());
        keyMap.put(PRIVATE_KEY,privateKey.getEncoded());
        return keyMap;
    }

    /**
     * 公钥加密
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeByPublic(PublicKey key, byte[] data) throws Exception {
        //加解密类
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decodeByPrivate(PrivateKey key, byte[] data) throws Exception{
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) {
        RSASecret util = new RSASecret();
        try {
            Map<String,byte[]> keyMap = generateKeyBytes();
            PublicKey publicKey = util.getPublicKey(keyMap.get(RSASecret.PUBLIC_KEY));
            PrivateKey privateKey = util.getPrivateKey(keyMap.get(RSASecret.PRIVATE_KEY));
            byte[] encodeText = util.encodeByPublic(publicKey,"liuzetian".getBytes());
//            System.out.println(Base64.encodeBase64(encodeText));
            byte[] decodeText = util.decodeByPrivate(privateKey,encodeText);
            System.out.println(new String(decodeText,"UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
