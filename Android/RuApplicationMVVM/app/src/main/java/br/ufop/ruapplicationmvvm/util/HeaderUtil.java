package br.ufop.ruapplicationmvvm.util;

public class HeaderUtil {
    public static String headerTitle(Integer num) {
        if (num == Constants.ALMOCO) {
            return "Almoço";
        }
        return "Jantar";
    }


    public static boolean isAlmoco(Integer num) {
        return num == Constants.ALMOCO;
    }


}
