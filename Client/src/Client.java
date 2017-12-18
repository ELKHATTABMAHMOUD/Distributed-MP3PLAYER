import Lecteurmp3.*;
import Lecteurmp3.Morceau;


/*
 * ------------------------------------------------------------------------------------------------------------------
 * ---------------------	Réalisé par : 	EL KHATTAB Mahmoud. Master 1 - ILSEN ------------------------------------
 * ------------------------------------------------------------------------------------------------------------------
 */
public class Client
{
    public static void main(String[] args)
    {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args))
        {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("LecteurMP3:default -p 20000");
            ManagerPrx lecteurmp3 = ManagerPrx.checkedCast(base);
            if(lecteurmp3 == null)
            {
                throw new Error("Invalid proxy");
            }
            /* ---------------------------------------------------------
             * Ajout de 4 Morceaux pour tester les fonction du serveur 
             ----------------------------------------------------------*/
            lecteurmp3.ajouterMorceau("Chandelier","Sia","POP","C:/Master ILSEN/Architecture Distribuée/Music");
            lecteurmp3.ajouterMorceau("Number one for me","Maher Zine","RnB", "C:/Master ILSEN/Architecture Distribuée/Music");
            lecteurmp3.ajouterMorceau("S.O.S","Indila","POP", "C:/Master ILSEN/Architecture Distribuée/Music");
            lecteurmp3.ajouterMorceau("Love Story","Indila","Groove", "C:/Master ILSEN/Architecture Distribuée/Music");
    		
            
            /*---------------------------------------------------------
             *  Recherche des morceaux de la catégorie POP
             ---------------------------------------------------------*/
            Morceau list_mrc[] ;
            list_mrc = lecteurmp3.rechMorceauxParGenre("POP");
            System.out.println("--------------- Les morceaux musicaux de la catégorie POP ---------------");
            for(int i=0 ; i< list_mrc.length; i++)
            	System.out.println("Titre  : <"+ list_mrc[i].titre +">\t chanteur(se) : <"+list_mrc[i].artiste+">");
            
            
            /*---------------------------------------------------------
             *  Recherche des morceaux de la chanteuse Indila
             ---------------------------------------------------------*/
            list_mrc = lecteurmp3.rechMorceauxParArtiste("Indila");
            System.out.println("---------- Les morceaux musicaux du chanteur(se) <Indila> ---------------");
            
            for(int i=0 ; i< list_mrc.length; i++)
            	System.out.println("Titre  : <"+ list_mrc[i].titre +">\t Genre musical : <"+list_mrc[i].genre+">");
            
            
            /*---------------------------------------------------------
             *  Recherche du morceau dont du titre est : soul
             ---------------------------------------------------------*/
            System.out.println("-------------- Recherche du morceau <Number One for me> -----------------");

            boolean trouve = lecteurmp3.rechMorceauParTitre("Number one for me");
            if(trouve)
            	System.out.println("le morceau <Number one for me> a été trouvé");
            else
            	System.out.println("le morceau <Number one for me> n'existe pas");
            
            /*---------------------------------------------------------
             *  Suppression du morceau dont le titre est : S.O.S
             ---------------------------------------------------------*/
            System.out.println("------------------- Suppression du morceau <Love Story> -----------------");
            boolean est_supp = lecteurmp3.supprimerMorceauParTitre("Love Story");
            if(est_supp)
            	System.out.println("le morceau <Love Story> a été supprimé");
            else
            	System.out.println("le morceau <Love Story> n'a pas été supprimé");
            
            /*---------------------------------------------------------
             *  Suppression des morceaux de la catégorie POP 
             ---------------------------------------------------------*/
            System.out.println("--------- Suppression des morceaux de la catégorie POP ------------------");
            int nbr_morceaux = lecteurmp3.supprimerMorceauxParGenre("POP");
            System.out.println("\t"+nbr_morceaux+ " morceau de la catégorie POP ont été supprimés");
            
            /*------------------------------------------------------------------
             *  Recherche du morceau dont le titre est : S.O.S après suppression
             ------------------------------------------------------------------*/
            System.out.println("-------------------- Recherche du morceau <S.O.S> -----------------------");
            trouve = lecteurmp3.rechMorceauParTitre("S.O.S");
            if(trouve)
            	System.out.println("le morceau <S.O.S> a été trouvé");
            else
            	System.out.println("le morceau <S.O.S> n'existe pas");
            trouve = lecteurmp3.rechMorceauParTitre("Chandelier");
            if(trouve)
            	System.out.println("le morceau <Chandelier> a été trouvé");
            else
            	System.out.println("le morceau <Chandelier> n'existe pas");
            
            
            /*---------------------------------------------------------
             *  Recherche des morceaux de la catégorie POP
             ---------------------------------------------------------*/
            
            list_mrc = lecteurmp3.rechMorceauxParGenre("POP");
            System.out.println("--------------- Les morceaux musicaux de la catégorie POP ---------------");
            for(int i=0 ; i< list_mrc.length; i++)
            	System.out.println("Titre  : <"+ list_mrc[i].titre +">\t chanteur(se) : <"+list_mrc[i].artiste+">");
        }
    }
}