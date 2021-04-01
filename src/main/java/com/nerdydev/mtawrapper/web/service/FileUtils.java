package com.nerdydev.mtawrapper.web.service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtils{


    public static File[] dirListByAscendingDate(File folder) {
        if (!folder.isDirectory()) {
            return null;
        }
        File files[] = folder.listFiles();
        Arrays.sort( files, new Comparator()
        {
            public int compare(final Object o1, final Object o2) {
                return new Long(((File)o1).lastModified()).compareTo
                        (new Long(((File) o2).lastModified()));
            }
        });
        return files;
    }

    public static File[] dirListByDescendingDate(File folder) {
        if (!folder.isDirectory()) {
            return null;
        }
        File files[] = folder.listFiles();
        Arrays.sort( files, new Comparator()
        {
            public int compare(final Object o1, final Object o2) {
                return new Long(((File)o2).lastModified()).compareTo
                        (new Long(((File) o1).lastModified()));
            }
        });
        return files;
    }
}
