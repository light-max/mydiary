package com.lfq.diary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EncryptionTools {
    /**
     * 进行简单的加密 | 解密
     * @param file
     */
    public static void encryption(File file){
        try {
            File temp = new File(file.getPath()+".temp");
            InputStream in = new FileInputStream(file);
            OutputStream out = new FileOutputStream(temp);
            int value;
            while ((value=in.read())!=-1){
                value = ~value;
                out.write(value);
            }
            in.close();
            out.flush();
            out.close();
            file.delete();
            temp.renameTo(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
