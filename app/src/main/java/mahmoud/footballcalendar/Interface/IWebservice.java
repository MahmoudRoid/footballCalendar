package mahmoud.footballcalendar.Interface;

/**
 * Created by Mahmoud on 0026 12/26/2015.
 */
public interface IWebservice {

    void getResult(Object result,String Tag) throws Exception;

    void getError(String ErrorCodeTitle)throws Exception;
}
