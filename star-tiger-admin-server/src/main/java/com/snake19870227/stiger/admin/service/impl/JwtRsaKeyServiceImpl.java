package com.snake19870227.stiger.admin.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.RSA;
import com.snake19870227.stiger.admin.dao.mapper.SysCfgMapper;
import com.snake19870227.stiger.admin.entity.po.SysCfg;
import com.snake19870227.stiger.admin.service.JwtKeyService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Bu HuaYang
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JwtRsaKeyServiceImpl implements JwtKeyService {

    private static final Logger logger = LoggerFactory.getLogger(JwtRsaKeyServiceImpl.class);

    @Value("${stiger.security.jwt.public-key-code:jwt-public-key}")
    private String publicKeyCode;
    @Value("${stiger.security.jwt.private-key-code:jwt-private-key}")
    private String privateKeyCode;

    private SysCfgMapper sysCfgMapper;

    public JwtRsaKeyServiceImpl(SysCfgMapper sysCfgMapper) {
        this.sysCfgMapper = sysCfgMapper;
    }

    @Override
    public Map<String, Key> loadJwtKey() {
        Map<String, Key> keyMap = new HashMap<>(2);
        Optional<SysCfg> publicKeyCfgObj = Optional.ofNullable(sysCfgMapper.selectById(publicKeyCode));
        Optional<SysCfg> privateKeyCfgObj = Optional.ofNullable(sysCfgMapper.selectById(privateKeyCode));
        if (publicKeyCfgObj.isPresent() && privateKeyCfgObj.isPresent()) {
            try {
                RSA rsa = new RSA(privateKeyCfgObj.get().getCfgValue(), publicKeyCfgObj.get().getCfgValue());
                keyMap.put(publicKeyCode, rsa.getPublicKey());
                keyMap.put(privateKeyCode, rsa.getPrivateKey());
            } catch (Exception e) {
                logger.error("系统RSA密钥对无效,重新生成...", e);
                keyMap = createKey();
            }
        } else {
            keyMap = createKey();
        }
        return keyMap;
    }

    private Map<String, Key> createKey() {
        Map<String, Key> keyMap = new HashMap<>(2);
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        sysCfgMapper.insert(new SysCfg().setCfgCode(publicKeyCode).setCfgValue(Base64.encode(keyPair.getPublic().getEncoded())));
        sysCfgMapper.insert(new SysCfg().setCfgCode(privateKeyCode).setCfgValue(Base64.encode(keyPair.getPrivate().getEncoded())));
        keyMap.put(publicKeyCode, keyPair.getPublic());
        keyMap.put(privateKeyCode, keyPair.getPrivate());
        return keyMap;
    }
}
