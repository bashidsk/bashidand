package festsamacharservicesprivatelimited.festsamachar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by eruvaka on 17-09-2016.
 */
public class Fsp_Program extends Fragment {

    public Fsp_Program() {
        // Required empty public constructor
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.add_fest, container, false);
        return v;
    }
}
