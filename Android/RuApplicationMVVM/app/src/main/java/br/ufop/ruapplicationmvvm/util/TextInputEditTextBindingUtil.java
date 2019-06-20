package br.ufop.ruapplicationmvvm.util;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

public class TextInputEditTextBindingUtil {
    @BindingAdapter({"app:validation", "app:errorMsg"})
    public static void setErrorEnable(TextInputLayout textInputLayout, StringRule stringRule,
                                      final String errorMsg) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                textInputLayout
                        .setErrorEnabled(stringRule.validate(textInputLayout.getEditText().getText()));
                if (stringRule.validate(textInputLayout.getEditText().getText())) {
                    textInputLayout.setError(errorMsg);
                } else {
                    textInputLayout.setError(null);
                }
            }
        });
        textInputLayout
                .setErrorEnabled(stringRule.validate(textInputLayout.getEditText().getText()));
        if (stringRule.validate(textInputLayout.getEditText().getText())) {
            textInputLayout.setError(errorMsg);
        } else {
            textInputLayout.setError(null);
        }
    }

    public interface StringRule {

        boolean validate(Editable s);
    }

    public static class Rule {

        public static StringRule NOT_EMPTY_RULE = s -> Validate.isEmpty(s.toString());
        public static StringRule EMAIL_RULE = s -> !Validate.isEmail(s.toString());
        public static StringRule PASSWORD_RULE = s -> !Validate.isPassword(s.toString());
        public static StringRule CPF_RULE = s -> !Validate.isCPF(s.toString());
    }

}