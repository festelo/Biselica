package com.example.me.biselica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    boolean end = false;
    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    String word;//TODO ПЕРЕМЕСТИТЬ! УВЕЛИЧИТЬ ШРИФТ!
    char[] wordArray; //TODO ПЕРЕМЕСТИТЬ!
    char[] clearWordArray; //TODO ПЕРЕМЕСТИТЬ word.length()
    ArrayList<Character> symbolsUsed = new ArrayList<>();
    int Try;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (EditText) findViewById(R.id.textView3);
        tv3 = (TextView) findViewById(R.id.textView4);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Try = Integer.parseInt(sp.getString("HowMuchTry", "6"));
        tv3.setText(String.valueOf(Try));
        tv2.addTextChangedListener(inputTextWatcher);
        tv2.requestFocus();
        prepareForGame();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            event.startTracking();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            tv2.requestFocus();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void prepareForGame() {
        String filePath = sp.getString("list", "words.txt");
        String[] lines = {};
        try {
            FileInputStream fos = openFileInput(filePath);
            byte[] bytes = new byte[fos.available()];
            if(fos.read(bytes) == 0) throw new FileNotFoundException();
            lines = new String (bytes).split("\n");
            fos.close();
        } catch (FileNotFoundException e) {
            try {
                FileOutputStream fOut = openFileOutput(filePath, MODE_APPEND);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                osw.write("Кошка\n" +
                        "Японец\n" +
                        "Негр\n" +
                        "Фашист\n" +
                        "Коромысло\n" +
                        "Попугай\n" +
                        "Собака\n" +
                        "Птенец\n" +
                        "Голубь\n" +
                        "Воробей\n" +
                        "Азиат\n" +
                        "Виселица\n" +
                        "Диалог\n" +
                        "Словарь\n" +
                        "Язык\n" +
                        "Слово\n" +
                        "Ошибка\n" +
                        "Буква\n" +
                        "Человек\n" +
                        "Мужчина\n" +
                        "Женщина\n" +
                        "Машина\n" +
                        "Автобус\n" +
                        "Троллейбус\n" +
                        "Водопад\n" +
                        "Квадрат\n" +
                        "Треугольник\n" +
                        "Прямоугольник\n" +
                        "Посуда\n" +
                        "аванпост\n" +
                        "автомат\n" +
                        "азбука\n" +
                        "айсберг\n" +
                        "урна\n" +
                        "игра\n" +
                        "аквариум\n" +
                        "автомобиль\n" +
                        "приз\n" +
                        "Якубович\n" +
                        "аккумулятор\n" +
                        "акустика\n" +
                        "громкость\n" +
                        "алкоголь\n" +
                        "анализ\n" +
                        "анатомия\n" +
                        "вакуум\n" +
                        "англичанин\n" +
                        "немец\n" +
                        "белорус\n" +
                        "курица");
                osw.flush();
                osw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            prepareForGame();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random rand = new Random();
        word = lines[rand.nextInt(lines.length)];
        wordArray = word.toCharArray();
        clearWordArray = new char[word.length()];

        for(int i = 0; i<clearWordArray.length; i++){
            clearWordArray[i] = '-';
        }
        clearWordArray[0] = wordArray[0];
        symbolsUsed.add(Character.toUpperCase(wordArray[0]));
        clearWordArray[clearWordArray.length-1] = wordArray[wordArray.length-1];
        symbolsUsed.add(Character.toUpperCase(wordArray[wordArray.length-1]));
        boolean a = false;
        if(Process(Character.toUpperCase(wordArray[wordArray.length-1]))  != 0 ) a = true;
        if(Process(Character.toUpperCase(wordArray[0])) != 0) a = true;
        if(a && Arrays.equals(clearWordArray, wordArray)) {
                end = true;
                tv.setTextColor(0xff00ff00);
        };
        tv.setText(String.valueOf(clearWordArray));
    }

    public void onClick(View view){
        Try = Integer.parseInt(sp.getString("HowMuchTry", "6"));
        tv3.setText(String.valueOf(Try));
        tv1.setText(R.string.BeforeTap);
        tv.setTextColor(0xff000000);
        end = false;
        symbolsUsed = new ArrayList<>();
        //TODO
        prepareForGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private int Process(char Symbol){
        int result = 0;
        for (int i = 0; i < word.length(); i++) {
            if (Symbol == Character.toUpperCase(wordArray[i])) {
                result++;
                clearWordArray[i] = wordArray[i];
            }
        }
        return result;
    }
    private TextWatcher inputTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) { }
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { //ТУТ ВСЕ ДЕЛАЕТСЯ
            if(tv2.getText().length() == 0 || end) return;
            Resources rs = getResources();
            char Symbol = tv2.getText().toString().toUpperCase().toCharArray()[0];
            tv2.setText("");
            String[] MSG = rs.getStringArray(R.array.AfterTap);
            int fin = -1;

            if(symbolsUsed.contains(Symbol)) fin=2;
            else {
                symbolsUsed.add(Symbol);
                int result = Process(Symbol);

                if (result == 0) {
                    fin = 1;
                    Try--;
                    if (Try == 0) {
                        fin = 3;
                        end = true;
                        clearWordArray = wordArray;
                        tv.setTextColor(0xffff0000);
                    }
                } else {
                    fin = 0;
                    if (Arrays.equals(clearWordArray, wordArray)) {
                        fin = 4;
                        end = true;
                        tv.setTextColor(0xff00ff00);
                    }
                }
            }//\"
            tv3.setText(String.valueOf(Try));
            tv1.setText(String.format(MSG[fin], String.valueOf(Symbol).toUpperCase()));
            tv.setText(String.valueOf(clearWordArray));
        }
    };
}
