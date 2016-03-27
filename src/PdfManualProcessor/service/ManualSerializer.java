package PdfManualProcessor.service;

import PdfManualProcessor.Manual;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serializes and deserialize manuals.
 */
public class ManualSerializer {
    private static final Path RAW_DATA_FILE =Paths.get("src\\PdfManualProcessor\\res\\allManuals.txt");

    public static void saveManualsToFile(List<Manual> manuals) {
        StringWriter stringWriter = new StringWriter();
        for (Manual manual : manuals){
            stringWriter.write(manual.getId());
            stringWriter.write("\t");
            stringWriter.write(manual.getPdfUrl());
            stringWriter.write(System.lineSeparator());
        }

        try (FileWriter fileWriter = new FileWriter(RAW_DATA_FILE.toAbsolutePath().toFile())) {
            fileWriter.write(stringWriter.toString());
            stringWriter.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    /**
     * to be deleted after class is complete.
     */

    public static void main(String[] args) {
        List<Manual> manuals = new ArrayList<>();
        manuals.add(new Manual("work","pdf"));
        manuals.add(new Manual("work2","pdf2"));
        manuals.add(new Manual("work3","pdf3"));
        saveManualsToFile(manuals);
    }


    // TODO:  decide and implement methods
}