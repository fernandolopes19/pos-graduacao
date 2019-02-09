package br.ufg.inf.pos.fund_android.services;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.pos.fund_android.R;
import br.ufg.inf.pos.fund_android.model.Usuario;


public class WebTask extends WebTaskBase {

    private static final String SERVICE_URL = "login";
    private String email;
    private String senha;

    public WebTask(Context context, String email, String senha) {
        super(context, SERVICE_URL);
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void handleResponse(String response) {
        try {
            JSONObject responseJson = new JSONObject(response);
            Usuario usuario = parseJsonToUsuario(responseJson);
            EventBus.getDefault().post(usuario);
        }catch (JSONException e) {
            e.printStackTrace();
            if(!isSilent()){
                EventBus.getDefault().post(new Error(getContext().getString(R.string.label_error_invalid_response)));
            }
        }
    }

    private Usuario parseJsonToUsuario(JSONObject usuarioJson) {
        Usuario usuario = new Usuario();
        try {
            usuario.setNome(usuarioJson.getString("name"));
            usuario.setToken(usuarioJson.getString("token"));
            usuario.setUrlFoto(usuarioJson.getString("photo_url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuario;
    }


    @Override
    public String getRequestBody() {
        Map<String,String> mapRequest = new HashMap<>();
        mapRequest.put("email", email);
        mapRequest.put("senha", senha);

        return new Gson().toJson(mapRequest);
    }
}
