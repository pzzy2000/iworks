package cn.oxo.iworks.networks.hclient;

import java.io.File;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

public class PostUploadFileRequest extends PostGetRequest {

      private File file;

      public PostUploadFileRequest(CharEncoded charEncoded, File file) {
            super(charEncoded);
            this.file = file;

      }

      public PostUploadFileRequest(File file) {
            this(CharEncoded.UTF8, file);
      }

      public File getFile() {
            return file;
      }

}
