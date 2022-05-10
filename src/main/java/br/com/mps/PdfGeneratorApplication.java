package br.com.mps;

import br.com.mps.generator.PdfGenerator;
import com.itextpdf.text.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@SpringBootApplication
public class PdfGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfGeneratorApplication.class, args);
		try {
			test();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static void test() throws IOException {
		StringBuilder builder = new StringBuilder();
		String title = "";
		String lines;

		try(BufferedReader reader = new BufferedReader(
				new FileReader("/dados/tmp/Redução de Jornada.txt"))) {
			while ((lines = reader.readLine()) != null) {
				if (title.isBlank()) {
					title = lines + "\n\r";
				} else {
					builder.append(lines).append("\n\r");
				}
			}
		}
		lines = builder.toString();


		try {
			PdfGenerator pdfGenerator = new PdfGenerator();
			InputStream in = pdfGenerator.createDocument()
					.setFontFamily(FontFactory.TIMES_BOLD)
					.setFontSize(18)
					.write(title, Element.ALIGN_CENTER)
					.setFontFamily(FontFactory.TIMES_ROMAN)
					.setFontSize(12)
					.write(lines, Element.ALIGN_LEFT)
					.newPage()
					.write("CPF: "+ "12345678901")
					.write("Matrícula: " + "2100123456")
					.write("Nome: " + "João da Silva")
					.write("Token: " + "A1B2C3D4E5F6G7H8")
					.getDocument();
			FileOutputStream fileOut = new FileOutputStream("/dados/tmp/Exemplo Gerador PDF - Typeform.pdf");
			fileOut.write(in.readAllBytes());
			fileOut.close();
		} catch (DocumentException | IOException e) {
			log.error(e.getMessage());
		}
	}
}
