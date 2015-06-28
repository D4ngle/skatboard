package d4ngle_studios.com.skatboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * This is the Entry Page
 */
public class EntryPage extends AppCompatActivity {

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

        /**
         * catch unexpected error
         */
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

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
        @Bind(R.id.playerPager)
        ViewPager playerPager;

        @Bind(R.id.additional_info_more)
        TextView toggleAdditionalInfoMore;
        @Bind(R.id.additionalGameInfoView)
        GridLayout additionalGameInfo;

        @Bind(R.id.editTextPoints)
        EditText resultTextfield;
        @Bind(R.id.buttonCompute)
        Button buttonCompute;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ViewPager pager = null;
        private View rootView;

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

        /*
         * Inspired by
         * https://gist.github.com/8cbe094bb7a783e37ad1
         */
        private class SampleAdapter extends PagerAdapter {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View page = getActivity().getLayoutInflater().inflate(R.layout.page, container, false);

                ToggleButton playerButton = (ToggleButton) page.findViewById(R.id.playerToggleButton);
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/icomoon.ttf");
                playerButton.setTypeface(font);

                playerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateButtonGroup(v);
                        updateGameValue();
                    }
                });

                TextView tv = (TextView) page.findViewById(R.id.text);

                final String msg = String.format(getString(R.string.item), position + 1);

                tv.setText(msg);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("HALLO: " + msg);
                    }
                });

                container.addView(page);

                return (page);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return (4);
            }

            @Override
            public float getPageWidth(int position) {
                return (0.33f);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_entry_page, container, false);
            ButterKnife.bind(this, rootView);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

//            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/icomoon.ttf");

            //TODO Depending on ScreenSize: !
            additionalGameInfo.setVisibility(View.GONE);

            pager = playerPager;
            pager.setAdapter(new SampleAdapter());
            pager.setOffscreenPageLimit(9);

            buttonCompute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGameValue();
                }
            });
        }

        @OnClick({R.id.additional_info_less})
        public void hideAdditionalInfo() {
            additionalGameInfo.setVisibility(View.GONE);
            toggleAdditionalInfoMore.setVisibility(View.VISIBLE);
        }

        @OnClick({R.id.additional_info_more})
        public void showAdditionalInfo() {
            additionalGameInfo.setVisibility(View.VISIBLE);
            toggleAdditionalInfoMore.setVisibility(View.GONE);
        }

        @OnClick({R.id.numberJacks1, R.id.numberJacks2, R.id.numberJacks3, R.id.numberJacks4,
                R.id.valueSuitsDiamonds, R.id.valueSuitsHearts, R.id.valueSuitsSpades, R.id.valueSuitsClubs})
        public void updateView(View v) {
            updateButtonGroup(v);
            updateGameValue();
        }

        @OnCheckedChanged({R.id.checkBoxHand, R.id.checkBoxOuvert, R.id.checkBoxSchneider, R.id.checkBoxSchneiderAngesagt, R.id.checkBoxSchwarz, R.id.checkBoxSchwarzAngesagt})
        public void updateGameValue() {
            int happyPlayers = getHappyPlayers();
            int jacksValue = getMathValue(getCheckedButtonForTag("numberJacksButton"));
            int gameColorValue = getMathValue(getCheckedButtonForTag("gameValueButton"));

            if (jacksValue != 0 && gameColorValue != 0) {
                int modifier = 1 + getAdditionalGameInfo();
                int result = (jacksValue + modifier) * gameColorValue;
                if (happyPlayers > 1) {
                    result = result * 2;
                }
                resultTextfield.setText("" + result);
            }
        }

        private int getAdditionalGameInfo() {
            int result = 0;
            for (CheckBox box : getCheckBoxesByTag((ViewGroup) rootView, "modifierCheckbox")) {
                if (box.isChecked()) {
                    result++;
                }
            }
            return result;
        }

        private int getHappyPlayers() {
            int count = 0;
            for (ToggleButton button : getToggleButtonsByTag((ViewGroup) rootView, "player")) {
                if (button.isChecked()) {
                    count++;
                }
            }
            return count;
        }

        private int getMathValue(ToggleButton button) {
            if (null == button) {
                return 0;
            }
            switch (button.getId()) {
                case R.id.numberJacks1:
                    return 1;
                case R.id.numberJacks2:
                    return 2;
                case R.id.numberJacks3:
                    return 3;
                case R.id.numberJacks4:
                    return 4;
                case R.id.valueSuitsDiamonds:
                    return 9;
                case R.id.valueSuitsHearts:
                    return 10;
                case R.id.valueSuitsSpades:
                    return 11;
                case R.id.valueSuitsClubs:
                    return 12;
                case R.id.valueGrand:
                    return 24;
            }
            return 0;
        }

        private static boolean isVisiblePlayer(LinearLayout playersLayout, View view) {
            return true;
        }

        private void updateButtonGroup(View v) {
            ArrayList<ToggleButton> buttonList = getToggleButtonsByTag((ViewGroup) rootView, (String) v.getTag());
            for (ToggleButton b : buttonList) {
                if (!b.equals(v)) {
                    if (v.getTag().equals("player")) {
                        //Player Button was checked before
                        if (!((ToggleButton) v).isChecked()) {
                            b.setChecked(true);
                        } else {
                            b.setChecked(false);
                        }
                    } else {
                        b.setChecked(false);
                    }
                }
            }
        }

        // from http://stackoverflow.com/a/16262479/3727256
        private static ArrayList<View> getViewsByTag(ViewGroup root, String tag) {
            ArrayList<View> views = new ArrayList<>();
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

        private static ArrayList<ToggleButton> getToggleButtonsByTag(ViewGroup root, String tag) {
            ArrayList<ToggleButton> result = new ArrayList<>();

            for (View view : getViewsByTag(root, tag)) {
                if (view instanceof ToggleButton) {
                    result.add((ToggleButton) view);
                }
            }
            return result;
        }

        private static ArrayList<CheckBox> getCheckBoxesByTag(ViewGroup root, String tag) {
            ArrayList<CheckBox> result = new ArrayList<>();

            for (View view : getViewsByTag(root, tag)) {
                if (view instanceof CheckBox) {
                    result.add((CheckBox) view);
                }
            }
            return result;
        }

        private ToggleButton getCheckedButtonForTag(String tag) {
            List<ToggleButton> buttonList = getToggleButtonsByTag((ViewGroup) rootView, tag);
            for (ToggleButton b : buttonList) {
                if (b.isChecked()) {
                    return b;
                }
            }
            return null;
        }
    }
}
