import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;

public interface Requestable {
    public void requestCallBack(int tag, ArrayList<MatjiData> data);
}