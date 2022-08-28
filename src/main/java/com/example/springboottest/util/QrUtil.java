package com.example.springboottest.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;


/**
 * @BelongProject: QRScanner
 * @BelongPackage: com.lujie.qrscanner.qr
 * @Author: lujie
 * @Data: 2021/8/20 9:47
 * Description: 二维码识别与生成
 */
public class QrUtil {


    // Function to create the QR code
    public void createQR(String data, String path, String charset, Map hashMap, int height, int width) throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    private  void print(File file) throws WriterException, IOException, PrintException {
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
        PrintService defaultService = PrintServiceLookup
                .lookupDefaultPrintService();
        DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
        Object fis = new FileInputStream(file); // 构造待打印的文件流
        DocAttributeSet das = new HashDocAttributeSet();
        Doc doc = new SimpleDoc(fis, flavor, das);
        job.print(doc, pras);
    }


    // 扫描二维码图片
    public String readQR(FileInputStream inputStream, String charset, Map hashMap) throws FileNotFoundException, IOException, NotFoundException {

        //截取图片
        BufferedImage bufferedImage = ImageIO.read(inputStream).getSubimage(50,50,300,300);
        //灰阶增强
        bufferedImage = toGrayImage(bufferedImage);
        ImageIO.write(bufferedImage, "png", new File("D:\\d.png"));

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

    /**
     * 处理原图模糊，灰阶增强。
     *
     * @param image
     * @return
     */
    private static BufferedImage toGrayImage(BufferedImage image) {
        BufferedImage bi = image;
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        bi = op.filter(bi, null);
        return bi;
    }

}
