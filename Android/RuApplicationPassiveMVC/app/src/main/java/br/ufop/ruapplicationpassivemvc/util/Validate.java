package br.ufop.ruapplicationpassivemvc.util;

import br.ufop.ruapplicationpassivemvc.model.entity.Notice;

import java.util.InputMismatchException;

public class Validate {

    public static boolean isCPF(String CPF) {
        //from https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char digito10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                digito10 = '0';
            else digito10 = (char) (r + 48);
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);


            return (digito10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean lenghtMin(String str, int num) {
        return str.length() >= num;
    }

    public static boolean isFormComplete(String title, int type, String content) {
        return !title.isEmpty() && type >= 0 && !content.isEmpty();
    }

    public static boolean wasFieldUpdate(String value, String newValue) {
        return !value.equals(newValue);
    }

    public static boolean formWasUpdated(String title, int type, String content, Notice notice) {
        if (title.equals(notice.getSubject()) && type == (notice.getType() + 1) && content.equals(notice.getContent())) {
            return false;
        }

        return isFormComplete(title, type, content);
    }

}
