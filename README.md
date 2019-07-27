# 2019Summer

暑假帮助孟老师做一些工作，用到一些自己以前未掌握的内容，现上传

## pictureRename

- 需求：对一些具有类似命名的图片进行重命名

- 因为自己一直没有掌握对图片的读取和写入，特此尝试了一下

- 总结：

  - 读取的时候，先将图片的字节码存入流中

    ~~~java
    InputStream in = new FileInputStream(originalDirectoryPath + "\\" + fileName);
    data = new byte[in.available()];
    in.read(data);
    for (int j = 0; j < data.length; ++j) {
        if (data[j] < 0) {// 调整异常数据
            data[j] += 256;
        }
    }
    ~~~

    调整异常数据那里不是很懂，但是调试的时候确实会出现负值（？？）

  - 写入的时候，将流中的信息写入到文件中

    ~~~java
    OutputStream out = new FileOutputStream(newPath);
    out.write(data);
    out.flush();
    ~~~

  - **读取、写入完成后记得close()**

