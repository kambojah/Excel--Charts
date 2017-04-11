package com.mobisystems.automation.excel.area;


import com.mobisystems.automation.excel.AcceptanceTest;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Area extends AcceptanceTest {

    @Test(priority = 1)
    public void openArea() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Chart1")));
        driver.findElementByAccessibilityId("Chart1").tap(1, 1);
        takeScreenshot("Area");
    }

    @Test(priority = 2)
    public static void visualArea() throws IOException, InterruptedException {

        long start = System.currentTimeMillis();
        int q=0;
        File file1 = new File("/Users/pavelpopov/Desktop/Automation/Excel/Charts/PNG/Area.txt");

        FileWriter fw = new FileWriter(file1.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        File fileA= new File("/Users/pavelpopov/Desktop/Automation/Excel/Charts/PNG/Actual/Area.PNG");
        BufferedImage image = ImageIO.read(fileA);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        int[][] clr=  new int[width][height];

        File fileB= new File("/Users/pavelpopov/Desktop/Automation/Excel/Charts/PNG/Base/Area.PNG");
        BufferedImage images = ImageIO.read(fileB);
        int widthe = images.getWidth(null);
        int heighte = images.getHeight(null);
        int[][] clre=  new int[widthe][heighte];
        int smw=0;
        int smh=0;
        int p=0;

        if(width>widthe)
        {
            smw =widthe;
        }
        else
        {
            smw=width;
        }
        if(height>heighte)
        {
            smh=heighte;
        }
        else
        {
            smh=height;
        }

        for(int a=0;a<smw;a++)
        {
            for(int b=0;b<smh;b++)
            {
                clre[a][b]=images.getRGB(a,b);
                clr[a][b]=image.getRGB(a,b);
                if(clr[a][b]==clre[a][b])
                {
                    p=p+1;
                    bw.write("\t");
                    bw.write(Integer.toString(a));
                    bw.write("\t");
                    bw.write(Integer.toString(b));
                    bw.write("\n");
                }
                else
                    q=q+1;
            }
        }

        float w,h=0;
        if(width>widthe)
        {
            w=width;
        }
        else
        {
            w=widthe;
        }
        if(height>heighte)
        {
            h = height;
        }
        else
        {
            h = heighte;
        }
        float s = (smw*smh);

        float x =(100*p)/s;

        LOG.info("AREA CHART VISUAL TEST IS COMPLETED");
        LOG.info("THE PERCENTAGE SIMILARITY IS APPROXIMATELY ="+x+"%");
        long stop = System.currentTimeMillis();
        LOG.info("TIME FOR COMPARISION ="+(stop-start));
        LOG.info("NUMBER OF PIXEL GETS VARIED:="+q);
        LOG.info("NUMBER OF PIXEL GETS MATCHED:="+p);
    }
}


