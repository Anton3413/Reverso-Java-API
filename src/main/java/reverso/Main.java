package reverso;


import reverso.data.response.impl.SpellCheckResponse;
import reverso.supportedLanguages.Language;

public class Main {
    public static void main(String[] args) {
        Reverso reverso = new Reverso();

        SpellCheckResponse response = reverso.getSpellCheck(Language.ITALIAN,"here ve have errores, we shuld to fix it");

        System.out.println(response.toJson());

        System.out.println(response.getStats().toJson());
    }
}