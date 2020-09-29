package com.itheima.health.main;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.utils.SMSUtils;

/**
 * @author: chencongming
 * @date: 2020-09-26 00:00
 */
public class SMSMain {
    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"18664622727","666666");
    }
}