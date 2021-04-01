package com.nerdydev.mtawrapper.web.service;

import com.nerdydev.mtawrapper.web.dto.CheckIndexFileDto;
import com.nerdydev.mtawrapper.web.dto.WtcFolderNameDto;
import com.nerdydev.mtawrapper.web.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Value("${mta.upload.ear.directory}")
    public String uploadDirectory;

    @Value("${mta.upload.ear.output}")
    public String outputDirectory;

    @Value("${mta.bin.path}")
    public String mtaBinPath;

    @Value("${jgit.src.checkout.dir}")
    public String jgitCheckoutDir;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");


    public List<String> getMtaRules() throws IOException, InterruptedException {
        String fileName = mtaBinPath + "rules.txt";
        List<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            line = line.replace("\t", "");
            lines.add(line);
        }
        lines = lines.subList(5, lines.size());
        bufferedReader.close();
        Thread.sleep(1000);
        return lines;
    }

    public WtcFolderNameDto uploadFile(MultipartFile file, String target) {
        String fileName = file.getOriginalFilename() + "-" + dateFormat.format(new Date()) + ".report";
        String finalOutdir = outputDirectory + fileName;

        try {
            Path copyLocation = Paths.get( uploadDirectory + File.separator
                    + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            String createOutputDirCommand = "mkdir " + finalOutdir;
            commandExecutor(fileName, createOutputDirCommand);
            String command = mtaBinPath + "mta-cli --input " + uploadDirectory + file.getOriginalFilename()
                    + " --output "
                    + finalOutdir + " --target " + target + " > " + mtaBinPath + fileName + ".log" ;
            commandExecutor(fileName, command);
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!");
        }
        WtcFolderNameDto wtcFolderNameDto = new WtcFolderNameDto(fileName);
        return wtcFolderNameDto;
    }

    public String commandExecutor(String sourceName, String command) {
        try {
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "command executed and output folder will be available at the following directory" + outputDirectory;
    }

    public CheckIndexFileDto isFolderPresent(String folderName) {
        File file = new File(outputDirectory + folderName + "./index.html");
        if (file.exists()) {
            return  new CheckIndexFileDto("index.html exists");
        } else {
            LOGGER.info("folder not found");
            return new CheckIndexFileDto();
        }
    }

    public File[] getDirectoryListByAscendingDate() {
        File files[] = FileUtils.dirListByAscendingDate(new File(outputDirectory));
        return files;
    }

    public File[] getDirectoryListByDescendingDate() {
        File files[] = FileUtils.dirListByDescendingDate(new File(outputDirectory));
        return files;
    }
}
