package br.com.gpma.jogodaforca;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

public class ForcaController {

    private String palavraParaAdvinhar;
    private String palavraParaAdvinharSemAcento;
    private Set<Character> letrasUsadas;
    private int qtdeErros = 0;
    private String letrasErradas;

    public ForcaController(String palavra) {
        letrasUsadas = new HashSet<Character>();
        letrasErradas = "";
        this.palavraParaAdvinhar = palavra;
        palavra = Normalizer.normalize(palavra, Normalizer.Form.NFD);
        this.palavraParaAdvinharSemAcento = palavra.replaceAll("[^\\p{ASCII}]", "");
    }

    public int getQtdeErros() {
        return qtdeErros;
    }

    public void joga(Character letra) {
        //Se letra já foi jogada, sai do método
        if (letrasUsadas.contains(letra)) return;

        letrasUsadas.add(letra);

        if (palavraParaAdvinharSemAcento.contains(letra.toString())) return;

        qtdeErros++;
        letrasErradas += letra.toString().trim().charAt(0);
    }

    public String getPalavraAteAgora() {
        String visualizacao = "";
        int c = 0;
        for(char s : palavraParaAdvinharSemAcento.toCharArray()) {
            if(letrasUsadas.contains(s)) {
                c = new String(palavraParaAdvinharSemAcento).indexOf(s, c);
                visualizacao += palavraParaAdvinhar.charAt(c);
            } else {
                visualizacao += " ";
            }
        }
        return visualizacao;
    }
    public String getLetrasErradas() {
        return letrasErradas;
    }

    public boolean Perdeu() {
        return qtdeErros == 6;
    }
    public boolean Ganhou() {
        return !getPalavraAteAgora().contains(" ");
    }
    public boolean Terminou() {
        return Perdeu() || Ganhou();
    }


}
