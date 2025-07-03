import java.util.*;
import java.io.*;

public class parser {

    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "report.txt";

        Map<String, Integer> accTotal = new HashMap<>();
        Map<String, Integer> typeTotal = new HashMap<>();
        Map<String, Integer> dateTotal = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() < 74) {
                    System.err.println("Skipping invalid line (too short): " + line);
                    continue;
                }
                String accNum = line.substring(0, 10).trim();
                String aNum = line.substring(10, 25).trim();
                String bNum = line.substring(25, 40).trim();
                String startTime = line.substring(40, 54).trim();
                String endTime = line.substring(54, 68).trim();
                String callType = line.substring(68, 69).trim();
                int cost = Integer.parseInt(line.substring(69, 74).trim());

                // Total per account
                accTotal.put(accNum, accTotal.getOrDefault(accNum, 0) + cost);

                // Total per call type
                typeTotal.put(callType, typeTotal.getOrDefault(callType, 0) + cost);

                // Total per day
                String date = startTime.substring(0, 8);
                dateTotal.put(date, dateTotal.getOrDefault(date, 0) + cost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("=== Total Cost per Account ===\n");
            for (Map.Entry<String, Integer> entry: accTotal.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(key + ": " + value + " sen\n");
            }

            writer.write("\n=== Total Cost per Call Type ===\n");
            for (Map.Entry<String, Integer> entry: typeTotal.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(key + ": " + value + " sen\n");
            }

            writer.write("\n=== Total Cost per Day ===\n");
            for (Map.Entry<String, Integer> entry: dateTotal.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(key + ": " + value + " sen\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Report written to " + outputFile);
    }

}
