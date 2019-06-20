package br.ufop.ruapplicationmvvm.util;

public class ReplyAction {

    public static String replyActionSucess(int option, String name) {
        switch (option) {
            case Constants.INSERT:
                return name + Constants.INSERT_SUCESS_MESSAGE;


            case Constants.UPDATE:
                return name + Constants.UPDATE_SUCESS_MESSAGE;


            case Constants.DELETE:
                return name + Constants.DELETE_SUCESS_MESSAGE;

        }
        return "";
    }

    public static String replyActionError(int option, String name) {
        switch (option) {
            case Constants.INSERT:
                return name + Constants.INSERT_ERROR_MESSAGE;


            case Constants.UPDATE:
                return name + Constants.UPDATE_ERROR_MESSAGE;


            case Constants.DELETE:
                return name + Constants.DELETE_ERROR_MESSAGE;

        }
        return "";
    }


}
