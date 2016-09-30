package festsamacharservicesprivatelimited.festsamachar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by eruvaka on 17-09-2016.
 */
public class AddFesetFragment extends Fragment {

    public AddFesetFragment() {
        // Required empty public constructor
    }
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.add_fest, container, false);
        return v;
    }
}
