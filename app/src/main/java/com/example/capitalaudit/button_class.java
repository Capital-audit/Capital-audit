package com.example.capitalaudit;
import com.example.capitalaudit.json_class.*;
import com.example.capitalaudit.api_class.*;
import com.example.capitalaudit.Util.*;

import java.io.IOException;

public class button_class {
    public static boolean login_button(String username, String password) throws IOException {
        api_class api = new api_class();
        String hashedUsr = Util.cred_hasher(username);
        String hashedPwd = Util.cred_hasher(password);
        String jsonString = json_class.login_to_json(hashedUsr, hashedPwd);
        String response = api.login_request(jsonString);


        return false;
    }
}
