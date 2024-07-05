package app5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

    private Terminal terminal;
  // Attribut(s)


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(Terminal varTerminal) {  // avec arguments
    //
      terminal = varTerminal;
  }



  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
    //
      if(terminal.getTypes() == Terminal.Types.NUMBER){
          return Integer.parseInt(terminal.getChaine());
      }
      return 0;
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    //
      return terminal.getChaine();
  }

}
