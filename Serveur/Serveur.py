#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys, traceback, Ice
from Lecteurmp3 import Manager, Morceau
	
"""	--------------------------------------------------------------------------------------------------------------------
	--------------------------  Réalisé par : EL KHATTAB Mahmoud, Master 1 - ILSEN  ------------------------------------
	--------------------------------------------------------------------------------------------------------------------"""

		
"""----------------------------------------------------------------------------------------------------------------------------
--- La classe Lecteur est utile pour ajouter des morceaux à une liste, chercher un ou plusieurs morceaux dans la liste des
---	morceau et bien évidement supprimer un ou plusieurs de la liste
----------------------------------------------------------------------------------------------------------------------------"""
	
class Lecteur(Manager):
	""" -----------------------------------------------------------------------------------------------------------------------
		Constructeur du lecteur dont à l'intéreur on dclare la liste qui contiendra tous les morceaux à jouer  
		--------------------------------------------------------------------------------------------------------------------"""
	def __init__(self):
		self.morceaux = list();
		self.nombre_morceaux = 0 ;
	
	""" --------------------------------------------------------------------------------------------------------------------
		Cette méthode permet d'ajouter un morceau pasé en paramètres à la liste des morceaux sauvegradé sur le serveur 
		--------------------------------------------------------------------------------------------------------------------
	"""
	def ajouterMorceau(self, titre, artiste, genre, adresse, current=None):
		morceau  = Morceau(titre, artiste, genre, adresse)
		if self.morceau_existe(morceau) == False :
			print 'Ajout du morceau de titre : '+titre
			self.morceaux.append(morceau)
			self.nombre_morceaux += 1 ;
		else :
			print 'Le morceau '+ titre +' existe déjà';
	""" -----------------------------------------------------------------------------------------------------------------------
		Cette méthode permet de rechercher et de renvoyer la liste des morceaux dont le nom de l'artiste est fourni en 
		paramètres
		--------------------------------------------------------------------------------------------------------------------"""
	def rechMorceauxParArtiste(self, artiste, current=None):
		""" --- Liste des morceaux trouvés --- """
		list_morceaux = list()
		print 'Recherche de tous les morceaux du chanteur(se) : '+artiste
		i = 0  
		while i < len(self.morceaux):
			if self.morceaux[i].artiste == artiste:
				list_morceaux.append(self.morceaux[i])
			i +=1
		return list_morceaux ;
		
	""" -----------------------------------------------------------------------------------------------------------------------
		Cette méthode permet de rechercher et de renvoyer la liste des morceaux dont le genre est fourni en 
		paramètres
		--------------------------------------------------------------------------------------------------------------------"""
	def rechMorceauxParGenre(self, genre, current=None):
		""" --- Liste des morceaux trouvés --- """
		list_morceaux = list()
		print 'Recherche des morceaux de la catégorie : '+genre
		i = 0  
		while i < len(self.morceaux):
			if self.morceaux[i].genre == genre:
				list_morceaux.append(self.morceaux[i])
			i +=1
		i = 0 ;
		return list_morceaux ;	
	""" -----------------------------------------------------------------------------------------------------------------------
		Cette méthode permet de rechercher un morceau par son titre fourni en paramètres. La méthode retourne un booléen (True si le morceau est trouvé et False sinon
		--------------------------------------------------------------------------------------------------------------------"""
	def rechMorceauParTitre(self, titre, current=None):
	
		print 'Recherche du morceau : '+titre
		i = 0  
		while i < len(self.morceaux):
			if self.morceaux[i].titre == titre:
				return True ;
			i +=1
		return False ;
	""" -----------------------------------------------------------------------------------------------------------------------
		Cette méthode permet de rechercher un morceau par son titre fourni en paramètres. La méthode retourne un booléen (True si le morceau est trouvé et False sinon
		--------------------------------------------------------------------------------------------------------------------"""
		
	def rechMorceauParLocation(self, location, current=None):
	
		print 'Recherche du morceau qui se trouve à : '+location
		i = 0  
		while i < len(self.morceaux):
			if self.morceaux[i].ressource == location:
				return True ;
			i +=1
		return False ;
	""" -----------------------------------------------------------------------------------------------------------------------
		Les fonctions de suppression : l'explication du fonctionnement de ces fonctions est donnée sur le fichier définissant 
		l'interface slice. 
		--------------------------------------------------------------------------------------------------------------------"""
	def supprimerMorceauxParArtiste(self, artiste, current=None):
		nbr_morceaux = 0 
		print 'Suppression des morceaux de l\'artiste : '+artiste
		i = 0  
		while i < len(self.morceaux):
			if self.morceaux[i].artiste == artiste:
				del self.morceaux[i] ; 
				nbr_morceaux +=1
			i += 1 
		return nbr_morceaux ;
		
	def supprimerMorceauxParGenre(self, genre, current=None):
		nbr_morceaux = 0
		print 'Suppression des morceaux du genre : '+genre
		i = 0
		while i < len(self.morceaux):
			if self.morceaux[i].genre == genre:
				print self.morceaux[i].titre
				del self.morceaux[i]
				nbr_morceaux +=1
			i += 1 
		return nbr_morceaux 
		
	def supprimerMorceauParTitre(self, titre, current=None):
		print 'Suppression du morceau : '+titre
		i = 0
		while i < len(self.morceaux):
			if self.morceaux[i].titre == titre: 
				del self.morceaux[i] ; 
				return True
			i += 1 
		return False ;
	
	def supprimerMorceauParLocation(self, location, current=None):
		print 'Suppression du morceau  qui se trouve à : '+location
		i = 0
		while i < len(self.morceaux):
			if self.morceaux[i].location == location: 
				del self.morceaux[i] ; 
				return True
			i += 1 
		return False ;
	
	def morceau_existe(self, morceau, current=None):
		i = 0
		while i < len(self.morceaux):
			if self.morceaux[i].titre == morceau.titre and self.morceaux[i].artiste == morceau.artiste and self.morceaux[i].genre == morceau.genre and self.morceaux[i].ressource == morceau.ressource: 
				return True
			i += 1 
		return False ;
		
	""" -----------------------------------------------------------------------------------------------------------------------
		Méthode d'affichage et de test 
		--------------------------------------------------------------------------------------------------------------------"""
	def printstring(self, s, current=None):
		print s ;
		list = [1,2,3]
		return list ;
		
		
	""" -----------------------------------------------------------------------------------------------------------------------
		Execution du serveur 
		--------------------------------------------------------------------------------------------------------------------"""
with Ice.initialize(sys.argv) as communicator:
    adapter = communicator.createObjectAdapterWithEndpoints("LecteurMP3Adapter", "default -p 20000")
    object = Lecteur()
    adapter.add(object, communicator.stringToIdentity("LecteurMP3"))
    adapter.activate()
    communicator.waitForShutdown()