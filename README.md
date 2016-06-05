### Simple android application

# ToDo

Autor:
Klaudia Olejniczak

# Opis ogólny

	Aplikacja do zarządzania swoją listą tasków.
			
# Opis techniczny:

	Aplikacja budowana przy pomocy Gradle.
	
	## Min SDK ver 19
	## Target SDK ver 23
	## APK kompilowana w SDK ver 23
	
	## Biblioteki dodatkowe:
			* RecyclerView - ver 23.0
			* CardViews - ver 23.0
			* Picasso - ver 2.5.2 - biblioteka do pobierania obrazków z url'ów
			* JodaTime - ver 2.9.3.1 - biblioteka do zarządzania czasem
			* Butterknife - ver 6.1.0 - biblioteka do wiązania elementów z view'sów w XML'u do zmiennych w JAVIE
			
# Opis szczegółowy:

	## Task zawiera:
			* Tytuł - pole obowiązkowe
			* Opis
			* Date z deadline'm
			* URL do obrazka
			
	## Funkcjonalnosć:
			* Dodawanie nowych tasków - poprzez floatingButtona
			* Usuwanie tasków zakończonych - porpzez swipe elementy listy
			* Edycja tasków - poprzez long clicka
			* Back up danych do pliku JSON - opcja w menu na ActionBar
			* Przywracanie danych z pliku JSON - opcja w menu na ActionBar
			
			

