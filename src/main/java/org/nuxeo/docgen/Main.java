package org.nuxeo.docgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.docgen.random.RandomTextGenerator;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class Main {

    protected static void help() {
        System.out.println("This command takes between 3 and 6 parameters");
        System.out.println("name : name of the schema to generate (required)");
        System.out.println("prefix : prefix of the schema to generate (required)");
        System.out.println("xsdfilelocation : location of the XSD file to parse (required)");
        System.out.println("fieldExtractionFile : file listing fields to extract to generate a de-normalized schema (optional, if empty de-normalization is not run)");
        System.out.println("subName : target name of the de-normalized schema (optional, defaults to sub + name)");
        System.out.println("subPrefix : target prefix of the de-normalized schema (optional, default to sub + prefix)");
        System.out.println("");
        System.out.println(" Example :");
        System.out.println(" mySchema myPrefix myXSD.xsd fields mySubSchema mySubPrefix");
    }

    public static void main(String args[]) throws IOException {

        String inputFile = null;
        String outputFile = null;

        Map<String, String> params = new HashMap<String, String>();


        if (args.length >= 1) {
            inputFile = args[0];
            for (int i = 1; i < args.length; i++) {
                if (args[i].contains("=")) {
                    String [] parts = args[i].split("=");
                    params.put(parts[0], parts[1]);
                } else if (outputFile==null) {
                    outputFile = args[i];
                }
            }
        } else {
            help();
            return;
        }

        try {
            DocGen gen = new DocGen();

            if (params.containsKey("loop")) {
                for (int i = 0; i < Integer.parseInt(params.get("loop")); i++) {
                    params.put("idx", ""+i);
                    File template = new File(inputFile);
                    gen.generateFile(template, outputFile, params);
                }
            } else {
                File template = new File(inputFile);
                gen.generateFile(template, outputFile, params);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


}
