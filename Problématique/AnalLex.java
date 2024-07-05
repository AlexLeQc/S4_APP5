package app5;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

// Attributs
//  ...
  String s;
  int ptrLecture;
  EtatLexical etat;
  String prochainterminal;


  enum EtatLexical {
      INITIAL,
      OPERATEUR,
      VARIABLE,
      VERIFUNDERSCORE,
      NOMBRE

  }

	
/** Constructeur pour l'initialisation d'attribut(s)
 */
  public AnalLex(String s ) {  // arguments possibles
    //
    this.s = s.replaceAll("\\s+","");
    this.s = this.s.replaceAll("//.*", "")+ ' ';
    ptrLecture = 0;

  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
      return ptrLecture < (s.length() - 1);
  }

  private void analInit(char c){
    if (Character.isLetter(c)){
      if(Character.isLowerCase(c)){
        ErreurLex("Premier caractere est une minuscule");
      }
      etat = EtatLexical.VARIABLE;
    }
    else if(Character.isDigit(c)){
      etat = EtatLexical.NOMBRE;
    }
    else if(c=='+' || c=='-' || c == '*' || c =='/' || c=='(' || c==')'){
      etat = EtatLexical.OPERATEUR;
    }
    else if(c == '_'){
      ErreurLex(("Premier caractere est un underscore."));
    }
    else{
      ErreurLex("Caractere invalide.");
    }

  }

  private Terminal analOper(){

    Terminal retour = null;

    switch (s.charAt(--ptrLecture - 1)) {
      case '-', '+' -> {
        retour = new Terminal(prochainterminal.substring(0, prochainterminal.length() - 1), Terminal.Types.OPERATEUR2);
      }
      case '*', '/' -> {
        retour = new Terminal(prochainterminal.substring(0, prochainterminal.length() - 1), Terminal.Types.OPERATEUR1);
      }
      case '(' -> {
        retour = new Terminal(prochainterminal.substring(0, prochainterminal.length() - 1), Terminal.Types.PARENTHESEO);
      }
      case ')' -> {
        retour = new Terminal(prochainterminal.substring(0, prochainterminal.length() - 1), Terminal.Types.PARENTHESEF);
      }
    }
    return retour;
  }

  private Terminal analVar(char c){

    Terminal retour = null;
    if (c == '_') {
      etat = EtatLexical.VERIFUNDERSCORE;
    }
    else if(!Character.isLetter(c)) {
      ptrLecture--;
      retour = new Terminal(prochainterminal.substring(0, prochainterminal.length() - 1), Terminal.Types.VARIABLE);
    }
    return retour;
  }

  private void analVerifUnderscore(char c){
    if(Character.isLetter(c)){
      etat = EtatLexical.VARIABLE;
    }
    else if (c == '_'){
      ErreurLex("Caractere invalide: Impossible d'avoir un double underscore (__)");
    }
    else{
      ErreurLex("Caractere invalide: Impossible de finir avec un _");
    }
  }


  private Terminal analNombre(char c){
    Terminal retour = null;
    if (!Character.isDigit(c)){
      ptrLecture--;
      retour = new Terminal(prochainterminal.substring(0, prochainterminal.length() - 1), Terminal.Types.NUMBER);
    }
    return retour;
  }
  
  
/** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
 */  
  public Terminal prochainTerminal( ) {
     //
    prochainterminal = "";
    etat = EtatLexical.INITIAL;
    Terminal retour = null;
    while(retour == null){
      System.out.println(etat);
      char c = s.charAt(ptrLecture);
      ptrLecture++;
      prochainterminal += c;

      switch (etat) {
        case INITIAL -> analInit(c);
        case OPERATEUR -> retour = analOper();
        case VARIABLE -> {
          Terminal validV = analVar(c);
          if (validV != null) {
            retour = validV;
          }
        }
        case VERIFUNDERSCORE -> analVerifUnderscore(c);
        case NOMBRE -> {
          Terminal validN = analNombre(c);
          if (validN != null) {
            retour = validN;
          }
        }
      }
    }
    return retour;
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s) {
     //
    throw new Error("ERREUR lexical au niveau du caractere " + ptrLecture + "; " + s );
  }

  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
    args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite +=t.getChaine() + "\n" ;  // toWrite contient le resultat
    }				                  //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
