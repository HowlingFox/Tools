package com.example.springboottest.util;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.util
 * @Author: lujie
 * @Date: 2022/8/22 17:53
 * @Description: 自动执行计算机事件
 */
public class AutoExecution {
    Robot robot;
    AutoExecution() throws AWTException {
        robot = new Robot();
    }

    public void mouseMove(int x, int y) throws InterruptedException {
        robot.mouseMove(x,y);
        Point point = MouseInfo.getPointerInfo().getLocation();
        System.out.println(point);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        System.out.println("第一次点击");
        Thread.sleep(3000);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        System.out.println("第二次点击");
    }
    public static void main(String[] args) throws AWTException, InterruptedException {
        AutoExecution autoExecution = new AutoExecution();
        while (true){
            autoExecution.mouseMove(1200,500);
            Thread.sleep(1000*60*10);
        }

    }
}
