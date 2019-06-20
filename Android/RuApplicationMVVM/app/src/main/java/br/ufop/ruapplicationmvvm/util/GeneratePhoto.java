package br.ufop.ruapplicationmvvm.util;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GeneratePhoto {

    public static MultipartBody.Part photo(String filePath){
        File file = new File(filePath);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
      return  MultipartBody.Part.createFormData("image", file.getName(), mFile);
    }
}
