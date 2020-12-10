package Utilities;

import java.io.*;

public class LogInProcess {
    public String isValidLogin(String email, String password){
        File fileName = new File("MailServer/All_Users.txt");
        BufferedReader checker = null;
        try {
            checker = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String emailReed;
        String passwordRead;
        String username;
        try {
            while((emailReed = checker.readLine()) != null) {
                if(emailReed.equals(email)) {
                    passwordRead = checker.readLine();
                    if(passwordRead.equals(password)){
                        username = checker.readLine();
                        return username;
                    }else{
                        checker.readLine();
                        continue;
                    }
                }
                checker.readLine();
                checker.readLine();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
