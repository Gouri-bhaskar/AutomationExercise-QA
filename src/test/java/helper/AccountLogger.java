package helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.*;
import java.time.Instant;

public class AccountLogger {
    private static final String LOG_PATH = "./src/test/resources/created_accounts.json";

    public static void logAccount(String name, String email) {
        try {
            JSONArray accounts;

            // If file exists, load existing accounts
            File file = new File(LOG_PATH);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(LOG_PATH)));
                accounts = new JSONArray(content);
            } else {
                accounts = new JSONArray();
            }

            JSONObject newAccount = new JSONObject();
            newAccount.put("name", name);
            newAccount.put("email", email);
            newAccount.put("createdAt", Instant.now().toString());

            accounts.put(newAccount);

            FileWriter fw = new FileWriter(LOG_PATH);
            fw.write(accounts.toString(2)); // pretty print
            fw.close();

        } catch (Exception e) {
            e.printStackTrace(); // Replace with logger if needed
        }
    }
}
