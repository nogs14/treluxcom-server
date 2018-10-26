/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treluxcom.dao;

import com.treluxcom.service.IFileTransfer;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author apple
 */
public class FileTransferImpl extends UnicastRemoteObject implements IFileTransfer {

    public FileTransferImpl() throws RemoteException {
    }
    private static String sep = File.separator;
    private static String PATH = "." + sep + "src" + sep + "main" + sep + "resources" + sep + "images" + sep;

    @Override
    public byte[] downloadFile(String fileName) {

        try {
            File file = new File(PATH + fileName);
            byte buffer[] = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(PATH + fileName));
            input.read(buffer, 0, buffer.length);
            input.close();
            System.out.println("good");
            return (buffer);
        } catch (Exception e) {
            System.out.println("FileImpl.getMessage() ");
            e.printStackTrace();
            return (null);

        }
    }

    public boolean copierServer(File source, File dest) {
        return false;
    }
}
