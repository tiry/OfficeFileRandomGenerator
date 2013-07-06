package org.nuxeo.docgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.nuxeo.docgen.random.RandomTextGenerator;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class DocGen {

    RandomTextGenerator rtg ;

    public DocGen() throws Exception {
        rtg = new RandomTextGenerator();
        rtg.prefilCache();
    }
    public void generateFile(File template, String outputFile, Map<String, String> params) throws Exception {

        String inputFile = template.getName();
        if (outputFile==null) {
            int idx = inputFile.lastIndexOf(".");
            outputFile = inputFile.substring(0, idx) + "_" + Math.round(Math.random()*10000) + "_" + inputFile.substring(idx);
        }

        File output = new File(outputFile);
        output.createNewFile();

        generateFile(template, output, params);
        System.out.println("generated " + output.getAbsolutePath());
    }

    protected  void generateFile(File template, File output, Map<String, String> params) throws Exception {

        InputStream in = new FileInputStream(template);

        // load the template
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
                in, TemplateEngineKind.Freemarker,
                false);

        IContext context = report.createContext();
        for (String key : params.keySet()) {
            context.put(key, params.get(key));
        }

        context.put("random", System.currentTimeMillis());
        context.put("randomText", rtg.getRandomParagraph());

        FileOutputStream out = new FileOutputStream(output);
        report.process(context, out);
        out.close();
    }
}
