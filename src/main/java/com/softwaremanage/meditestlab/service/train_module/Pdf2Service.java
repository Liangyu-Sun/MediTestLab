package com.softwaremanage.meditestlab.service.train_module;

import ch.qos.logback.classic.Logger;
import com.itextpdf.kernel.colors.Color;
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
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Standard;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import com.softwaremanage.meditestlab.repository.exam_module.AssessmentRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.ProjectRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.StandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.desktop.PrintFilesEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



@Service
public class Pdf2Service {
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private UserRepository userRepository;


    public Resource generateAssessmentPdf(Integer assessmentId) throws IOException {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID"));

        if (!assessment.isCompleted()) {
            throw new IllegalStateException("Assessment is not completed");
        }

        Project project = projectRepository.findByPId(assessment.getProjectId());
        Standard standard = standardRepository.findBySId(project.getsId());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4.rotate()); // 设置页面为横向A4
        Document document = new Document(pdfDoc);

        // 设置字体
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);

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

        // 添加标题
        Paragraph title = new Paragraph("考核证书")
                .setFont(font)
                .setFontSize(50)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
        float xTitle = 350; // 距离左侧边缘450像素
        float yTitle = pageHeight - 100; // 距离顶部约100像素
        title.setFixedPosition(1, xTitle, yTitle, 340); // 第一页，X坐标，Y坐标，高度
        document.add(title);

        // 添加证书编号
        String certificateNumber = "证书编号：" + (2024100000L + assessment.getAssessmentId());
        Paragraph certificateNumberParagraph = new Paragraph(certificateNumber)
                .setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.RIGHT);
        float yCertificateNumber = yTitle - 50; // 距离标题下方50像素
        float xCertificateNumber = pageWidth - 250; // 距离右侧边缘100像素
        certificateNumberParagraph.setFixedPosition(1, 500, yCertificateNumber, 250);
        document.add(certificateNumberParagraph);

        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        String currentDate = dateFormat.format(new Date());

        float yContent = yTitle - 100; // 距离标题下方250像素
        float xContent = 300;

        // 获取 inspectorId 对应的真实姓名
        User user = userRepository.findRealnameByuId(assessment.getInspectorId());
        String inspectorRealname=user.getuRealname();

        Paragraph content1 = new Paragraph()
                .add(new Text("兹证明 "))
                .add(new Text(inspectorRealname + " 先生/女士："))
                .setFont(font)
                .setFontSize(22)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.LEFT);

        content1.setFixedPosition(1, xContent, yContent, 450);
        document.add(content1);

        Paragraph content2 = new Paragraph("    于 " + currentDate + " 完成 " + standard.getsName() + " ，" + project.getpNum() + " ，" + project.getpName() + " 项目考核，获得该项目检测授权。")
                .setFont(font)
                .setFontSize(22)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.LEFT);

        content2.setFixedPosition(1, xContent, yContent - 120, 450); // 调整 y 坐标以确保换行
        document.add(content2);

        Paragraph content3 = new Paragraph("    特发此证")
                .setFont(font)
                .setFontSize(22)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.LEFT);

        content3.setFixedPosition(1, xContent, yContent - 150, 450); // 调整 y 坐标以确保换行
        document.add(content3);

        // 签发单位和日期：距离左侧300像素，距离底部50像素；日期：距离右侧100像素，距离底部50像素
        Paragraph issuerAndDate = new Paragraph()
                .add("签发单位：xxx有限公司");
        issuerAndDate.setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.LEFT);
        float yIssuerAndDate = 50; // 距离底部50像素
        issuerAndDate.setFixedPosition(1, 300, yIssuerAndDate, 300);
        document.add(issuerAndDate);
        // 签发单位位置
        Paragraph dateOnly = new Paragraph("日期：" + currentDate)
                .setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.RIGHT);
        dateOnly.setFixedPosition(1, 450, yIssuerAndDate, 300); // 日期位置

        document.add(dateOnly);


        document.close();
        InputStream targetStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());


        return new InputStreamResource(targetStream);
    }
}