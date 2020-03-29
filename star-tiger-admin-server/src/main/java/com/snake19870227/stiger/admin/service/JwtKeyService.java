package com.snake19870227.stiger.admin.service;

import java.security.Key;
import java.util.Map;

/**
 * @author Bu HuaYang
 */
public interface JwtKeyService {

    Map<String, Key> loadJwtKey();
}
