import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nuxeo.docgen.DocGen;

public class TestGen {

    @Test
    public void testGen() throws Exception {

        URL url = this.getClass().getClassLoader().getResource(
                "testDoc.odt");
        File docxFile = new File(url.toURI());
        assertNotNull(docxFile);

        Map<String, String> params = new HashMap<String, String>();

        params.put("Var1", "Hello");
        DocGen gen = new DocGen();
        gen.generateFile(docxFile, null, params);

    }
}
