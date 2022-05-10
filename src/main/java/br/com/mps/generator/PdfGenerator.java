package br.com.mps.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class PdfGenerator {

    Document document;
    ByteArrayOutputStream outputStream;

    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

    public PdfGenerator() throws DocumentException {
        this.document = new Document();
        this.outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        log.info("PdfGenerator initialized");
    }

    public PdfGenerator createDocument() {
        if (!document.isOpen()) {
            document.open();
            log.info("Document opened");
        } else {
            log.warn("Document is already open");
        }
        return this;
    }

    public PdfGenerator newPage() {
        document.newPage();
        return this;
    }

    public PdfGenerator write(String pageText) throws DocumentException {
        Paragraph paragraph = new Paragraph(pageText, font);
        return writeElement(pageText, paragraph);
    }

    public PdfGenerator write(String elementText, int alignment) throws DocumentException {
        Paragraph paragraph = new Paragraph(elementText, font);
        paragraph.setAlignment(alignment);
        return writeElement(elementText, paragraph);
    }

    private PdfGenerator writeElement(String elementText, Paragraph paragraph) throws DocumentException {
        if (document.isOpen()) {
            document.add(paragraph);
            return this;
        } else {
            throw new DocumentException("Document is not open");
        }
    }

    public ByteArrayInputStream getDocument() {
        document.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public PdfGenerator setFontFamily(String fontname) {
        this.font.setFamily(fontname);
        return this;
    }

    public PdfGenerator setFontSize(int size) {
        this.font.setSize(size);
        return this;
    }

    public PdfGenerator setFontColor(BaseColor color) {
        this.font.setColor(color);
        return this;
    }

}

