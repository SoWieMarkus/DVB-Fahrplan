package markus.wieland.dvbfahrplan;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.database.point.PointViewModel;

public abstract class SearchFragment extends DefaultFragment implements TextWatcher {

    protected Fragment currentFragment;
    protected PointViewModel pointViewModel;
    protected DVBApi dvbApi;

    public SearchFragment(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @CallSuper
    @Override
    public void bindViews() {
        assert getActivity() != null;

        pointViewModel = ViewModelProviders.of(getActivity()).get(PointViewModel.class);
        dvbApi = new DVBApi(getActivity());
    }

    protected void loadFragment(Fragment fragment) {
        if (currentFragment != null && currentFragment.equals(fragment)) return;
        currentFragment = fragment;
        if (getActivity() == null) return;
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right_animation, R.anim.slide_out_left_animation)
                .addToBackStack(null)
                .replace(R.id.frame_layout, currentFragment).commit();
    }

    public abstract boolean handleBackPress();

    public void focus(TextInputLayout textInputLayout) {
        if (textInputLayout.getEditText() == null) return;
        if (getActivity() == null) return;

        textInputLayout.getEditText().requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

        String content = textInputLayout.getEditText().getText().toString().trim();

        textInputLayout.getEditText().setSelection(content.length());
    }

    public void clearFocus(TextInputLayout textInputLayout) {

        if (textInputLayout.getEditText() == null) return;
        if (getActivity() == null) return;

        textInputLayout.getEditText().clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(textInputLayout.getEditText().getWindowToken(), 0);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
