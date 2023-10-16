# Zaawansowane-programowanie-w-Javie

## Database

Baza danych zawiera 3 tabele:
 - Users
   - Dane użytkowników niezbędne do logowania: email, password(hash)
   - Dane użytkowników do identyfikacji: Name, Surname, ProfilePicture(hash)
   - Dane dla administratora: createdAt, updatedAt
 - List
   - Klucz obcy wskazujący na itemy przynależące do listy
   - Hash, 16 znakowy 'kod', użytkownik w celu podzielenia się listą będzie przekazywał link do listy i hash innemu użytkownikowi któremu zechce udostępnić listę
   - Klucz obcy wzkazujący na użytkownika do którego należy lista
   - Dane dla administratora: createdAt, updatedAt
 - ListItem
   - Klucz obcy wskazujący na listę do której przynależy item
   - Dane do określenia zawartości : Name, isDone, Description
   - Dane dla administratora: createdAt, updatedAt

Wszystkie pola za wyjątkiem `Description` w `ListItem` są wymagane. Każda tabela posiada klucz główny o atrybucie `Auto Increament`

<p align='center'>
<img src="https://github.com/Gabrysiewicz/Zaawansowane-programowanie-w-Javie/blob/main/Database/Database.png">
</p>
