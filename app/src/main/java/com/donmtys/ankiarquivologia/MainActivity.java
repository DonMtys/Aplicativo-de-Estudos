package com.donmtys.ankiarquivologia;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final String[] perguntas = {
            "Arquivo permanente deve ficar em local de difícil acesso porque a pesquisa não é aberta a todos?",
            "Quais são os quatro critérios clássicos de classificação dos arquivos?",
            "Arquivo é apenas um conjunto de documentos pertencentes a um acervo?"
    };

    private final String[] respostas = {
            "Não. Arquivos permanentes têm guarda definitiva e servem à consulta e à pesquisa, salvo restrições legais de sigilo.",
            "• Entidade mantenedora;\n• Estágio de evolução;\n• Extensão de atuação;\n• Natureza dos documentos.",
            "Não. Arquivo é o conjunto de documentos produzidos e recebidos por uma pessoa ou instituição no exercício de suas atividades.\n\nAcervo é termo mais amplo; arquivo exige vínculo orgânico com as atividades do produtor."
    };

    private final String[] macetes = {
            "Permanente = preservar + pesquisar.",
            "Mantenedora → estágio → extensão → natureza.",
            "Arquivo nasce da atividade."
    };

    private int indice = 0;
    private boolean respostaVisivel = false;

    private TextView contador;
    private TextView pergunta;
    private TextView resposta;
    private TextView macete;
    private TextView rotuloResposta;
    private Button revelar;
    private Button anterior;
    private Button proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(245, 247, 251));
        getWindow().setNavigationBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        LinearLayout raiz = new LinearLayout(this);
        raiz.setOrientation(LinearLayout.VERTICAL);
        raiz.setPadding(dp(20), dp(18), dp(20), dp(18));
        raiz.setBackgroundColor(Color.rgb(245, 247, 251));

        TextView titulo = texto("Anki Arquivologia", 26, Color.rgb(25, 35, 55), Typeface.BOLD);
        raiz.addView(titulo);

        TextView subtitulo = texto("3 cartões cirúrgicos • estudo offline", 14, Color.rgb(100, 110, 128), Typeface.NORMAL);
        subtitulo.setPadding(0, dp(4), 0, dp(14));
        raiz.addView(subtitulo);

        contador = texto("", 13, Color.rgb(90, 74, 210), Typeface.BOLD);
        contador.setGravity(Gravity.END);
        contador.setPadding(0, 0, 0, dp(8));
        raiz.addView(contador);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
        raiz.addView(scroll, scrollParams);

        LinearLayout cartao = new LinearLayout(this);
        cartao.setOrientation(LinearLayout.VERTICAL);
        cartao.setPadding(dp(22), dp(22), dp(22), dp(22));
        cartao.setBackground(fundo(Color.WHITE, 22, Color.rgb(226, 230, 240), 1));
        cartao.setElevation(dp(3));
        scroll.addView(cartao, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT));

        TextView frente = texto("FRENTE", 12, Color.rgb(90, 74, 210), Typeface.BOLD);
        cartao.addView(frente);

        pergunta = texto("", 22, Color.rgb(28, 36, 52), Typeface.BOLD);
        pergunta.setPadding(0, dp(12), 0, dp(22));
        pergunta.setLineSpacing(dp(3), 1f);
        cartao.addView(pergunta);

        View divisor = new View(this);
        divisor.setBackgroundColor(Color.rgb(232, 235, 242));
        cartao.addView(divisor, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(1)));

        rotuloResposta = texto("VERSO", 12, Color.rgb(28, 145, 100), Typeface.BOLD);
        rotuloResposta.setPadding(0, dp(22), 0, dp(8));
        cartao.addView(rotuloResposta);

        resposta = texto("", 18, Color.rgb(45, 54, 70), Typeface.NORMAL);
        resposta.setLineSpacing(dp(4), 1f);
        cartao.addView(resposta);

        macete = texto("", 16, Color.rgb(91, 69, 20), Typeface.BOLD);
        macete.setPadding(dp(14), dp(12), dp(14), dp(12));
        LinearLayout.LayoutParams maceteParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        maceteParams.setMargins(0, dp(18), 0, 0);
        cartao.addView(macete, maceteParams);

        revelar = botao("Mostrar resposta", Color.rgb(90, 74, 210), Color.WHITE);
        LinearLayout.LayoutParams revelarParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(54));
        revelarParams.setMargins(0, dp(16), 0, dp(12));
        raiz.addView(revelar, revelarParams);
        revelar.setOnClickListener(v -> {
            respostaVisivel = !respostaVisivel;
            atualizarVisibilidade();
        });

        LinearLayout navegacao = new LinearLayout(this);
        navegacao.setOrientation(LinearLayout.HORIZONTAL);
        raiz.addView(navegacao, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(52)));

        anterior = botao("← Anterior", Color.WHITE, Color.rgb(70, 78, 94));
        anterior.setBackground(fundo(Color.WHITE, 16, Color.rgb(215, 220, 232), 1));
        LinearLayout.LayoutParams navItem = new LinearLayout.LayoutParams(0, dp(52), 1f);
        navItem.setMargins(0, 0, dp(6), 0);
        navegacao.addView(anterior, navItem);

        proximo = botao("Próximo →", Color.rgb(35, 42, 58), Color.WHITE);
        LinearLayout.LayoutParams navItem2 = new LinearLayout.LayoutParams(0, dp(52), 1f);
        navItem2.setMargins(dp(6), 0, 0, 0);
        navegacao.addView(proximo, navItem2);

        anterior.setOnClickListener(v -> {
            if (indice > 0) {
                indice--;
                respostaVisivel = false;
                atualizarCartao();
            }
        });

        proximo.setOnClickListener(v -> {
            if (indice < perguntas.length - 1) {
                indice++;
                respostaVisivel = false;
                atualizarCartao();
            }
        });

        setContentView(raiz);
        atualizarCartao();
    }

    private void atualizarCartao() {
        contador.setText("Cartão " + (indice + 1) + " de " + perguntas.length);
        pergunta.setText(perguntas[indice]);
        resposta.setText(respostas[indice]);
        macete.setText("MACETE: " + macetes[indice]);
        anterior.setEnabled(indice > 0);
        anterior.setAlpha(indice > 0 ? 1f : 0.45f);
        proximo.setEnabled(indice < perguntas.length - 1);
        proximo.setAlpha(indice < perguntas.length - 1 ? 1f : 0.45f);
        atualizarVisibilidade();
    }

    private void atualizarVisibilidade() {
        int visibilidade = respostaVisivel ? View.VISIBLE : View.GONE;
        rotuloResposta.setVisibility(visibilidade);
        resposta.setVisibility(visibilidade);
        macete.setVisibility(visibilidade);
        revelar.setText(respostaVisivel ? "Ocultar resposta" : "Mostrar resposta");
    }

    private TextView texto(String conteudo, int tamanhoSp, int cor, int estilo) {
        TextView view = new TextView(this);
        view.setText(conteudo);
        view.setTextSize(tamanhoSp);
        view.setTextColor(cor);
        view.setTypeface(Typeface.create("sans-serif", estilo));
        return view;
    }

    private Button botao(String titulo, int corFundo, int corTexto) {
        Button button = new Button(this);
        button.setText(titulo);
        button.setTextSize(15);
        button.setTextColor(corTexto);
        button.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setBackground(fundo(corFundo, 16, corFundo, 0));
        return button;
    }

    private GradientDrawable fundo(int cor, int raioDp, int corBorda, int bordaDp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(cor);
        drawable.setCornerRadius(dp(raioDp));
        if (bordaDp > 0) {
            drawable.setStroke(dp(bordaDp), corBorda);
        }
        return drawable;
    }

    private int dp(int valor) {
        return Math.round(valor * getResources().getDisplayMetrics().density);
    }
}
