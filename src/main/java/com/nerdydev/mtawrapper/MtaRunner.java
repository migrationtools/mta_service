package com.nerdydev.mtawrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Component
public class MtaRunner implements CommandLineRunner {

    @Value("${mta.bin.path}")
    public String mtaBinPath;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Override
    public void run(String... args) throws Exception {
        String fileName = mtaBinPath + "rules.txt";
        System.out.println(fileName);
        String command = mtaBinPath + "mta-cli.bat --listTargetTechnologies > " + fileName;
        System.out.println(command);
        try {

            System.out.println("command = " + command);
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
