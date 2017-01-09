package mahmoud.footballcalendar.Classes;

import java.util.Random;

/**
 * Created by Mahmoud on 0001 9/1/2016.
 */
public class API {
    public String[] api_string={"92690348dbfc46b59846305590caf246"
            ,"92690348dbfc46b59846305590caf246"
            ,"92690348dbfc46b59846305590caf246"
            ,"92690348dbfc46b59846305590caf246"
            ,"92690348dbfc46b59846305590caf246"};


    public String random_api(){
        Random random = new Random();
        int i = random.nextInt(5 - 0) + 0;
        return this.api_string[i];
    }
}
