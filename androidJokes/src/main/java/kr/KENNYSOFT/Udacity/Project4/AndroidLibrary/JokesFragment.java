package kr.KENNYSOFT.Udacity.Project4.AndroidLibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JokesFragment extends Fragment
{


	public JokesFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View returnView=inflater.inflate(R.layout.fragment_jokes,container,false);
		((TextView)returnView.findViewById(R.id.jokes)).setText(getActivity().getIntent().getExtras().getString("jokes"));
		return returnView;
	}

}
