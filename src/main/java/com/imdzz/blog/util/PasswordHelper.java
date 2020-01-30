package com.imdzz.blog.util;

import com.imdzz.blog.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加密密码的工具类
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 10:45
 */
public class PasswordHelper {
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "md5"; // 基础散列算法
    public static final int HASH_ITERATIONS = 1; // 自定义散列次数

    /**
     * 加盐散列，设置用户密码
     * @param user
     */
    public static void encryptPassword(User user) {
        // 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(),
                user.getSalt(), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }


}
