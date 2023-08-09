package com.example.popselllists.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.popselllists.R;
import com.example.popselllists.databinding.FragmentDashboardBinding;
import com.example.popselllists.retrofit.Chatroom;
import com.example.popselllists.retrofit.JsonPlaseHolderApi;
import com.example.popselllists.retrofit.Post;
import com.example.popselllists.retrofit.PostBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private TextView textView2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView2 = root.findViewById(R.id.text_dashboard);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wapp.pepsell.net/Pepsell2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaseHolderApi jsonPlaseHolderApi = retrofit.create(JsonPlaseHolderApi.class);

        PostBody postBody = new PostBody("CHATROOM_LIST", "380990143524", "1", 1690819233997L);

        Call<Post> call = jsonPlaseHolderApi.getPosts(postBody);
        generateCall(call);


        return root;
    }

    private void generateCall(Call<Post> call) {
        Toast.makeText(getActivity(), "generateCall", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView2.setText("codeErr: " + response);
                    return;
                }

//                textView2.setText("resp: " + response.);

                Toast.makeText(getActivity(), "onResponse", Toast.LENGTH_SHORT).show();

                Post posts = response.body();

//                textView2.append("\n code: " + posts.getSTATUS() + "\n data: " + posts.getChatrooms());
//                textView2.append("\n\n response: " + posts.getChatrooms());

                if (posts != null) {
                    for (Chatroom postItem : posts.getChatrooms()) {

                        String content = "";
                        content += "Status: " + postItem.getName() + "\n";

                        textView2.append(content);
                    }
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_SHORT).show();
                textView2.setText(t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}