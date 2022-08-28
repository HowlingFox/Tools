package com.example.springboottest.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.service.impl
 * @Author: lujie
 * @Date: 2022/8/22 15:34
 * @Description:
 */
public class XWPFDocumentImpl {
    /**
     * 打开Word
     *
     * @param filePath 文件全路径
     */
    public static XWPFDocument open(String filePath) throws Exception {
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            return new XWPFDocument(is);
        } finally {
            is.close();
        }
    }


    public static void setContent(XWPFDocument doc) throws IOException, InvalidFormatException {
        // 标题1，1级大纲
        XWPFParagraph para1 = doc.createParagraph();
        // 关键行// 1级大纲
        para1.setStyle("1");
        XWPFRun run1 = para1.createRun();
        // 标题内容
        run1.setText("标题1");


        // 标题2，2级大纲
        XWPFParagraph para2 = doc.createParagraph();
        // 关键行// 2级大纲
        para2.setStyle("2");

        XWPFRun run2 = para2.createRun();
        // 标题内容
        run2.setText("标题2");

        // 正文
        XWPFParagraph paraX = doc.createParagraph();
        XWPFRun runX = paraX.createRun();
        for (int i = 0; i < 100; i++) {
            // 正文内容
            runX.setText("正文\r\n");
        }

        XWPFParagraph para3 = doc.createParagraph();
        XWPFRun run = para3.createRun();
        //设置级别
        para3.setStyle("2");
        run.setText("测试标题3");
        XWPFParagraph para3_text = doc.createParagraph();
        XWPFRun run_text = para3_text.createRun();
        run_text.setText("二级内容");

        XWPFParagraph paragraph3_1 = doc.createParagraph();
        XWPFRun run3_1 = paragraph3_1.createRun();
        //设置级别
        paragraph3_1.setStyle("3");
        run3_1.setText("三级标题文本3");

        XWPFParagraph paragraph3_1_text = doc.createParagraph();
        XWPFRun run3_1_text = paragraph3_1_text.createRun();
        run3_1_text.setText("三级标题文本，文本，文本");
        //追加柱状图
        createHistogram(doc);

    }

    public static void createHistogram(XWPFDocument doc) throws IOException, InvalidFormatException {
        // 2、创建chart图表对象,抛出异常
        XWPFChart chart = doc.createChart(15 * Units.EMU_PER_CENTIMETER, 10 * Units.EMU_PER_CENTIMETER);

        // 3、图表相关设置
        chart.setTitleText("使用POI创建的柱状图"); // 图表标题
        chart.setTitleOverlay(false); // 图例是否覆盖标题

        // 4、图例设置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP); // 图例位置:上下左右

        // 5、X轴(分类轴)相关设置
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM); // 创建X轴,并且指定位置
        xAxis.setTitle("日期（年月）"); // x轴标题
        String[] xAxisData = new String[] {
                "2021-01","2021-02","2021-03","2021-04","2021-05","2021-06",
                "2021-07","2021-08","2021-09","2021-10","2021-11","2021-12",
        };
        XDDFCategoryDataSource xAxisSource = XDDFDataSourcesFactory.fromArray(xAxisData,"x"); // 设置X轴数据

        // 6、Y轴(值轴)相关设置
        XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT); // 创建Y轴,指定位置
        yAxis.setTitle("粉丝数（个）"); // Y轴标题
        yAxis.setCrossBetween(AxisCrossBetween.BETWEEN); // 设置图柱的位置:BETWEEN居中
        Integer[] yAxisData = new Integer[]{
                10, 35, 21, 46, 79, 88,
                39, 102, 71, 28, 99, 57
        };
        XDDFNumericalDataSource<Integer> yAxisSource = XDDFDataSourcesFactory.fromArray(yAxisData,"y"); // 设置Y轴数据

        // 7、创建柱状图对象
        XDDFBarChartData barChart = (XDDFBarChartData) chart.createData(ChartTypes.BAR, xAxis, yAxis);
        barChart.setBarDirection(BarDirection.COL); // 设置柱状图的方向:BAR横向,COL竖向,默认是BAR

        // 8、加载柱状图数据集
        XDDFBarChartData.Series barSeries = (XDDFBarChartData.Series) barChart.addSeries(xAxisSource, yAxisSource);
        barSeries.setTitle("粉丝数", null); // 图例标题

        // 9、绘制柱状图
        chart.plot(barChart);
    }


    /**
     * 判断文件是否存在，不存在就创建
     *
     * @param file
     */
    public static void createFile(File file) {
        if (file.exists()) {
            System.out.println("File exists");
        } else {
            System.out.println("File not exists, create it ...");
            //getParentFile() 获取上级目录(包含文件名时无法直接创建目录的)
            if (!file.getParentFile().exists()) {
                System.out.println("not exists");
                //创建上级目录
                file.getParentFile().mkdirs();
            }
            try {
                //在上级目录里创建文件
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File copyFile(String tempPath, String targetPath) throws Exception {
        File temp = new File(tempPath);
        File target = new File(targetPath);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(temp);
            out = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
            }
            out.flush();
            return target;
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * 设置页眉
     *
     * @param text 页眉内容
     */
    static void setHeader(XWPFDocument doc, String text) {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, doc);//段落对象
        ctp.addNewR().addNewT().setStringValue(text);//设置页眉参数
        ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.PRESERVE);
        CTSectPr sectPr = doc.getDocument().getBody().isSetSectPr() ? doc.getDocument().getBody().getSectPr() : doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc, sectPr);
        XWPFHeader header = policy.createHeader(STHdrFtr.DEFAULT, new XWPFParagraph[]{paragraph});
        header.setXWPFDocument(doc);
    }


    public static void main(String[] args) throws Exception {
        //下面是模块测试
        String tempPath = "D:\\test\\poi\\word\\test.docx"; //模板文件路径
        String targetPath = "D:\\test\\poi\\word\\fomat.docx";//生成文件路径

        //复制模板
        File file = copyFile(tempPath, targetPath);
        createFile(file);
        //打开word
        XWPFDocument doc = open(targetPath);
        //设置页眉
        setHeader(doc, "测试页眉");
        //添加内容
        setContent(doc);
        //更新目录
        doc.enforceUpdateFields();
        //word写入到文件
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(targetPath);
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
