package com.qqlive.aphone.fortest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qqlive.aphone.fortest.GithubActivity;
import com.qqlive.aphone.fortest.ITabClickListener;
import com.qqlive.aphone.fortest.Kitchen.KitchenTestActivity;
import com.qqlive.aphone.fortest.LeakCanary.LeakCanaryTestActivity;
import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.SunSine.SunShineMainActivity;
import com.qqlive.aphone.fortest.TODOList.TaskMainActivity;
import com.qqlive.aphone.fortest.ToyActivity;
import com.qqlive.aphone.fortest.UiColorsAndFonts.UiColorsAndFrontsMainActivity;
import com.qqlive.aphone.fortest.background.BackGroundMainActivity;
import com.qqlive.aphone.fortest.boardingpass.BoardPassMainActivity;
import com.qqlive.aphone.fortest.intent.IntentMainActivity;
import com.qqlive.aphone.fortest.quiz.QuizMainActivity;



public class videoFragment extends BaseFragment implements ITabClickListener,View.OnClickListener {

    private Button btn;
    @Override

    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        view.findViewById(R.id.onClickOpenContentProviderButton).setOnClickListener(this);
        view.findViewById(R.id.onClickGithubButton).setOnClickListener(this);
        view.findViewById(R.id.onClickOpenToyButton).setOnClickListener(this);
        view.findViewById(R.id.createYourOwn).setOnClickListener(this);
        view.findViewById(R.id.onClickTasksButton).setOnClickListener(this);
        view.findViewById(R.id.onClickToBackgroundButton).setOnClickListener(this);
        view.findViewById(R.id.onClickUiButton).setOnClickListener(this);
        view.findViewById(R.id.onClickUICandFButton).setOnClickListener(this);
        view.findViewById(R.id.onClickLeakCanaryButton).setOnClickListener(this);
        view.findViewById(R.id.onClickSunshineButton).setOnClickListener(this);
        view.findViewById(R.id.onClickTestButton).setOnClickListener(this);

        return view;
    }

    public void onMenuItemClick() {

    }
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onClickOpenContentProviderButton:
                Intent contentIntent = new Intent().setClass(getActivity(), QuizMainActivity.class);
                getActivity().startActivity(contentIntent);
                break;
            case R.id.onClickGithubButton:
                Intent Githubintent = new Intent();
                Githubintent.setClass(getActivity(),GithubActivity.class);
                getActivity().startActivity(Githubintent);
                break;
            case R.id.onClickOpenToyButton:
                Intent Toyintent = new Intent().setClass(getActivity(),ToyActivity.class);
                getActivity().startActivity(Toyintent);
                break;
            case R.id.createYourOwn:
                Intent intentIntent = new Intent().setClass(getActivity(), IntentMainActivity.class);
                getActivity().startActivity(intentIntent);
                break;
            case R.id.onClickTasksButton:
                Intent tasksIntent = new Intent().setClass(getActivity(), TaskMainActivity.class);
                getActivity().startActivity(tasksIntent);
                break;
            case R.id.onClickToBackgroundButton:
                Intent backgroundIntent = new Intent().setClass(getActivity(), BackGroundMainActivity.class);
                getActivity().startActivity(backgroundIntent);
                break;
            case R.id.onClickUiButton:
                Intent UiIntnent = new Intent().setClass(getActivity(),BoardPassMainActivity.class);
                getActivity().startActivity(UiIntnent);
                break;
            case R.id.onClickUICandFButton:
                Intent UiIntent = new Intent().setClass(getActivity(), UiColorsAndFrontsMainActivity.class);
                getActivity().startActivity(UiIntent);
                break;
            case R.id.onClickLeakCanaryButton:
                Intent LeakCanaryIntent = new Intent().setClass(getActivity(), LeakCanaryTestActivity.class);
                getActivity().startActivity(LeakCanaryIntent);
                break;
            case  R.id.onClickSunshineButton:
                Intent SunShineIntent = new Intent().setClass(getActivity(), SunShineMainActivity.class);
                getActivity().startActivity(SunShineIntent);
                break;
            case R.id.onClickTestButton:
                Intent TestIntent = new Intent().setClass(getActivity(), KitchenTestActivity.class);
                getActivity().startActivity(TestIntent);
                break;

            default:
                break;

        }
    }
}
