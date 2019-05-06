package com.e15cn2.restaurantorder.screen.main.admin.add_table;

import android.widget.Toast;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.data.repository.TableRepository;
import com.e15cn2.restaurantorder.data.source.remote.TableRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentAddTableBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.utils.Constants;

public class AddTableFragment extends BaseFragment<FragmentAddTableBinding>
        implements AddTableContract.View {
    private AddTableContract.Presenter mPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_table;
    }

    @Override
    protected void initData() {
        mPresenter = new AddTablePresenter(
                TableRepository.getInstance(TableRemoteDataSource.getInstance()), this);
    }

    @Override
    public void showMessage(String msg) {
        if (msg.equals(Constants.JSonKey.MESSAGE_SUCCESS)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_add_table_success), Toast.LENGTH_SHORT).show();
//            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.text_add_table_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        //Belong to dev
    }
}
