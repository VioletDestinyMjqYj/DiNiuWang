package com.gxweilan.diniuwang.wechatpay.auth;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * @author xy-peng
 */
public class WechatPayUploadHttpPost extends HttpPost {

    private final String meta;

    private WechatPayUploadHttpPost(URI uri, String meta) {
        super(uri);
        this.meta = meta;
    }

    public String getMeta() {
        return meta;
    }

    public static class Builder {

        private final URI uri;
        private String fileName;
        private String fileSha256;
        private InputStream fileInputStream;
        private ContentType fileContentType;

        public Builder(URI uri) {
            this.uri = uri;
        }

        public Builder withImage(String fileName, String fileSha256, InputStream inputStream) {
            this.fileName = fileName;
            this.fileSha256 = fileSha256;
            this.fileInputStream = inputStream;

            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType == null) {
                // guess this is a video uploading
                this.fileContentType = ContentType.APPLICATION_OCTET_STREAM;
            } else {
                this.fileContentType = ContentType.create(mimeType);
            }
            return this;
        }

        public WechatPayUploadHttpPost build() {
            if (fileName == null || fileSha256 == null || fileInputStream == null) {
                throw new IllegalArgumentException("缺少待上传图片文件信息");
            }

            if (uri == null) {
                throw new IllegalArgumentException("缺少上传图片接口URL");
            }

            String meta = String.format("{\"filename\":\"%s\",\"sha256\":\"%s\"}", fileName, fileSha256);
            WechatPayUploadHttpPost request = new WechatPayUploadHttpPost(uri, meta);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.RFC6532)
                    .addBinaryBody("file", fileInputStream, fileContentType, fileName)
                    .addTextBody("meta", meta, APPLICATION_JSON);

            request.setEntity(entityBuilder.build());
            request.addHeader(ACCEPT, APPLICATION_JSON.toString());

            return request;
        }
    }
}
