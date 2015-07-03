package d4ngle_studios.com.skatboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RegularEntryFragment extends Fragment {
    @Bind(R.id.playerPager)
    ViewPager playerPager;

    @Bind(R.id.additional_info_more)
    TextView toggleAdditionalInfoMore;
    @Bind(R.id.additional_info_less)
    TextView toggleAdditionalInfoLess;
    @Bind(R.id.additionalGameInfoView)
    GridLayout additionalGameInfo;

    @Bind(R.id.editTextPoints)
    EditText resultTextfield;
    @Bind(R.id.buttonCompute)
    Button buttonCompute;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;
    HashMap<Integer, ToggleButton> positionsMap = new HashMap<>();
    HashMap<String, ArrayList<ToggleButton>> allPlayerButtons = new HashMap<>();

    public static RegularEntryFragment newInstance(int sectionNumber) {
        RegularEntryFragment fragment = new RegularEntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public RegularEntryFragment() {
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

        additionalGameInfo.setVisibility(View.GONE);

        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(new SampleAdapter());
        playerPager.setAdapter(wrappedAdapter);
        playerPager.setOffscreenPageLimit(9);

        buttonCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGameValue();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.additional_info_less})
    public void hideAdditionalInfo() {
        int cx = (toggleAdditionalInfoLess.getLeft() + toggleAdditionalInfoLess.getRight()) / 2;
        int cy = (toggleAdditionalInfoLess.getTop() + toggleAdditionalInfoLess.getBottom()) / 2;
        int initialRadius = additionalGameInfo.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(additionalGameInfo, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                additionalGameInfo.setVisibility(View.GONE);
                toggleAdditionalInfoMore.setVisibility(View.VISIBLE);
            }
        });
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.additional_info_more})
    public void showAdditionalInfo() {
        toggleAdditionalInfoMore.setVisibility(View.GONE);
        int cx = (toggleAdditionalInfoMore.getLeft() + toggleAdditionalInfoMore.getRight()) / 2;
        int cy = (toggleAdditionalInfoMore.getTop() + toggleAdditionalInfoMore.getBottom()) / 2;
        int finalRadius = Math.max(additionalGameInfo.getWidth(), additionalGameInfo.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(additionalGameInfo, cx, cy, 0, finalRadius);
        additionalGameInfo.setVisibility(View.VISIBLE);
        anim.start();
    }

    @OnClick({R.id.numberJacks1, R.id.numberJacks2, R.id.numberJacks3, R.id.numberJacks4,
            R.id.valueSuitsDiamonds, R.id.valueSuitsHearts, R.id.valueSuitsSpades, R.id.valueSuitsClubs, R.id.valueGrand})
    public void updateView(View v) {
        uncheckButtonGroup(v);
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
        for (ToggleButton button : getVisiblePlayers()) {
            if (button.isChecked()) {
                count++;
            }
        }
        return count;
    }

    private List<ToggleButton> getVisiblePlayers() {

        int leftItemIndex = playerPager.getCurrentItem();
        int middleItemIndex = getIndex(leftItemIndex + 1);
        int rightItemIndex = getIndex(leftItemIndex + 2);

        return Arrays.asList(positionsMap.get(leftItemIndex), positionsMap.get(middleItemIndex), positionsMap.get(rightItemIndex));
    }

    private int getIndex(int i) {
        int max = positionsMap.size();
        return i < max ? i : i - max;
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

    private void uncheckButtonGroup(View clickedButton) {
        ArrayList<ToggleButton> buttonList = getToggleButtonsByTag((ViewGroup) rootView, (String) clickedButton.getTag());
        for (ToggleButton b : buttonList) {
            if (!b.equals(clickedButton)) {
                b.setChecked(false);
            }
        }
    }

    private void updateOtherPlayerButtonsCheckState(String tag, boolean isChecked) {
        for (ToggleButton button : allPlayerButtons.get(tag)) {
            button.setChecked(isChecked);
        }
    }

    private void updateActivePlayerButtonBackground(String tag) {
        for (ToggleButton b : allPlayerButtons.get(tag)) {
            b.setBackgroundResource(R.drawable.checked_circle_button);
        }
    }

    private void updateInactivePlayerButtonBackground(String tag) {
        for (ToggleButton b : allPlayerButtons.get(tag)) {
            b.setBackgroundResource(R.drawable.inactive_circle_button);
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

    //    Inspired by https://gist.github.com/8cbe094bb7a783e37ad1
    private class SampleAdapter extends PagerAdapter {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final View page = getActivity().getLayoutInflater().inflate(R.layout.page, container, false);

            ToggleButton playerButton = (ToggleButton) page.findViewById(R.id.playerToggleButton);
            TextView playerButtonText = (TextView) page.findViewById(R.id.playerToggleButtonText);

            playerButton.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/icomoon.ttf"));
            playerButton.setTag("" + (String) playerButton.getTag() + position);
            playerButtonText.setText(String.format(getString(R.string.item), position));

            ArrayList<ToggleButton> existingButtons = allPlayerButtons.get((String) playerButton.getTag());
            ArrayList<ToggleButton> temp = new ArrayList<>();
            temp.add(playerButton);
            if (null != existingButtons) {
                playerButton.setText(existingButtons.get(0).getText());
                playerButton.setBackground(existingButtons.get(0).getBackground());
                if (existingButtons.get(0).isChecked()) {
                    playerButton.setChecked(true);
                }
                temp.addAll(existingButtons);
            } else {
                playerButton.setText(R.string.icon_player);
            }

            allPlayerButtons.put((String) playerButton.getTag(), temp);
            positionsMap.put(position, playerButton);

            playerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateOtherPlayerButtonsCheckState((String) v.getTag(), ((ToggleButton) v).isChecked());

                    for (String s : allPlayerButtons.keySet()) {
                        if (!s.equals(v.getTag())) {
                            updateOtherPlayerButtonsCheckState(s, !((ToggleButton) v).isChecked());
                        }
                    }

                    updateActivePlayerButtonBackground((String) v.getTag());

                    for (String s : allPlayerButtons.keySet()) {
                        if (!s.equals(v.getTag())) {
                            updateInactivePlayerButtonBackground(s);
                        }
                    }

                    //TODO Update Not visible Players - set to neutral text

                    updateGameValue();
                }
            });

            container.addView(page);

            return (page);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
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

}
