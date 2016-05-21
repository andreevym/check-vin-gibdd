package ru.android_studio.checkvinbygibdd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_AGENT = "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
    public static final String PHPSESS_ID = "PHPSESSID";
    public static final  String CAPTCHA = "CAPTCHA";
    public static final String VIN = "VIN";;
    public static String phpsessId;
    private ImageView imageView;
    private EditText vinET;
    private EditText captchaET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.captchaIV);
        imageView.setOnClickListener(this);
        captchaET = (EditText) findViewById(R.id.captchaEV);
        vinET = (EditText) findViewById(R.id.vinET);

        findViewById(R.id.checkBtn).setOnClickListener(this);
        findViewById(R.id.clearBtn).setOnClickListener(this);
        loadCaptcha();
    }

    private void loadCaptcha() {
        try {
            Bitmap bm = new RetrieveCaptchaTask().execute().get();
            imageView.setImageBitmap(bm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class RetrieveCaptchaTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            try {
                MainActivity.phpsessId = mainRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (MainActivity.phpsessId == null) {
                System.out.println("ERROR");
                return null;
            }

            try {
                return BitmapFactory.decodeStream(saveImage());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            /*if (result != null) {
                imageView.setImageBitmap(result);
            }*/
        }
    }

    private String mainRequest() throws IOException {
        URL url = new URL("http://www.gibdd.ru/check/auto/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);

        return getPhpsessIdByUrlConnection(urlConnection);
    }

    private String getPhpsessIdByUrlConnection(URLConnection urlConnection) {
        String resultPhpsessId = null;
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> next : headerFields.entrySet()) {
            String key = next.getKey();
            List<String> value = next.getValue();
            if (key != null && key.equals("Set-Cookie")) {
                System.out.println("key: " + key);
                for (String s : value) {
                    if (s.startsWith(PHPSESS_ID)) {
                        int start = s.indexOf("=") + 1;
                        int endIndex = s.indexOf(";");
                        resultPhpsessId = s.substring(start, endIndex);
                    }
                }
            }
        }
        System.out.println("resultPhpsessId: " + resultPhpsessId);
        return resultPhpsessId;
    }

    private InputStream saveImage() throws IOException {
        String imageUrl = "http://www.gibdd.ru/proxy/check/getCaptcha.php?PHPSESSID=" + MainActivity.phpsessId;
        URL url = new URL(imageUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);
        urlConnection.setRequestProperty("X-Compress", "0");
        urlConnection.setRequestProperty("Referer", "http://www.gibdd.ru/check/auto/");
        urlConnection.setRequestProperty("Host", "www.gibdd.ru");
        urlConnection.setRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
        urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
        urlConnection.setRequestProperty("Accept-Language", "ru,en-US;q=0.8,en;q=0.6");
        urlConnection.setRequestProperty("Connection", "keep-alive");

        String cookie = String.format("captchaSessionId=%s; _ym_uid=1462452903560071241; PHPSESSID=%s; _ym_isad=1; _ga=GA1.2.74263411.1462452903; BITRIX_SM_REGKOD=00; BITRIX_SM_IP_REGKOD=77; siteType=pda", MainActivity.phpsessId, MainActivity.phpsessId);
        urlConnection.setRequestProperty("Cookie", cookie);

        return urlConnection.getInputStream();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkBtn:
                if(vinET.getText().toString().length() != 17) {
                    Toast.makeText(this, "VIN номер должен быть 17 символов", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(captchaET.getText().length() != 5) {
                    Toast.makeText(this, "Код подтверждения должен быть 5 символов", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra(VIN, vinET.getText().toString());
                intent.putExtra(CAPTCHA, captchaET.getText().toString());
                intent.putExtra(PHPSESS_ID, MainActivity.phpsessId);
                startActivity(intent);

                loadCaptcha();
                captchaET.getText().clear();
                //vinET.getText().clear();
                break;
            case R.id.captchaIV:
                loadCaptcha();
                break;
            case R.id.clearBtn:
                loadCaptcha();
                vinET.getText().clear();
                captchaET.getText().clear();
                break;
        }
    }
}
