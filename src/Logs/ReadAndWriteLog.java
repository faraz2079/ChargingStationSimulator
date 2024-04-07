package Logs;

import java.io.*;
import java.time.LocalDateTime;

public class ReadAndWriteLog {
       static String  fileName = ".\\src\\Logs\\logs.txt";
    public static  void writeLog(String message){
        String log  =  "at : "+ LocalDateTime.now()  + "   log  ->[ {  "+message+ "} ]"   ;
        System.out.println(log);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

            writer.append(log);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println("Error : " + e.getMessage());
        }
    }
    public static void readLog() throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            ReadAndWriteLog.writeLog(line);
        }
        bufferedReader.close();

    }

    public static void deleteLog() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {

            writer.write(" ");
            writer.newLine();
            writer.close();
            Thread.sleep(1000);
            System.out.println("log file is empty now ... ");
        } catch (IOException e) {
            System.err.println("Error : " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
