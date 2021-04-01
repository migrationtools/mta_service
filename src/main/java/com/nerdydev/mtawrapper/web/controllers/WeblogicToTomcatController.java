package com.nerdydev.mtawrapper.web.controllers;

import com.nerdydev.mtawrapper.web.dto.CheckIndexFileDto;
import com.nerdydev.mtawrapper.web.dto.WtcFileNamesDto;
import com.nerdydev.mtawrapper.web.dto.WtcFolderNameDto;
import com.nerdydev.mtawrapper.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class WeblogicToTomcatController {

    @Autowired
    FileService fileService;

    @Value("${mta.bin.path}")
    public String mtaBinPath;

    @GetMapping("/getRules")
    public List<String> getRules() throws IOException, InterruptedException {
        List<String> rulesList = fileService.getMtaRules();
        return rulesList;
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public WtcFolderNameDto uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes,
                                       @RequestParam String target) {
        WtcFolderNameDto wtcFolderNameDto = fileService.uploadFile(file, target);
        return wtcFolderNameDto;
    }

    @GetMapping("/findIndexFile")
    public CheckIndexFileDto getIsFoldersPresent(@RequestParam String folderName) {
        CheckIndexFileDto checkIndexFileDto = fileService.isFolderPresent(folderName);
        return checkIndexFileDto;
    }

    @GetMapping(value = "/consoleStreaming")
    public SseEmitter getStreamLogs(@RequestParam String logFile) {
        String logFilePath = mtaBinPath + logFile + ".log";
        SseEmitter emitter = new SseEmitter();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            int streamCounter = 0;
            long lastKnownPosition = 0;
            boolean flag = true;
            while (flag) {
                try {
                    RandomAccessFile readWriteFileAccess = new RandomAccessFile(logFilePath, "r");
                    readWriteFileAccess.seek(lastKnownPosition);
                    String streamLine = null;
                    while ((streamLine = readWriteFileAccess.readLine()) != null ) {
                        emitter.send((streamLine + "\n").getBytes());
                        if ((streamLine.contains("Access it at this URL:"))) {
                            flag = false;
                            emitter.complete();
                            break;
                        }
                        streamCounter++;
                    }
                    lastKnownPosition = readWriteFileAccess.getFilePointer();
                    readWriteFileAccess.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
        return emitter;
    }

    @GetMapping("/directoryListByAscendingDate")
    public File[] getDirectoryListByAscendingDate() {
        File[] files = fileService.getDirectoryListByAscendingDate();
        for (File file : files) {
            System.out.println("Directory List by Ascending Date Starts ::: ");
            System.out.println(file.getName() + " "
                    + new Date(file.lastModified()));
        }
        System.out.println("Directory List by Ascending Date Ends ::: ");
        return files;
    }

    @GetMapping("/directoryListByDescendingDate")
    public List<WtcFileNamesDto> getDirectoryListByDescendingDate() {
        File[] files = fileService.getDirectoryListByDescendingDate();
        List<WtcFileNamesDto> wtcFileNamesDtos = new ArrayList<>();
        for (File file : files) {
            wtcFileNamesDtos.add(new WtcFileNamesDto(file.getName(), new Date(file.lastModified())));
        }
        return wtcFileNamesDtos;
    }

}
