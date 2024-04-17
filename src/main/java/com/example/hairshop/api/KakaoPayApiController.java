package com.example.hairshop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class KakaoPayApiController {

    @Value("${kakao.admin_key}")
    private String adminKey;

    @PostMapping("/kakaopay")
    public String kakaoPay() {
        try {
            URL url = new URL("https://open-api.kakaopay.com/online/v1/payment/ready");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "SECRET_KEY " + adminKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String param = "cid=TC0ONETIME" +
                    "&partner_order_id=partner_order_id" +
                    "&partner_user_id=partner_user_id" +
                    "&item_name=초코파이" +
                    "&quantity=1" +
                    "&total_amount=2200" +
                    "&vat_amount=200" +
                    "&tax_free_amount=0" +
                    "&approval_url=https://developers.kakao.com/success" +
                    "&fail_url=https://developers.kakao.com/fail" +
                    "&cancel_url=https://developers.kakao.com/cancel";

            OutputStream output = conn.getOutputStream();
            DataOutputStream data = new DataOutputStream(output);
            data.writeBytes(param);
            data.close();

            int result = conn.getResponseCode();

            InputStream input;
            if(result == 200){
                input = conn.getInputStream();
            } else {
                input = conn.getErrorStream();
            }
            InputStreamReader read = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(read);

            return bufferedReader.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{\"result\":\"NO\"}";
    }
}
