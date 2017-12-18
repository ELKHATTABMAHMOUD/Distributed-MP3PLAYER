====================================================================================================
==================== Réalisé par : EL KHATTAB Mahmoud. Master 1 ILSEN.  ============================
====================================================================================================



========================== Notes sur le trvail réalisé =================================
Le serveur a été dévélopé en langage PYTHON  et le client  langage JAVA 			 ===
L'interface SLICE a été enregisté sous le fichier : lecteur.ice						 ===
========================================================================================


==> Le fichier Lecteur.ice contient une classe qui représente le morceau musical. 

==> Il contient également L'interface Manager qui comporte toutes les fonctions 
	d'ajout, de rechercher et de suppression d'un ou plusieurs morceaux. 

==> Le fichier contient aussi la séquence qui représente la liste des morceaux 
	disponibles c'est-à dire la PlayList

==> le fichier client est disponible sous le fichier java Client.java

==> Le fichier Server.py représente le serveur 


==> pour générer le code en java à partir de l'interface ice
					
			slice2java --output-dir Client/src Lecteurmp3.ice
			
==> pour la compilation et l'execution du client j'ai utilisé l'IDE Eclipse.

==> pour générer le code en python à partir de l'interface ice

			slice2py --output-dir Serveur Lecteurmp3.ice 
			
		=> execution : python Serveur.py
