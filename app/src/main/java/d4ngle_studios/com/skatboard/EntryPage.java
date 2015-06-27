package d4ngle_studios.com.skatboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This is the Entry Page
 */
public class EntryPage extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_page);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_entry_page, container, false);

            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/icomoon.ttf");

            Button buttonValueSuitsDiamonds = (Button)rootView.findViewById(R.id.valueSuitsDiamonds);
            Button buttonValueSuitsHearts = (Button)rootView.findViewById(R.id.valueSuitsHearts);
            Button buttonValueSuitsSpades = (Button)rootView.findViewById(R.id.valueSuitsSpades);
            Button buttonValueSuitsClubs = (Button)rootView.findViewById(R.id.valueSuitsClubs );

            ImageButton buttonValueSuitsGrand = (ImageButton)rootView.findViewById(R.id.valueGrand );

            Button buttonPlayer1 = (Button)rootView.findViewById(R.id.imageButtonPlayer1 );
            Button buttonPlayer2 = (Button)rootView.findViewById(R.id.imageButtonPlayer2 );
            Button buttonPlayer3 = (Button)rootView.findViewById(R.id.imageButtonPlayer3 );
            Button buttonPlayer4 = (Button)rootView.findViewById(R.id.imageButtonPlayer4 );

            buttonValueSuitsDiamonds.setTypeface(font);
            buttonValueSuitsHearts.setTypeface(font);
            buttonValueSuitsSpades.setTypeface(font);
            buttonValueSuitsClubs.setTypeface(font);

            buttonPlayer1.setTypeface(font);
            buttonPlayer2.setTypeface(font);
            buttonPlayer3.setTypeface(font);
            buttonPlayer4.setTypeface(font);

            addPlayerOnClickListeners(Arrays.asList(buttonPlayer1, buttonPlayer2, buttonPlayer3, buttonPlayer4));

            return rootView;
        }

        private void addPlayerOnClickListeners(final List<Button> buttons) {
            for (final Button b : buttons) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View clickedButton) {
                        changePlayerIcons((Button) clickedButton, buttons, getActivity().getString(R.string.icon_player_won));
                    }
                });
            }
        }
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

    private static boolean isVisiblePlayer(LinearLayout playersLayout, View view) {
        Rect scrollBounds = new Rect();
        playersLayout.getHitRect(scrollBounds);

        return view.getLocalVisibleRect(scrollBounds);
    }

    private static void changePlayerIcons(Button clickedButton, List<Button> allButtons, String winString) {

        if (clickedButton.getText().equals(winString)) {
            clickedButton.setText(R.string.icon_player_lost);
            for (Button button : allButtons) {
                if (isVisiblePlayer((LinearLayout) ((View) clickedButton.getParent()).findViewById(R.id.playersLayout), button)) {
                    if (!button.equals(clickedButton)) {
                        button.setText(R.string.icon_player_won);
                    }
                }
            }
        } else {
            clickedButton.setText(R.string.icon_player_won);
            for (Button button : allButtons) {
                if (isVisiblePlayer((LinearLayout) ((View) clickedButton.getParent()).findViewById(R.id.playersLayout), button)) {
                    if (!button.equals(clickedButton)) {
                        button.setText(R.string.icon_player_lost);
                    }
                }
            }
        }
    }

}
