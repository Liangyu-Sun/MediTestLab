package com.softwaremanage.meditestlab.service.train_module;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

@Service
public class Pdf3Service {

    public Resource generatePdf(String title, String content) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4.rotate()); // 设置页面为横向A4
        Document document = new Document(pdfDoc);

        // 获取页面尺寸
        PageSize pageSize = pdfDoc.getDefaultPageSize();

        // 使用页面的完整宽度和高度，不考虑边距
        float pageWidth = pageSize.getWidth();
        float pageHeight = pageSize.getHeight();

        // 从资源文件中读取背景图像，并调整其大小以适应整个页面
        InputStream imageStream = new ClassPathResource("images/background.png").getInputStream();
        Image img = new Image(ImageDataFactory.create(imageStream.readAllBytes()))
                    .setWidth(pageWidth)
                    .setHeight(pageHeight);

        // 设置图像位置，使其完全覆盖整个页面
        img.setFixedPosition(0, 0); // 左下角为起点

        // 将图像添加到文档中
        document.add(img);


        // 设置字体
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);

        // 添加标题
        Paragraph titleParagraph = new Paragraph(title)
                .setFont(font)
                .setFontSize(50)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);

        float xTitle = 350; // 距离左侧边缘450像素
        float yTitle = pageHeight - 100; // 距离顶部约100像素
        titleParagraph.setFixedPosition(1, xTitle, yTitle, 340);
        document.add(titleParagraph);


        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        String currentDate = dateFormat.format(new Date());

        // 添加证书编号
        String certificateNumber = "证书编号：" + (2024100000L);
        Paragraph certificateNumberParagraph = new Paragraph(certificateNumber)
                .setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.RIGHT);
        float yCertificateNumber = yTitle - 50; // 距离标题下方50像素
        float xCertificateNumber = pageWidth - 200; // 距离右侧边缘100像素
        certificateNumberParagraph.setFixedPosition(1, xCertificateNumber, yCertificateNumber, 200);
        document.add(certificateNumberParagraph);

        // 添加内容
        Paragraph contentParagraph = new Paragraph(content + "\n日期：" + currentDate)
                .setFont(font)
                .setFontSize(16)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);

        // 假设内容距离标题下方60像素
        float yContent = yCertificateNumber - 50; // 距离编号下方50像素
        float xContent = 300; // 距离左侧300像素
        contentParagraph.setFixedPosition(1, xContent, yContent, 400); // 第一页，x坐标，y坐标，高度

        document.add(contentParagraph);

        // 签发单位和日期：距离左侧300像素，距离底部50像素；日期：距离右侧100像素，距离底部50像素
        Paragraph issuerAndDate = new Paragraph()
                .add("签发单位：xxx有限公司")
                .add("\n日期：" + currentDate);
        issuerAndDate.setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.LEFT);
        float yIssuerAndDate = 50; // 距离底部50像素
        issuerAndDate.setFixedPosition(1, 300, yIssuerAndDate, 300); // 签发单位位置
        Paragraph dateOnly = new Paragraph("日期：" + currentDate)
                .setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.RIGHT);
        dateOnly.setFixedPosition(1, pageWidth - 100, yIssuerAndDate, 300); // 日期位置
        document.add(issuerAndDate);
        document.add(dateOnly);

        document.close();

        InputStream targetStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new InputStreamResource(targetStream);
    }
}
