package br.ufop.ruapplicationmvp.util;

import java.io.IOException;
import java.lang.annotation.Annotation;

import br.ufop.ruapplicationmvp.model.response.ApiError;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ConvertError {

    public static ApiError converErrors(ResponseBody response) {
        Converter<ResponseBody, ApiError> converter = RetrofitBuilder.getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError apiError = null;

        try {
            apiError = converter.convert(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiError;
    }
}
