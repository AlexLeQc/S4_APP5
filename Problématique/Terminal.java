package app5;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {

// Constantes et attributs
//  ....
  private final String chaine;
  private Types type;

  enum Types {
    VIDE,
    VARIABLE,
    NUMBER,
    OPERATEUR1,
    OPERATEUR2,
    PARENTHESEO,
    PARENTHESEF
  }


/** Un ou deux constructeurs (ou plus, si vous voulez)
  *   pour l'initalisation d'attributs 
 */	
  public Terminal(String s, Types t ) {   // arguments possibles
     //
    this.chaine = s;
    this.type = t;
  }


  public String getChaine() {
    return chaine;
  }

  public Types getTypes(){
    return type;
  }

}
