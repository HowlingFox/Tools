package com.example.springboottest.service.impl;

import com.example.springboottest.service.QRScanner;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.service
 * @Author: lujie
 * @Date: 2022/6/24 3:29
 * @Description:
 */
@Service
public class QRScnnerImpl implements QRScanner {
    @Override
    public String readQR(InputStream inputStream) throws IOException, NotFoundException {
        //截取图片
        BufferedImage bufferedImage = ImageIO.read(inputStream).getSubimage(50,50,300,300);
        //灰阶增强
        bufferedImage = toGrayImage(bufferedImage);
        ImageIO.write(bufferedImage, "png", new File("D:\\d.png"));

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

    @Override
    public String createQR(String arg, String outPutPuth, int height, int width, String charset) throws IOException, WriterException {
        BitMatrix matrix = null;
            matrix = new MultiFormatWriter().encode(
                    new String(arg.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToFile(matrix, outPutPuth.substring(outPutPuth.lastIndexOf('.') + 1), new File(outPutPuth));
        return outPutPuth;
    }

    private static BufferedImage toGrayImage(BufferedImage image) {
        BufferedImage bi = image;
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        bi = op.filter(bi, null);
        return bi;
    }
}