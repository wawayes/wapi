package com.wapi.wapiclientsdk.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.time.Instant;
import java.util.Date;

public class CommonUtil {
    public static String getSign(String body, String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String str = body + "." + secretKey;
        return md5.digestHex(str);
    }

    public static boolean checkTimeStamp(String timestamp) {
        Instant instant = Instant.ofEpochSecond(Long.parseLong(timestamp));
        long checkTimestamp = instant.toEpochMilli();
        long now = System.currentTimeMillis();
        long diffInMinutes = DateUtil.between(new Date(checkTimestamp), new Date(now), DateUnit.MINUTE);
        return diffInMinutes <= 5;
    }
}
