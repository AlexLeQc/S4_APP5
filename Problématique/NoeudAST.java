package app5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  private Terminal terminal;
  private ElemAST p1;
  private ElemAST p2;

  // Attributs

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(Terminal varTerminal, ElemAST varP1, ElemAST varP2 ) { // avec arguments

    terminal = varTerminal;
    p1 = varP1;
    p2 = varP2;
    //
  }

  public Terminal GetTerminal(){
    return terminal;
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {
     //
    return switch (terminal.getChaine()){
      case "+" -> p1.EvalAST() + p2.EvalAST();
      case "-" -> p1.EvalAST() - p2.EvalAST();
      case "*" -> p1.EvalAST() * p2.EvalAST();
      case "/" -> p1.EvalAST() / p2.EvalAST();
      default -> 0;
    };
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
     //
    return "(" + p1.LectAST() + " " + terminal.getChaine() + " " + p2.LectAST() + ")";
  }

}


