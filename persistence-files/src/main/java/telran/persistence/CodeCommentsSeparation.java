package telran.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeCommentsSeparation {
    public static void main(String[] args) {
        //data from args[0] split to two files: args[1], args[2]
        //for sake of simplicity comments may be only on one line, like comments at this file
        // /* */ cannot be
        // code ...// comment .... cannot be
            //However // may be not only at beginning of line, like this
        //args[0] - path to file containing code and comments 
        //args[1] - path to file for placing only code
        //args[2] - path to file for placing only comments
        
        if (args.length < 3) {
            System.out.println("Usage: java CodeCommentsSeparation <inputFile> <codeOutputFile> <commentsOutputFile>");
            return;
        }

        String inputFile = args[0];
        String codeOutputFile = args[1];
        String commentsOutputFile = args[2];

        boolean success = separateCodeAndComments(inputFile, codeOutputFile, commentsOutputFile);
        if (!success) {
            System.err.println("An error occurred during file processing.");
        }
    }

    private static boolean separateCodeAndComments(String inputFile, String codeOutputFile, String commentsOutputFile) {
        boolean result = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter codeWriter = new PrintWriter(new FileWriter(codeOutputFile));
             PrintWriter commentsWriter = new PrintWriter(new FileWriter(commentsOutputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, codeWriter, commentsWriter);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            result = false;
        }
        return result;
    }

    private static void processLine(String line, PrintWriter codeWriter, PrintWriter commentsWriter) {
        if (line.trim().startsWith("//")) {
            commentsWriter.println(line);
        } else {
            int commentIndex = line.indexOf("//");
            if (commentIndex != -1) {
                codeWriter.println(line.substring(0, commentIndex).trim());
                commentsWriter.println(line.substring(commentIndex).trim());
            } else {
                codeWriter.println(line);
            }
        }
    }
}