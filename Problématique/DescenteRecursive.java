package app5;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  // Attributs
  private Terminal dernierTerminal;
  private AnalLex ALEX;

/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
    //
  Reader r = new Reader(in);
  ALEX = new AnalLex(r.toString());
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt( ) {
  dernierTerminal = ALEX.prochainTerminal();
  return E();
   //
}


// Methode pour chaque symbole non-terminal de la grammaire retenue
// ... 
// ...

  private ElemAST E(){
    ElemAST b1 = null;
    ElemAST b2 = null;

    b1 = T();
    if (dernierTerminal.getTypes() == Terminal.Types.OPERATEUR2){
      Terminal operator = dernierTerminal;
      if (ALEX.resteTerminal()) {
        dernierTerminal = ALEX.prochainTerminal();
      }
      b2 = E();
      return new NoeudAST(operator, b1, b2);
    }
    else if (dernierTerminal.getTypes() == Terminal.Types.PARENTHESEF || !ALEX.resteTerminal() ){
      return b1;
    }
    else {
      ErreurSynt("Erreur de syntaxe au niveau E");
    }

    ErreurSynt("Erreur de syntaxe au niveau E " + dernierTerminal.getTypes().toString());
    return new FeuilleAST(new Terminal("NAN", Terminal.Types.VIDE));

  }

  private ElemAST T(){
    ElemAST b1 = null;
    ElemAST b2 = null;

    b1 = F();
    if(dernierTerminal.getTypes() == Terminal.Types.OPERATEUR1){
      Terminal operator = dernierTerminal;
      if (ALEX.resteTerminal()) {
        dernierTerminal = ALEX.prochainTerminal();
      }
      b2 = T();
      return new NoeudAST(operator, b1, b2);
    }
    else if (dernierTerminal.getTypes() == Terminal.Types.OPERATEUR2 || dernierTerminal.getTypes() == Terminal.Types.PARENTHESEF || !ALEX.resteTerminal()){
      return b1;
    }
    else{
      ErreurSynt("Erreur de syntaxe au niveau T");
    }
    ErreurSynt("Erreur de syntaxe au niveau T " + dernierTerminal.getTypes().toString());
    return new FeuilleAST(new Terminal("NAN", Terminal.Types.VIDE));
  }
  private ElemAST F(){
    ElemAST b1 = null;
    switch(dernierTerminal.getTypes()){
      case NUMBER, VARIABLE:
        b1 = new FeuilleAST(dernierTerminal);
        if (ALEX.resteTerminal()) {
          dernierTerminal = ALEX.prochainTerminal();
        }
        return b1;
      case PARENTHESEO:
        if (ALEX.resteTerminal()) {
          dernierTerminal = ALEX.prochainTerminal();
        }
        b1 = E();
        if(dernierTerminal.getTypes() != Terminal.Types.PARENTHESEF){
          ErreurSynt("Une parenthese n'est pas fermee");
        }
        if (ALEX.resteTerminal()) {
          dernierTerminal = ALEX.prochainTerminal();
        }
        return b1;
      default:
        ErreurSynt("Erreur au niveau F");
    }
    ErreurSynt("Erreur de syntaxe au niveau F " + dernierTerminal.getTypes().toString());
    return new FeuilleAST(new Terminal("NAN", Terminal.Types.VIDE));
  }



/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s)
{
    //
    throw new Error("Erreur syntaxique:" + s);
}



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWriteEval = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
    }
    DescenteRecursive dr = new DescenteRecursive(args[0]);
    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite 
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

}

