# Zaawansowane-programowanie-w-Javie

## Database

Baza danych zawiera 4 tabele:
 - Users
   - Dane użytkowników niezbędne do logowania: email, password(hash)
   - Dane użytkowników do identyfikacji: Name, Surname, ProfilePicture(hash)
   - Dane dla administratora: createdAt, updatedAt
 - List
   - Hash, 16 znakowy 'kod', użytkownik w celu podzielenia się listą będzie przekazywał link do listy i hash innemu użytkownikowi któremu zechce udostępnić listę
   - idUsers, Klucz obcy wzkazujący na użytkownika do którego należy lista
   - Mode, określający w jakim trybie lista będzie udostępniana
     1. Copy
     2. Shared
   - isFavourite, pole określające priorytet list
   - Dane dla administratora: createdAt, updatedAt
 - ListItem
   - Klucz obcy wskazujący na listę do której przynależy item
   - Dane do określenia zawartości : Name, isDone, Description
   - Dane dla administratora: createdAt, updatedAt
 - Users_has_List
   - Tabela pośrednia
     
Wszystkie pola za wyjątkiem `Description` w `ListItem` są wymagane. Każda tabela posiada klucz główny o atrybucie `Auto Increament`

<p align='center'>
<img src="https://github.com/Gabrysiewicz/Zaawansowane-programowanie-w-Javie/blob/main/Database/Database.png">
</p>
