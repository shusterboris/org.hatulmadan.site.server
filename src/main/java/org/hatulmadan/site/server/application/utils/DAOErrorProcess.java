package org.hatulmadan.site.server.application.utils;

public class DAOErrorProcess{
    public static String getErrorMessage(Exception e){
        String errMsg = "";
        String errClass = e.getClass().getCanonicalName();
        if (errClass.equals("NonTransientDataAccessException")){
            errMsg = "Неустранимая ошибка при работе с базой данных. Обратитесь в техническую поддержку";
        }else if (errClass.equals("ScriptException")){
            errMsg = "Неустранимая ошибка в запросе к базе данных. Обратитесь в техническую поддержку";
        }else if (errClass.equals("RecoverableDataAccessException")){
            errMsg = "Ошибка в запросе к базе данных. Повторите еще раз или обратитесь в техническую поддержку";
        }else if (errClass.equals("TransientDataAccessException")){
            errMsg = "Ошибка при работе с базой данных. Попробуйте еще раз";
        }else if (errClass.equals("DataAccessException")){
            errMsg = "Ошибка при работе с базой данных. Обратитесь в техническую поддержку";
        }else{
            errMsg = "Непредвиденная ошибка при работе с базой данных. Обратитесь в техническую поддержку";
        }
        return errMsg;
    }
}
