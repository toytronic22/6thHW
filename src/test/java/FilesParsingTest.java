import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.Test;

public class FilesParsingTest {

    ClassLoader cl = FilesParsingTest.class.getClassLoader();

    @Test
    void readFileFromArchive() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("example/anykey.zip");
                ZipInputStream zis = new ZipInputStream(resource, Charset.forName("windows-1251"));
        ) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().endsWith(".csv")) {

                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(1)[1]).contains("QA");

                } else if (entry.getName().contains(".xlsx")) {

                    XLS content = new XLS(zis);
                    assertThat(
                            content.excel.getSheetAt(0).getRow(0).getCell(0).
                                    getStringCellValue()).contains("Auth Service With Credentials");

                } else if (entry.getName().endsWith(".pdf")) {

                    PDF content = new PDF(zis);
                    assertThat(content.text)
                            .contains("Самоучитель Java с примерами и программами. 3-е издание.");

                }
            }
        }
    }
}