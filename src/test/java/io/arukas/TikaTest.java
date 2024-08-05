package io.arukas;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA. <br/>
 *
 * @author NiuYuxian <br/>
 * @since 2023-06-25 15:20:45 <br/>
 */
public class TikaTest {
    private InputStream fileInputStream(String filePath) {
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }

    @Test
    public void test01() throws IOException {
        InputStream file = fileInputStream("01.xlsx");
        Tika tika = new Tika();
        System.out.println(tika.detect(file));

    }

    @Test
    public void test02() throws IOException{
        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();

        String fileName = "a.docx";
        TikaInputStream stream = TikaInputStream.get(fileInputStream(fileName));

        Metadata metadata = new Metadata();
        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, fileName);
        MediaType mediaType = detector.detect(stream, metadata);

        System.out.println(mediaType.getType());
        System.out.println(mediaType);
    }
}
