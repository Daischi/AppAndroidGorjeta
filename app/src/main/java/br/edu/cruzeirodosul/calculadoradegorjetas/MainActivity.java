package br.edu.cruzeirodosul.calculadoradegorjetas;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // atributos que referenciam as views da activity
    private EditText contaEditText;
    private EditText gorjeta5EditText;
    private EditText gorjeta10EditText;
    private EditText gorjeta15EditText;
    private SeekBar percentualSeekBar;
    private EditText percentualEditText;
    private EditText gorjetaEditText;

    // Constantes usadas ao se salvar / restaurar o estado do app
    private static final String CONTA = "CONTA";
    private static final String PERCENTUAL = "PERCENTUAL";


    // atributos que armazena os valores que devem ser restaurados
    private double conta;
    private double percentual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Obtém refências ás views do layout da activity
        contaEditText = findViewById(R.id.contaEditText);
        gorjeta5EditText = findViewById(R.id.gorjeta5EditText);
        gorjeta10EditText = findViewById(R.id.gorjeta10EditText);
        gorjeta15EditText = findViewById(R.id.gorjeta15EditText);
        percentualSeekBar = findViewById(R.id.percentualSeekBar);
        gorjetaEditText = findViewById(R.id.gorjetaEditText);
        percentualEditText = findViewById(R.id.percentualEditText);



        //associa os ouvintes de eventos para as views interativas
        contaEditText.addTextChangedListener(ouvinteContaEditText);
        percentualSeekBar.setOnSeekBarChangeListener(ouvintePercentualSeekBar);

        // Verificar se o aplicativo acabou de ser iniciado ou esta sendo restaurado.
        // Neste caso, recupera os valores de conta e percentual

        if (savedInstanceState == null) {
            conta = 0;
            percentual = 7;
        } else {
            // recupera os valores da instância anterior
            conta = savedInstanceState.getDouble(CONTA);
            percentual = savedInstanceState.getDouble(PERCENTUAL);
        }

        contaEditText.setText(String.format("%.2f", conta));
        percentualSeekBar.setProgress((int) percentual);

    }

    /**
     * Atualiza o valor das gorjetas padrão
     */
    private void atualizaGorjetas() {
        double[] gorjetas = Calculadora.gorjeta(conta);
        gorjeta5EditText.setText(String.format("%.2f", gorjetas[0]));
        gorjeta10EditText.setText(String.format("%.2f", gorjetas[1]));
        gorjeta15EditText.setText(String.format("%.2f", gorjetas[2]));

    }

    /**
     * Atualiza o valor da gorjeta personalizada
     */
    private void atualizaGorjetaPersonalizada() {
        gorjetaEditText.setText(String.format("%.2f", Calculadora.gorjeta(conta, percentual)));
    }

    private TextWatcher ouvinteContaEditText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                conta = Double.parseDouble(contaEditText.getText().toString());

            } catch (NumberFormatException ex) {
                conta = 0;
            }
            atualizaGorjetas();
            atualizaGorjetaPersonalizada();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // Define o objeto ouvinte de  mudança no percentualSeekBar
    private SeekBar.OnSeekBarChangeListener ouvintePercentualSeekBar = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percentual = (double) percentualSeekBar.getProgress();
            percentualEditText.setText(String.format("%.1f", percentual));
            atualizaGorjetaPersonalizada();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(CONTA, conta);
        outState.putDouble(PERCENTUAL, percentual);
    }

}