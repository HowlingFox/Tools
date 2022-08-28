package com.example.springboottest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboottest.service.impl.QRScnnerImpl;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongProject: QRScanner
 * @BelongPackage: com.lujie.qrscanner.controller
 * @Author: lujie
 * @Data: 2021/8/20 11:40
 * Description:
 */
@RestController
@RequestMapping("/qrutil")
public class QRController {
    @Autowired
    QRScnnerImpl qrScnner;
    @RequestMapping("read")
    public String qrRead(@RequestParam("imageFile") MultipartFile imageFile) throws InterruptedException {
        JSONObject result = new JSONObject();
        String charSet = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> byteMap= new HashMap<EncodeHintType,ErrorCorrectionLevel>();
        byteMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);
        if (imageFile.isEmpty()) {
            result.put("msg", "失败，无法获取文件流");
            result.put("data","");
            result.put("success", "false");
            return JSONObject.toJSONString(result);
        }
        try {
            InputStream inputStream = imageFile.getInputStream();
            String qrStr = qrScnner.readQR(inputStream);
            result.put("msg", "成功");
            result.put("data",qrStr);
            result.put("success", "true");
            return JSONObject.toJSONString(result);
        } catch (Exception e) {
            result.put("msg", "失败，未知异常");
            result.put("data","");
            result.put("success", "false");
            return JSONObject.toJSONString(result);
        }
    }
}
