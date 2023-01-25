import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.ImageJson;
import org.junit.jupiter.api.Test;

public class JsonParseTest {

    ClassLoader cl = JsonParseTest.class.getClassLoader();


    @Test
    void jsonParse() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (
                InputStream resource = cl.getResourceAsStream("example/json.json");
                InputStreamReader reader = new InputStreamReader(resource);
        ) {
            ImageJson imageJson = mapper.readValue(reader, ImageJson.class);
            assertThat(imageJson.image.width).isEqualTo(800);
            assertThat(imageJson.image.height).isEqualTo(600);
            assertThat(imageJson.image.title).isEqualTo("View from 15th Floor");
            assertThat(imageJson.image.visible).isTrue();
            assertThat(imageJson.image.ids).contains(116, 943, 234, 38793);


        }

    }
}