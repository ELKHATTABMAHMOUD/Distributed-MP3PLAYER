module Lecteurmp3
{
	/* la classe représente le morceau musical */ 
	class Morceau{
		string titre ; 
		string artiste ; 
		string genre ;
		string ressource;
	};	
	/* les 3 sequences suivantes représentent signal audio d'un morceau musical, la liste des morceaux. il sert pour la spécifié comme type de retour de certaines méthode comme rechMorceauxParArtiste et rechMorceauxParGenre */
	sequence<float> signalAudio;
    sequence<Morceau> listMorceaux ;
	sequence<int> entiers ;
	
	interface Manager
    {
        bool ajouterMorceau(string titre, string genre, string auteur, string adresse);
		
		listMorceaux rechMorceauxParArtiste(string artiste);
		listMorceaux rechMorceauxParGenre(string genre);
		
		bool rechMorceauParTitre(string titre);
		bool rechMorceauParLocation(string location);
		
		/*---------------------------------------- fonctions de suppression -----------------------------------------
		 * la fonction supprimerMorceauxParArtiste supprime plusieurs Morceaux d'un même artiste (resp. du même genre). les
		 * deux fonctions retourne le nombre de morceaux supprimés   
		 */
		
		int supprimerMorceauxParArtiste(string artiste);
		int supprimerMorceauxParGenre(string genre);
		
		/* 
		 * Les deux fonctions suivantes permettent respectivement de supprimer un Morceau par titre et par son chemin. Elles 
		 * retournent true si la suppression a réussie et false sinon 
		 */
		
		bool supprimerMorceauParTitre(string titre);
		bool supprimerMorceauParLocation(string location);
		
		entiers printstring(string chaine);
		
    };
	
};