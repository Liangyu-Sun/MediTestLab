package com.softwaremanage.meditestlab.service.train_module;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Standard;
import com.softwaremanage.meditestlab.repository.exam_module.AssessmentRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.ProjectRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.StandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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


    public Resource generateAssessmentPdf(Integer assessmentId) throws IOException {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID"));

        //System.out.print(assessment.completed);
        if (!assessment.isCompleted()) {
            throw new IllegalStateException("Assessment is not completed");
        }

        Project project = projectRepository.findByPId(assessment.getProjectId());
        Standard standard = standardRepository.findBySId(project.getsId());

        // 从资源文件中读取背景图像
        ClassPathResource resource = new ClassPathResource("images/background.jpg");
        InputStream imageStream = resource.getInputStream();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        // 设置字体
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);

        // 添加背景图像
        ImageData imageData = ImageDataFactory.create(imageStream.readAllBytes());
        PdfImageXObject imageXObject = new PdfImageXObject(imageData);
        PdfFormXObject formXObject = new PdfFormXObject(imageXObject.getPdfObject());
        Image img = new Image(formXObject);
        img.setWidth(500); // 设置图像宽度
        img.setHeight(350); // 设置图像高度
        document.showTextAligned(new Paragraph().add(img), 300, 400, 1, TextAlignment.CENTER, null, 0);

        // 添加标题
        Paragraph title = new Paragraph("考核证书")
                .setFont(font)
                .setFontSize(40)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // 添加证书编号
        String certificateNumber = "证书编号：" + (2024100000L + assessment.getAssessmentId());
        Paragraph certificateNumberParagraph = new Paragraph(certificateNumber)
                .setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(certificateNumberParagraph);

        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        String currentDate = dateFormat.format(new Date());

        // 添加内容
        Paragraph content = new Paragraph()
                .add("兹证明 ")
                .add(new Paragraph(assessment.getInspectorId() + " 先生/女士：\n")
                        .setFont(font)
                        .setFontSize(16)
                        .setFontColor(ColorConstants.BLACK))
                .add("于 " + currentDate + " 完成 " + standard.getsName() + " ， " + project.getpNum() + " ， " + project.getpName() + " 项目考核，获得该项目检测授权。\n")
                .add("特发此证\n");
        content.setFont(font)
                .setFontSize(16)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(content);

        // 添加签发单位和日期
        Paragraph issuerAndDate = new Paragraph()
                .add("签发单位：xxx有限公司")
                .add("\n日期：" + currentDate);
        issuerAndDate.setFont(font)
                .setFontSize(14)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(issuerAndDate);

        document.close();

        InputStream targetStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());


        return new InputStreamResource(targetStream);
    }
}