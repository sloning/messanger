package com.messenger.util;

import com.messenger.exception.BadRequestException;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class ImageUtility {

    private ImageUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new BadRequestException("Image can not be saved");
        }
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] tmp = new byte[4 * 1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new BadRequestException("Image can not be saved");
        }
    }
}
