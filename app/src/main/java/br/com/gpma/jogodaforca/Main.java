package br.com.gpma.jogodaforca;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;
import java.util.Random;

public class Main extends AppCompatActivity {

    private Button btJogar;
    private Button btPlay;
    private EditText etLetra;
    private ForcaView fvJogo;

    ForcaController forcaController;
    public ForcaController getForcaController() {
        return forcaController;
    }
    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
    private String[] palavra = Palavras.getLista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btJogar = (Button) findViewById(R.id.btJogar);
        btPlay = (Button) findViewById(R.id.btPlay);
        etLetra = (EditText) findViewById(R.id.etLetra);
        fvJogo = (ForcaView) findViewById(R.id.fvJogo);

        init();
    }

    private void init() {

        btJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etLetra.getText().toString().trim().length() == 0) return;
                getForcaController().joga(etLetra.getText().toString().trim().charAt(0));
                fvJogo.invalidate(); // Como um refresh do canvas
                etLetra.getText().clear();

                if(getForcaController().Terminou()) {

                    btJogar.setEnabled(false);
                    btPlay.setEnabled(true);
                    etLetra.setEnabled(false);

                    if(getForcaController().Perdeu())
                        Toast.makeText(getApplicationContext(), "Você perdeu!", Toast.LENGTH_LONG).show();
                    if(getForcaController().Ganhou())
                        if(getForcaController().getQtdeErros() == 0)
                            Toast.makeText(getApplicationContext(), "Parabéns! Ganhou sem erros!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Você ganhou!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForcaController(new ForcaController(palavra[new Random().nextInt( palavra.length )]));
                fvJogo.setForcaController(getForcaController());
                etLetra.getText().clear();
                etLetra.setEnabled(true);
                btJogar.setEnabled(true);
                btPlay.setEnabled(false);
                fvJogo.invalidate(); // Como um refresh do canvas
            }
        });

        etLetra.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        btJogar.performClick();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
